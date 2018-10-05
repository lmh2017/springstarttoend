package org.springstarttoend.tx;

import org.springstarttoend.util.MessageTracker;

/**
 * @author mh_liu
 * @create 2018/10/4 上午11:33
 */
public class TransactionManager {

    public void start(){
        System.out.println("start tx");
        MessageTracker.addMsg("start tx");
    }
    public void commit(){
        System.out.println("commit tx");
        MessageTracker.addMsg("commit tx");
    }
    public void rollback(){
        System.out.println("rollback tx");
        MessageTracker.addMsg("rollback tx");
    }
}
