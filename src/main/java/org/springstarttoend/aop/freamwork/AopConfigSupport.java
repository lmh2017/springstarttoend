package org.springstarttoend.aop.freamwork;

import org.springstarttoend.aop.Advice;
import org.springstarttoend.aop.Pointcut;
import org.springstarttoend.utils.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author mh_liu
 * @create 2018/10/10 下午5:47
 */
public class AopConfigSupport implements AopConfig{

    private boolean proxyTargetClass = false;

    private Object targetObject = null;

    private List<Advice> advices = new ArrayList<Advice>();

    private List<Class> interfaces = new ArrayList<Class>();

    public AopConfigSupport() {

    }

    @Override
    public Class<?> getTargetClass() {
        return this.targetObject.getClass();
    }

    @Override
    public Object getTargetObject() {
        return this.targetObject;
    }

    @Override
    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    @Override
    public Class<?>[] getProxiedInterfaces() {
        return this.interfaces.toArray(new Class[this.interfaces.size()]);
    }

    @Override
    public boolean isInterfaceProxied(Class<?> intf) {
        Iterator var2 = this.interfaces.iterator();

        Class proxyIntf;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            proxyIntf = (Class)var2.next();
        } while(!intf.isAssignableFrom(proxyIntf));

        return true;
    }

    public void addInterface(Class<?> intf) {
        Assert.notNull(intf, "Interface must not be null");
        if (!intf.isInterface()) {
            throw new IllegalArgumentException("[" + intf.getName() + "] is not an interface");
        }
        if (!this.interfaces.contains(intf)) {
            this.interfaces.add(intf);

        }
    }

    @Override
    public List<Advice> getAdvices() {
        return this.advices;
    }

    @Override
    public void addAdvice(Advice advice) {
        this.advices.add(advice);
    }

    @Override
    public List<Advice> getAdvices(Method method) {
        List<Advice> result = new ArrayList<Advice>();
        for(Advice advice : this.getAdvices()){
            Pointcut pc = advice.getPointcut();
            if(pc.getMethodMatcher().matches(method)){
                result.add(advice);
            }
        }
        return result;
    }

    @Override
    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

}
