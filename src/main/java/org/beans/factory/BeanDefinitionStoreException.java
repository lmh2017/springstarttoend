package org.beans.factory;

import org.beans.BeansException;

/**
 * @author mh_liu
 * @create 2018/7/5 下午6:06
 */
public class BeanDefinitionStoreException extends BeansException{

    public BeanDefinitionStoreException(String message) {
        super(message);
    }

    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
