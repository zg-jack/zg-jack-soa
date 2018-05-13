package com.zhuguang.jack.advice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.zhuguang.jack.invoke.Invocation;
import com.zhuguang.jack.invoke.Invoke;
import com.zhuguang.jack.spring.configBean.Reference;

  
/** 
 * @Description TODO 
 * @ClassName   InvokeInvocationHandler 
 * @Date        2018年5月13日 下午8:43:54 
 * @Author      zg-jack 
 * 
 * 这个类就是一个增强类  advice
 */  
    
public class InvokeInvocationHandler implements InvocationHandler {
    
    private Invoke invoke;
    
    private Reference reference;
    
    public InvokeInvocationHandler(Invoke invoke,Reference reference) {
        this.invoke = invoke;
        this.reference = reference;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("============invoke到了InvokeInvocationHandler=============");
        
        //在这个invoke里面做一个远程的rpc调用。
        Invocation invocation = new Invocation();
        invocation.setIntf(reference.getIntf());
        invocation.setMethod(method);
        invocation.setObjs(args);
        return invoke.invoke(invocation);
    }
    
}
