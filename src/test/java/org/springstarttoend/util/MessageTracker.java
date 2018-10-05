package org.springstarttoend.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mh_liu
 * @create 2018/10/4 上午11:34
 */
public class MessageTracker {

    private static List<String> MESSAGES = new ArrayList<String>();

    public static void addMsg(String msg) {
        MESSAGES.add(msg);
    }

    public static void clearMsgs() {
        MESSAGES.clear();
    }

    public static List<String> getMsgs() {
        return MESSAGES;

    }
}
