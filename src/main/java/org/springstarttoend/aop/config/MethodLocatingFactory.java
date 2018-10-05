package org.springstarttoend.aop.config;

import org.springstarttoend.beans.BeanUtils;
import org.springstarttoend.beans.factory.BeanFactory;
import org.springstarttoend.utils.StringUtils;

import java.lang.reflect.Method;

/**
 * @author mh_liu
 * @create 2018/10/4 下午1:55
 */
public class MethodLocatingFactory {

    private String targetBeanName;

    private String methodName;

    private Method method;

    public void setTargetBeanName(String targetBeanName){
        this.targetBeanName = targetBeanName;
    }

    public void setMethodName(String methodName){
        this.methodName = methodName;
    }

    public void setBeanFactory(BeanFactory beanFactory){
        if (!StringUtils.hasText(this.targetBeanName)) {
            throw new IllegalArgumentException("Property 'targetBeanName' is required");
        }
        if (!StringUtils.hasText(this.methodName)) {
            throw new IllegalArgumentException("Property 'methodName' is required");
        }

        Class<?> beanClass = beanFactory.getType(this.targetBeanName);
        if (beanClass == null) {
            throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName + "'");
        }

        this.method = BeanUtils.resolveSignature(methodName,beanClass);

        if(this.method == null){
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
                    "] on bean [" + this.targetBeanName + "]");
        }
    }

    public Method getObject() throws Exception {
        return this.method;
    }
}
