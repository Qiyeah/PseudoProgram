package com.ex.qi.dao;

/**
 * Created by sunline on 2016/9/7.
 */
public abstract class BaseKwhDao extends BaseDaoImpl{
    public static final String KWH_DAY = "DayKwh";
    public static final String KWH_MONTH = "MonthKwh";
    public static final String KWH_YEAR= "YearKwh";
    public static final String KWH_ACCUM_DAY = "AccumDayKwh";
    public static final String KWH_ACCUM_MONTH = "AccumMonthKwh";
    public static final String KWH_ACCUM_YEAR = "AccumYearKwh";
    public abstract float getDegreeByNum(String table, String foreign, int route, int num);
    public abstract int findLastNum(String table,String foreign, int route);
}
