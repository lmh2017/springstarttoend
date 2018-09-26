package org.springstarttoend.beans.factory.config;

import org.springstarttoend.beans.BeansException;

/**
 * @author mh_liu
 * @create 2018/9/25 下午5:59
 */
public interface BeanPostProcessor {

    Object beforeInitialization(Object bean, String beanName) throws BeansException;


    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
