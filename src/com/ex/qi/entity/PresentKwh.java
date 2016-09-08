package com.ex.qi.entity;

import java.sql.Date;

/**
 * Created by sunline on 2016/9/4.
 */
public class PresentKwh {
    private String mId;
    /**
     * 对应设备ID
     */
    private String fId;
    /**
     * 通道号
     */
    private int mRoute;
    /**
     * 电度值
     */
    private float mDegree;
    /**
     * 某时或某天
     */
    private int mPoint;

    private int num;

    private Date mDate;
    /**
     * 对应数据库中的表： DayKwh,MothKwh,YearKwh
     * @param id
     * @param fId
     * @param route
     * @param degree
     * @param point
     */
    public PresentKwh(String id, String fId, int route, float degree, int point,int num) {
        this.num = num;
        mId = id;
        this.fId = fId;
        mRoute = route;
        mDegree = degree;
        mPoint = point;
    }


    public PresentKwh() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getRoute() {
        return mRoute;
    }

    public void setRoute(int route) {
        mRoute = route;
    }

    public float getDegree() {
        return mDegree;
    }

    public void setDegree(float degree) {
        mDegree = degree;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public int getPoint() {
        return mPoint;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPoint(int point) {
        mPoint = point;
    }
}
