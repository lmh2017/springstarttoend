package org.springstarttoend.beans.factory.config;

import org.springstarttoend.beans.factory.BeanFactory;
/**
 * @author mh_liu
 * @create 2018/9/21 下午5:47
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object resolveDependency(DependencyDescriptor descriptor);

}
