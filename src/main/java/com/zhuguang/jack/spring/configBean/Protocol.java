package com.zhuguang.jack.spring.configBean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.zhuguang.jack.netty.NettyUtil;
import com.zhuguang.jack.rmi.RmiServerStart;

public class Protocol implements ApplicationListener<ContextRefreshedEvent>,InitializingBean {
    private String id;
    
    private String port;
    
    private String name;
    
    private String host;
    
    private String contextpath;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPort() {
        return port;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public String getContextpath() {
        return contextpath;
    }
    
    public void setContextpath(String contextpath) {
        this.contextpath = contextpath;
    }

    /* 
     * ContextRefreshedEvent保证spring的配置全部加载完以后才会触发
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {
        
        if(!"netty".equalsIgnoreCase(name)) {
            return;
        }
        
        new Thread(new Runnable() {
            public void run() {
                try {
                    NettyUtil.startServer(port);
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void afterPropertiesSet() throws Exception {
        if("rmi".equalsIgnoreCase(name)) {
            RmiServerStart rmiServerStart = new RmiServerStart();
            rmiServerStart.startRmiServer(host, port, "jacksoa");
        }
    }
}
