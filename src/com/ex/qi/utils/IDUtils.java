package com.ex.qi.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by sunline on 2016/6/2.
 */
public class IDUtils {
    public static final int AC = 0x01;
    public static final int DC = 0x02;
    public static final int INFO = 0x03;
    public static final int REAL_KWH = 0x04;
    public static final int KWH_DAY = 0x05;
    public static final int KWH_MONTH = 0x06;
    public static final int KWH_YEAR = 0x07;
    public static final int KWH_ACCUM_DAY = 0x08;
    public static final int KWH_ACCUM_MONTH = 0x09;
    public static final int KWH_ACCUM_YEAR = 0x10;
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
        }else if (INFO == type){
            return "INF"+ randomStr29();
        }else if (REAL_KWH == type){
            return "REA"+ randomStr29();
        }else if (KWH_DAY == type){
            return "DAY"+ randomStr29();
        }else if (KWH_MONTH == type){
            return "MON"+ randomStr29();
        }else if (KWH_YEAR == type){
            return "YEA"+ randomStr29();
        }else if (KWH_ACCUM_DAY == type){
            return "ACD"+ randomStr29();
        }else if (KWH_ACCUM_MONTH == type){
            return "ACM"+ randomStr29();
        }else if (KWH_ACCUM_YEAR == type){
            return "ACY"+ randomStr29();
        }
        return null;
    }
    public static int generateId(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(Integer.toString(new Random().nextInt(10)));
        }
        return Integer.parseInt(sb.toString());
    }
}
