package com.zhuguang.jack.cluster;

import com.zhuguang.jack.invoke.Invocation;
import com.zhuguang.jack.invoke.Invoke;

public class FailoverCluster implements Cluster {
    
    public String invoke(Invocation invocation) throws Exception {
        String retries = invocation.getReference().getRetries();
        int count = Integer.parseInt(retries);
        
        for (int i = 0; i < count; i++) {
            try {
                Invoke invoke = invocation.getInvoke();
                String result = invoke.invoke(invocation);
                return result;
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        
       throw new RuntimeException("连接了 "  + count + "次还失败");
    }
}
