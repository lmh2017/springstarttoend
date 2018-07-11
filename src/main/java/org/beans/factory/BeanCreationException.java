package org.beans.factory;

/**
 * @author mh_liu
 * @create 2018/7/11 下午3:38
 */
public class BeanCreationException extends Throwable {
    private String beanName;
    public BeanCreationException(String msg) {
        super(msg);

    }
    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeanCreationException(String beanName, String msg) {
        super("Error creating bean with name '" + beanName + "': " + msg);
        this.beanName = beanName;
    }

    public BeanCreationException(String beanName, String msg, Throwable cause) {
        this(beanName, msg);
        initCause(cause);
    }
    public String getBeanName(){
        return this.beanName;
    }


}
