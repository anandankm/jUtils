package com.grooveshark.util;


public class StringUtils 
{
    public static void logToStdOut(String threadName, String msg) {
        System.out.println(DateUtils.getNow() + ": [" + threadName + "] " + msg);
    }
}
