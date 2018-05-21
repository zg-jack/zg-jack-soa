package com.zhuguang.jack.spring.configBean;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zhuguang.jack.advice.InvokeInvocationHandler;
import com.zhuguang.jack.cluster.Cluster;
import com.zhuguang.jack.cluster.FailoverCluster;
import com.zhuguang.jack.invoke.HttpInvoke;
import com.zhuguang.jack.invoke.Invoke;
import com.zhuguang.jack.invoke.NettyInvoke;
import com.zhuguang.jack.invoke.RmiInvoke;
import com.zhuguang.jack.loadBalance.LoadBalance;
import com.zhuguang.jack.loadBalance.RondomLoadBalance;
import com.zhuguang.jack.loadBalance.RoundRobinLoadBalance;
import com.zhuguang.jack.registry.BaseRegistryDelegate;

public class Reference implements FactoryBean,ApplicationContextAware,InitializingBean {
    private String id;
    
    private String intf;
    
    private String check;
    
    private String protocol;
    
    private String loadbalance;
    
    private String cluster;
    
    private String retries;
    
    private Invoke invoke;
    
    private ApplicationContext application;
    
    private static Map<String,Invoke> invokeMaps = new HashMap<String,Invoke>();
    
    private static Map<String,LoadBalance> loadBalances = new HashMap<String,LoadBalance>();
    
    private static Map<String,Cluster> servers = new HashMap<String,Cluster>();
    
      
    /** 
     * @Fields registryInfo 本地缓存注册中心中的服务列表信息
     */  
        
    private List<String> registryInfo = new ArrayList<String>();
    
    
    static {
        invokeMaps.put("http", new HttpInvoke());
        invokeMaps.put("rmi", new RmiInvoke());
        invokeMaps.put("netty", new NettyInvoke());
        invokeMaps.put("jack", new NettyInvoke());
        
        loadBalances.put("random", new RondomLoadBalance());
        loadBalances.put("roundrob", new RoundRobinLoadBalance());
        
        servers.put("failover", new FailoverCluster());
        servers.put("failfast", new FailoverCluster());
        servers.put("failsafe", new FailoverCluster());
    }
    
    /* 
     * 返回一个对象，然后被spring容器管理
     * 
     * 这个方法要返回 intf这个接口的代理实例
     */
    public Object getObject() throws Exception {
        
        if(protocol != null && !"".equals(protocol)) {
            invoke = invokeMaps.get(protocol);
        } else {
            Protocol protocol = application.getBean(Protocol.class);
            if(protocol != null) {
                invoke = invokeMaps.get(protocol.getName());
            } else {
                invoke = invokeMaps.get("http");
            }
        } 
        
        Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[] {Class.forName(intf)},
                new InvokeInvocationHandler(invoke,this));
        
        return proxy;
    }
    
    /* 
     * 返回实例的类型
     */
    public Class getObjectType() {
        try {
            if (intf != null && !"".equals(intf)) {
                return Class.forName(intf);
            }
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /* 
     * 是否单例
     */
    public boolean isSingleton() {
        return true;
    }
    
    public String getId() {
        return id;
    }
    
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.application = applicationContext;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getIntf() {
        return intf;
    }
    
    public void setIntf(String intf) {
        this.intf = intf;
    }
    
    public String getCheck() {
        return check;
    }
    
    public void setCheck(String check) {
        this.check = check;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void afterPropertiesSet() throws Exception {
        registryInfo = BaseRegistryDelegate.getRegistry(id, application);
    }

    public List<String> getRegistryInfo() {
        return registryInfo;
    }

    public void setRegistryInfo(List<String> registryInfo) {
        this.registryInfo = registryInfo;
    }

    public static Map<String, LoadBalance> getLoadBalances() {
        return loadBalances;
    }

    public static void setLoadBalances(Map<String, LoadBalance> loadBalances) {
        Reference.loadBalances = loadBalances;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public static Map<String, Cluster> getServers() {
        return servers;
    }

    public static void setServers(Map<String, Cluster> servers) {
        Reference.servers = servers;
    }
}
