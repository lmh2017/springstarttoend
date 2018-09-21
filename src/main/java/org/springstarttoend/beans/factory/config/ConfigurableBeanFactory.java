package org.springstarttoend.beans.factory.config;

/**
 * @author mh_liu
 * @create 2018/7/10 下午5:53
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory{

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();
}
