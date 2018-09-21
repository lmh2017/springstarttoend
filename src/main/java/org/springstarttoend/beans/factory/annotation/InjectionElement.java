package org.springstarttoend.beans.factory.annotation;

import org.springstarttoend.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @author mh_liu
 * @create 2018/9/21 下午5:29
 */
public abstract class InjectionElement {

    protected Member member;

    protected AutowireCapableBeanFactory factory;

    InjectionElement(Member member,AutowireCapableBeanFactory factory){
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);
}
