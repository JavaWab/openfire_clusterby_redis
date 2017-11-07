package org.jivesoftware.openfire.plugin.rest.utils;

import java.util.Arrays;

public class StringUtils {

//    public static void main(String[] args) {
//        System.out.println(makeNewString("jui_277267266", "abc_6363"));
//        System.out.println(makeNewString("abc_6363", "jui_277267266"));
//    }

    public static String makeNewString(String s1, String s2) {
            String[] strings = new String[] {s1, s2};
            Arrays.sort(strings);
            return String.join(":", strings);
    }

}
