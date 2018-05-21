package com.zhuguang.jack.cluster;

import com.zhuguang.jack.invoke.Invocation;

public interface Cluster {
    public String invoke(Invocation invocation) throws Exception;
}
