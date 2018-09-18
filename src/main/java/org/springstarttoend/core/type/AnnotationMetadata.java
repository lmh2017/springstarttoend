package org.springstarttoend.core.type;


import org.springstarttoend.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * @author mh_liu
 * @create 2018/9/15 下午3:04
 */
public interface AnnotationMetadata extends ClassMetadata{

    Set<String> getAnnotationTypes();


    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
