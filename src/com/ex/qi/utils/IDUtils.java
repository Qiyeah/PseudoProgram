package com.ex.qi.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunline on 2016/6/2.
 */
public class IDUtils {
    public static final int AC = 0x01;
    public static final int DC = 0x02;
    public static final int DEVICEINFO = 0x03;
    public static final int REAL_KWH = 0x04;
    public static final int DAY_KWH = 0x05;
    public static final int MONTH_KWH = 0x06;
    public static final int YEAR_KWH = 0x07;
    public static final int PASTDAY_KWH = 0x08;
    public static final int PASTMONTH_KWH = 0x09;
    public static final int PASTYEAR_KWH = 0x10;
    public static String randomStr29(){
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        for (int i = 0; i < 12 ; i++) {
            sb.append(""+(int)(Math.random() * 10));
        }
        return sb.toString();
    }
    public static String randomStr30(){
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        for (int i = 0; i < 13 ; i++) {
            sb.append(""+(int)(Math.random() * 10));
        }
        return sb.toString();
    }
    public static String getId(int type){
        if (AC == type){
            return "AC"+ randomStr30();
        }else if (DC == type){
            return "DC"+ randomStr30();
        }else if (DEVICEINFO == type){
            return "INF"+ randomStr29();
        }else if (REAL_KWH == type){
            return "REA"+ randomStr29();
        }else if (DAY_KWH == type){
            return "DAY"+ randomStr29();
        }else if (MONTH_KWH == type){
            return "MON"+ randomStr29();
        }else if (YEAR_KWH == type){
            return "YEA"+ randomStr29();
        }else if (PASTDAY_KWH == type){
            return "ACD"+ randomStr29();
        }else if (PASTMONTH_KWH == type){
            return "ACM"+ randomStr29();
        }else if (PASTYEAR_KWH == type){
            return "ACY"+ randomStr29();
        }
        return null;
    }
}
