package com.grooveshark.util;

import java.util.List;
import java.util.LinkedList;
import java.io.IOException;
import java.lang.reflect.Field;


public class StringUtils
{
    public static void logToStdOut(String threadName, String msg) {
        System.out.println(DateUtils.getNow() + ": [" + threadName + "] " + msg);
    }

    public static LinkedList<String> splitTrim(String str, String sep) {
        LinkedList<String> splitted = new LinkedList<String>();
        String[] strSplit = str.split(sep);
        for(String st : strSplit) {
            splitted.add(st.trim());
        }
        return splitted;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static List<String> getFieldNames(Class<?> clazz)
        throws SecurityException
    {
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldNames = new LinkedList<String>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }
}
