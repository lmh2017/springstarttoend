package org.springstarttoend.beans.factory;

/**
 * @author mh_liu
 * @create 2018/7/5 下午5:36
 * bean工厂，获取实例对象
 */
public interface BeanFactory {

    Object getBean(String beanId);

    Class<?> getType(String beanName);

}
