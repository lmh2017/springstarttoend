package org.springstarttoend.aop;

/**
 * @author mh_liu
 * @create 2018/10/5 下午4:27
 */
@SuppressWarnings("serial")
public class AopInvocationException extends RuntimeException{

    /**
     * Constructor for AopInvocationException.
     * @param msg the detail message
     */
    public AopInvocationException(String msg) {
        super(msg);
    }

    /**
     * Constructor for AopInvocationException.
     * @param msg the detail message
     * @param cause the root cause
     */
    public AopInvocationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
