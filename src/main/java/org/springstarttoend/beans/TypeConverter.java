package org.springstarttoend.beans;

/**
 * @author mh_liu
 * @create 2018/8/15 下午7:16
 */
public interface TypeConverter {

    <T> T convertIfNecessary(Object value,Class<T> requiredTyep);
}
