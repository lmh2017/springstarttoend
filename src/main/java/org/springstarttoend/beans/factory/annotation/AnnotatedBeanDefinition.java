package org.springstarttoend.beans.factory.annotation;

import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.core.type.AnnotationMetadata;

/**
 * @author mh_liu
 * @create 2018/9/17 下午2:59
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

    AnnotationMetadata getMetadata();

}
