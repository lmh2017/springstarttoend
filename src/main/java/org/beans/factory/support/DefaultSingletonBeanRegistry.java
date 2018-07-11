package org.beans.factory.support;

import org.beans.factory.config.SingletonBeanRegistry;
import org.utils.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mh_liu
 * @create 2018/7/10 下午6:15
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final static Map<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName,"'beanName' must not be null");

        Object oldObject = this.getSingleton(beanName);
        if(oldObject != null){
            throw new IllegalStateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
        }
        this.singletonObjects.put(beanName,singletonObject);

    }

    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
}
