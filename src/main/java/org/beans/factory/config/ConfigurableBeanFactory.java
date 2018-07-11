package org.beans.factory.config;

import org.beans.factory.BeanFactory;

/**
 * @author mh_liu
 * @create 2018/7/10 下午5:53
 */
public interface ConfigurableBeanFactory extends BeanFactory{

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();
}
