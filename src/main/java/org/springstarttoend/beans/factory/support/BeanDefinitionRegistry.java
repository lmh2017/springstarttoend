package org.springstarttoend.beans.factory.support;

import org.springstarttoend.beans.BeanDefinition;

/**
 * @author mh_liu
 * @create 2018/7/5 下午5:46
 * spring容器注册接口
 */
public interface BeanDefinitionRegistry {

    // 关键 -> 往注册表中注册一个新的 BeanDefinition 实例
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    // 移除注册表中已注册的 BeanDefinition 实例
    void removeBeanDefinition(String beanName);

    // 从注册中取得指定的 BeanDefinition 实例
    BeanDefinition getBeanDefinition(String beanName);

    // 判断 BeanDefinition 实例是否在注册表中（是否注册）
    boolean containsBeanDefinition(String beanName);

    // 取得注册表中所有 BeanDefinition 实例的 beanName（标识）
    String[] getBeanDefinitionNames();

    // 返回注册表中 BeanDefinition 实例的数量
    int getBeanDefinitionCount();

    // beanName（标识）是否被占用
    boolean isBeanNameInUse(String beanName);
}
