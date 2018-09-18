package org.springstarttoend.stereotype;

import java.lang.annotation.*;

/**
 * @author mh_liu
 * @create 2018/9/15 下午4:07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";

}
