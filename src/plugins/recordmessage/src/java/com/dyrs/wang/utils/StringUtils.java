package com.dyrs.wang.utils;

import java.util.Arrays;

public class StringUtils {
    public static String makeNewString(String s1, String s2) {
        String[] strings = new String[] {s1, s2};
        Arrays.sort(strings);
        return String.join(":", strings);
    }
}
