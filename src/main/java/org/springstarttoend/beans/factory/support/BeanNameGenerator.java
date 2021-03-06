package org.springstarttoend.beans.factory.support;

import org.springstarttoend.beans.BeanDefinition;

/**
 * @author mh_liu
 * @create 2018/9/17 下午3:07
 */
public interface BeanNameGenerator {

    /**
     * Generate a bean name for the given bean definition.
     * @param definition the bean definition to generate a name for
     * @param registry the bean definition registry that the given definition
     * is supposed to be registered with
     * @return the generated bean name
     */
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}
