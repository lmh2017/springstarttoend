package org.springstarttoend.beans.factory.config;

/**
 * @author mh_liu
 * @create 2018/8/13 下午8:13
 */
public class TypedStringValue {

    private Object value;
    public TypedStringValue(Object attributeValue) {
        this.value = attributeValue;
    }

    public Object getValue(){
        return this.value;
    }

}
