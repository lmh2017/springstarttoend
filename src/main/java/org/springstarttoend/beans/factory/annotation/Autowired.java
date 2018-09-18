package org.springstarttoend.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author mh_liu
 * @create 2018/9/7 下午4:58
 */
@Target({ElementType.CONSTRUCTOR,ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    boolean required() default true;

}
