package com.zhuguang.jack.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.zhuguang.jack.redis.RedisApi;
import com.zhuguang.jack.spring.configBean.Protocol;
import com.zhuguang.jack.spring.configBean.Registry;
import com.zhuguang.jack.spring.configBean.Service;

public class RedisRegistry implements BaseRegistry {
    
    public boolean registry(String ref, ApplicationContext application) {
        try {
            Protocol protocol = application.getBean(Protocol.class);
            Map<String, Service> services = application.getBeansOfType(Service.class);
            Registry registry = application.getBean(Registry.class);
            RedisApi.createJedisPool(registry.getAddress());
            for (Map.Entry<String, Service> entry : services.entrySet()) {
                //这个if就对应一个service标签的注册信息
                /**
                 * {host:ip : 
                    {protocol : http,
                    service : JSONObject.toJsonString(Service.class)}
                   }
                 *  */
                if (entry.getValue().getRef().equals(ref)) {
                    JSONObject jo = new JSONObject();
                    jo.put("protocol", JSONObject.toJSONString(protocol));
                    jo.put("service", JSONObject.toJSONString(entry.getValue()));
                    
                    JSONObject hostport = new JSONObject();
                    hostport.put(protocol.getHost() + ":" + protocol.getPort(),
                            jo);
                    //需要把service标签的注册信息，加入到对应的服务中
                    lpush(hostport, ref);
                }
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private void lpush(JSONObject hostport, String key) {
        if (RedisApi.exists(key)) {
            Set<String> keySet = hostport.keySet();
            String hostportkey = "";
            for (String kk : keySet) {
                hostportkey = kk;
            }
            
            List<String> registryInfo = RedisApi.lrange(key);
            List<String> newRegistry = new ArrayList<String>();
            
            boolean isold = false;
            
            //这个循环的目的，有可能生产者启动的时候修改了某一些配置信息，这时候就需要把该生产者原来的信息替换成修改后的信息
            for (String node : registryInfo) {
                JSONObject jo = JSONObject.parseObject(node);
                //如果registryInfo注册信息里面包含了这个service的配置信息，说明以前这个生产者注册过
                //这时候再启动的话，我要把之前的service的注册信息，替换
                if (jo.containsKey(hostportkey)) {
                    newRegistry.add(hostport.toJSONString());
                    isold = true;
                }
                else {
                    //如果没有，那就说明之前这个service标签没有注册过，这时候可能是一个新的生产者启动
                    newRegistry.add(node);
                }
            }
            //如果有配置信息的修改，需要把之前的老配置的key删掉
            if (isold) {
                if (newRegistry.size() > 0) {
                    RedisApi.del(key);
                    String[] newRegistryStr = new String[newRegistry.size()];
                    for (int i = 0; i < newRegistry.size(); i++) {
                        newRegistryStr[i] = newRegistry.get(i);
                    }
                    RedisApi.lpush(key, newRegistryStr);
                }
            }
            else {
                //如果没有配置信息修改，就把心的hostport对象加入到list中
                //list.add()
                RedisApi.lpush(key, hostport.toJSONString());
            }
        }
        else {
            //第一次这个服务注册
            RedisApi.lpush(key, hostport.toJSONString());
        }
    }
    
    public List<String> getRegistry(String id, ApplicationContext application) {
        try {
            Registry registry = application.getBean(Registry.class);
            RedisApi.createJedisPool(registry.getAddress());
            
            if(RedisApi.exists(id)) {
                return RedisApi.lrange(id);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
