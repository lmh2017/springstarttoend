package org.springstarttoend.utils;

/**
 * @author mh_liu
 * @create 2018/7/10 下午6:18
 */
public abstract class Assert {

    public static void notNull(Object object,String message){
        if(object == null){
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasText(String beanName, String s) {
        if(StringUtils.hasText(s)){

        }
    }
}
