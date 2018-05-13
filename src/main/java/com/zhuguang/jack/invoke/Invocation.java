package com.zhuguang.jack.invoke;

import java.lang.reflect.Method;

public class Invocation {
    private Method method;
    
    private Object[] objs;
    
    private String intf;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getObjs() {
        return objs;
    }

    public void setObjs(Object[] objs) {
        this.objs = objs;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }
    
    
}
