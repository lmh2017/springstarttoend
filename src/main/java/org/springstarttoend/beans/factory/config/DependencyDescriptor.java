package org.springstarttoend.beans.factory.config;

import org.springstarttoend.utils.Assert;

import java.lang.reflect.Field;

/**
 * @author mh_liu
 * @create 2018/9/20 下午6:35
 */
public class DependencyDescriptor {
    private Field field;
    private String methodName;
    private Class<?>[] parameterTypes;
    private int parameterIndex;
    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Field must not be null");
        this.field = field;
        this.required = required;

    }

    public DependencyDescriptor(String methodName,Class<?>[] parameterTypes,boolean required){
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.required = required;
        this.parameterIndex = 0;
    }

    public Class<?> getParameterType(){
        return parameterTypes[parameterIndex];
    }

    public void increaseParameterIndex(){
        this.parameterIndex += 1;
    }

    public Class<?> getDependencyType(){
        if(this.field != null){
            return field.getType();
        }else if(parameterTypes.length != 0){
            return getParameterType();
        }
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired() {
        return this.required;
    }
}
