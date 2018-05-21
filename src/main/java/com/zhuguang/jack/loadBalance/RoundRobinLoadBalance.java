package com.zhuguang.jack.loadBalance;

import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.zhuguang.jack.invoke.NodeInfo;

public class RoundRobinLoadBalance implements LoadBalance {
    
    private static Integer index = 0;
    
    public NodeInfo doSelect(List<String> registryInfo) {
        
        String registry = "";
        synchronized (index) {
            if (index >= registryInfo.size()) {
                index = 0;
            }
            registry = registryInfo.get(index);
            index++;
        }
        JSONObject parseObject = JSONObject.parseObject(registry);
        Collection<Object> values = parseObject.values();
        
        JSONObject node = new JSONObject();
        
        for (Object value : values) {
            node = JSONObject.parseObject(value.toString());
        }
        
        JSONObject protocol = node.getJSONObject("protocol");
        
        NodeInfo ni = new NodeInfo();
        ni.setHost(protocol.get("host") != null ? protocol.getString("host")
                : "");
        ni.setPort(protocol.get("port") != null ? protocol.getString("port")
                : "");
        ni.setContextpath(protocol.get("contextpath") != null ? protocol.getString("contextpath") : "");
        return ni;
    }
    
}
