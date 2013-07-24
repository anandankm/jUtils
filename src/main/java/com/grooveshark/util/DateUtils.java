package com.grooveshark.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{
    public static final SimpleDateFormat fullSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat partSdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getNow()
    {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(now);
    }

    public static Date getDate(String dateString, boolean full)
        throws ParseException
    {
        Date date = null;
        if (full) {
            date = DateUtils.fullSdf.parse(dateString);
        } else {
            date = DateUtils.partSdf.parse(dateString);
        }
        return date;
    }
}
