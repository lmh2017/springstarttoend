package org.springstarttoend.beans.factory.annotation;

import org.springstarttoend.beans.factory.BeanCreationException;
import org.springstarttoend.beans.factory.config.AutowireCapableBeanFactory;
import org.springstarttoend.beans.factory.config.DependencyDescriptor;
import org.springstarttoend.utils.ReflectionUtils;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * @author mh_liu
 * @create 2018/9/25 下午3:46
 */
public class AutowiredMethodElement extends InjectionElement {

    boolean required;

    public AutowiredMethodElement(Member member, boolean required, AutowireCapableBeanFactory factory) {
        super(member,factory);
        this.required = required;

    }

    @Override
    public void inject(Object target) {
        Method method = (Method) member;
        Object[] arguments;
        Class<?>[] paramTypes = method.getParameterTypes();
        arguments = new Object[paramTypes.length];
        DependencyDescriptor dependencyDescriptor = new DependencyDescriptor(method.getName(),paramTypes,required);
        for(int i=0;i<arguments.length;i++){
            Object bean = factory.resolveDependency(dependencyDescriptor);
            arguments[i] = bean;
            dependencyDescriptor.increaseParameterIndex();
        }
        try {
            if(arguments != null){
                ReflectionUtils.makeAccessible(method);
                method.invoke(target,arguments);
            }
        }catch (Exception e){
            throw new BeanCreationException("Could not autowire field: " + method, e);
        }
    }
}
