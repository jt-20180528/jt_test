package com.jt.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    private static String ymd = "yyyy-MM-dd";
    private static String ymdhms = "yyyy-MM-dd hh:mm:ss";
    private static String ymdHms = "yyyy-MM-dd HH:mm:ss";
    private static String ymdHmsNoFormat = "yyyyMMddHHmmss";

    public static String ymd2str() {
        SimpleDateFormat sdf = new SimpleDateFormat(ymd);
        return sdf.format(new Date());
    }

    public static String ymdhms2str() {
        SimpleDateFormat sdf = new SimpleDateFormat(ymdhms);
        return sdf.format(new Date());
    }

    public static Date ymdHms2date() {
        SimpleDateFormat sdf = new SimpleDateFormat(ymdHms);
        Date date = null;
        try{
            date= sdf.parse(sdf.format(new Date()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    public static Date ymd2date() {
        SimpleDateFormat sdf = new SimpleDateFormat(ymd);
        Date date = null;
        try{
            date= sdf.parse(sdf.format(new Date()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    public static Date ymdhms2date() {
        SimpleDateFormat sdf = new SimpleDateFormat(ymdhms);
        Date date = null;
        try{
            date= sdf.parse(sdf.format(new Date()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    public static String ymdHms2str() {
        SimpleDateFormat sdf = new SimpleDateFormat(ymdHms);
        return sdf.format(new Date());
    }

    public static String ymdHms2str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(ymdHms);
        return sdf.format(date);
    }

    public static String ymdHmsNoFormat2str() {
        SimpleDateFormat sdf = new SimpleDateFormat(ymdHmsNoFormat);
        return sdf.format(new Date());
    }

    public static boolean beforeDate(Date targetDate){
        Calendar currentDt = Calendar.getInstance();
        currentDt.setTime(new Date());
        Calendar targetDt = Calendar.getInstance();
        targetDt.setTime(targetDate);
        return targetDt.before(currentDt);
    }
}
