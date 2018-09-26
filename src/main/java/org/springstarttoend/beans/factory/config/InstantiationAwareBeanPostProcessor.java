package org.springstarttoend.beans.factory.config;

import org.springstarttoend.beans.BeansException;

/**
 * @author mh_liu
 * @create 2018/9/25 下午2:56
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    void postProcessPropertyValues(Object bean, String beanName)
            throws BeansException;
}
