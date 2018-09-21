package org.springstarttoend.beans.factory.annotation;

import org.springstarttoend.beans.factory.BeanCreationException;
import org.springstarttoend.beans.factory.config.AutowireCapableBeanFactory;
import org.springstarttoend.beans.factory.config.DependencyDescriptor;
import org.springstarttoend.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

/**
 * @author mh_liu
 * @create 2018/9/21 下午5:30
 */
public class AutowiredFieldElement extends InjectionElement {

    boolean required;

    public AutowiredFieldElement(Member member, boolean required, AutowireCapableBeanFactory factory) {
        super(member, factory);
        this.required = required;
    }

    public Field getField(){
        return (Field)this.member;
    }

    @Override
    public void inject(Object target) {
        Field field = this.getField();
        try {
            DependencyDescriptor desc = new DependencyDescriptor(field,required);
            Object object = factory.resolveDependency(desc);

            if(object != null){
                ReflectionUtils.makeAccessible(field);
                field.set(target,object);
            }
        }catch (Exception e){
            throw new BeanCreationException("Could not autowire field: " + field, e);
        }
    }
}
