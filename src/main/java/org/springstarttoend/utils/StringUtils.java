package org.springstarttoend.utils;

/**
 * @author mh_liu
 * @create 2018/8/10 下午5:52
 */
public class StringUtils {

    public static boolean hasText(String str) {
        return hasLength(str) && containsText(str);
    }

    public static boolean hasLength(String str) {
        return str != null && !str.isEmpty();
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();

        for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }


}
