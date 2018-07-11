package org.beans;

/**
 * @author mh_liu
 * @create 2018/7/5 下午6:07
 */
public class BeansException extends RuntimeException{

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
