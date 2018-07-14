package org.springstarttoend.beans.factory;

import org.springstarttoend.beans.BeanDefinition;

/**
 * @author mh_liu
 * @create 2018/7/5 下午5:36
 * bean工厂，获取实例对象
 */
public interface BeanFactory {

    Object getBean(String beanId);

    String getBeanClassName();

    BeanDefinition getBeanDefinition(String petStore);
}
