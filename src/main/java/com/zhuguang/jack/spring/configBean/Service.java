package com.zhuguang.jack.spring.configBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zhuguang.jack.registry.BaseRegistryDelegate;

public class Service implements InitializingBean,ApplicationContextAware {
    private String id;
    
    private String intf;
    
    private String ref;
    
    private ApplicationContext application;

    public String getId() {
        return id;
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

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    /* 
     * 这个方法是在Service类实例化以后，并且里面的属性IOC注入进来要后调用的
     */
    public void afterPropertiesSet() throws Exception {
        System.out.println("Service afterPropertiesSet " + intf + " : " + ref);
        BaseRegistryDelegate.registry(ref,application);
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.application = applicationContext;
    }
}
