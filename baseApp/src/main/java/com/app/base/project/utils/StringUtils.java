package com.app.base.project.utils;

public class StringUtils {

    public static String getFormattedString(String msg, Object... args) {
        return msg.formatted(args);
    }

    public static String notFoundMessage(String errMsg){
        return getFormattedString("Invalid data %s not found", errMsg);
    }
}
