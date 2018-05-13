package com.zhuguang.jack.spring.parse;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.zhuguang.jack.spring.configBean.Protocol;
import com.zhuguang.jack.spring.configBean.Reference;


  
/** 
 * @Description TODO 
 * @ClassName   SOANamespaceHandler 
 * @Date        2018年5月10日 下午10:03:20 
 * @Author      zg-jack 
 * 
 * 这个类是用来注册标签解析类的
 */  
    
public class SOANamespaceHandler extends NamespaceHandlerSupport {

    public void init() {
        this.registerBeanDefinitionParser("registry", new RegistryBeanDefinitionParse());
        this.registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParse(Reference.class));
        this.registerBeanDefinitionParser("protocol", new ProtocolBeanDefinitionParse(Protocol.class));
        this.registerBeanDefinitionParser("service", new ServiceBeanDefinitionParse());
    }
}
