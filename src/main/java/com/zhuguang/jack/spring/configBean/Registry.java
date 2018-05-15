package com.zhuguang.jack.spring.configBean;

import java.util.HashMap;
import java.util.Map;

import com.zhuguang.jack.registry.BaseRegistry;
import com.zhuguang.jack.registry.RedisRegistry;

public class Registry {
    private String id;
    
    private String protocol;
    
    private String address;
    
    private static Map<String,BaseRegistry> registrys = new HashMap<String,BaseRegistry>();
    
    static {
        registrys.put("redis", new RedisRegistry());
        registrys.put("zookeeper", null);
        registrys.put("mongodb", null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static Map<String, BaseRegistry> getRegistrys() {
        return registrys;
    }

    public static void setRegistrys(Map<String, BaseRegistry> registrys) {
        Registry.registrys = registrys;
    }
}
