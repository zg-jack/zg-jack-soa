package com.zhuguang.jack.loadBalance;

import java.util.List;

import com.zhuguang.jack.invoke.NodeInfo;

public interface LoadBalance {
    NodeInfo doSelect(List<String> registryInfo);
}
