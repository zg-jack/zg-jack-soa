package com.zhuguang.jack.cluster;

import com.zhuguang.jack.invoke.Invocation;
import com.zhuguang.jack.invoke.Invoke;

public class FailfastCluster implements Cluster {
    
    public String invoke(Invocation invocation) throws Exception {
        Invoke invoke = invocation.getInvoke();
        try {
            return invoke.invoke(invocation);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
}
