package org.springstarttoend.aop.freamwork;

/**
 * @author mh_liu
 * @create 2018/10/12 下午3:16
 */
public class AopConfigException extends RuntimeException {

    /**
     * Constructor for AopConfigException.
     * @param msg the detail message
     */
    public AopConfigException(String msg) {
        super(msg);
    }

    /**
     * Constructor for AopConfigException.
     * @param msg the detail message
     * @param cause the root cause
     */
    public AopConfigException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
