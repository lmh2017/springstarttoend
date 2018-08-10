package org.springstarttoend.beans.factory.config;

import org.springstarttoend.utils.Assert;

/**
 * @author mh_liu
 * @create 2018/8/10 下午5:49
 */
public class RuntimeBeanReference {

    private final String beanName;

    public RuntimeBeanReference(String beanName){
        Assert.hasText(beanName, "'beanName' must not be empty");
        this.beanName = beanName;
    }

    public String getBeanName(){
        return this.beanName;
    }




}
