package com.zhuguang.jack.invoke;

import java.lang.reflect.Method;

import com.zhuguang.jack.spring.configBean.Reference;

public class Invocation {
    private Method method;
    
    private Object[] objs;
    
    private String intf;
    
    private Reference reference;
    
    private Invoke invoke;

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

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Invoke getInvoke() {
        return invoke;
    }

    public void setInvoke(Invoke invoke) {
        this.invoke = invoke;
    }
}
