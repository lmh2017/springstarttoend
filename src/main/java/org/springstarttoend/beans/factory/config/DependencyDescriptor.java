package org.springstarttoend.beans.factory.config;

import org.springstarttoend.utils.Assert;

import java.lang.reflect.Field;

/**
 * @author mh_liu
 * @create 2018/9/20 下午6:35
 */
public class DependencyDescriptor {
    private Field field;
    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Field must not be null");
        this.field = field;
        this.required = required;

    }
    public Class<?> getDependencyType(){
        if(this.field != null){
            return field.getType();
        }
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired() {
        return this.required;
    }
}
