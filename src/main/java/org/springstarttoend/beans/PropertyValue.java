package org.springstarttoend.beans;

/**
 * @author mh_liu
 * @create 2018/8/10 下午5:32
 * xml里面的<property></property>内容的抽象
 */
public class PropertyValue {

    private String name;

    private Object value;

    private boolean converted = false;

    private Object convertedValue;

    public PropertyValue(String name,Object value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public synchronized boolean isConverted() {
        return converted;
    }

    public synchronized Object getConvertedValue() {
        return convertedValue;
    }

    public synchronized void setConvertedValue(Object convertedValue) {
        this.convertedValue = convertedValue;
    }
}
