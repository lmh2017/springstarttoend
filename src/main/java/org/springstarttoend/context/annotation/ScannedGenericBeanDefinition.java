package org.springstarttoend.context.annotation;

import org.springstarttoend.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springstarttoend.beans.factory.support.GenericBeanDefinition;
import org.springstarttoend.core.type.AnnotationMetadata;

/**
 * @author mh_liu
 * @create 2018/9/17 下午3:00
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition{

    private final AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata metadata){
        super();
        this.metadata = metadata;
        setBeanClassName(this.metadata.getClassName());

    }

    @Override
    public AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}
