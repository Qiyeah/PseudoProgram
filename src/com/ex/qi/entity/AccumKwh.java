package com.ex.qi.entity;

import java.sql.Date;

/**
 * Created by sunline on 2016/9/4.
 */
public class AccumKwh {
    String mId;
    String fId;
    int mRoute;
    float mDegree;
    int mPoint;
    int mNum;
    Date mDate;

    public AccumKwh() {
    }

    public AccumKwh(String id, String fId, int route, float degree, int num, int point) {
        mId = id;
        this.fId = fId;
        mRoute = route;
        mDegree = degree;
        mPoint = point;
        mNum = num;
    }

    public AccumKwh(String id, String fId, int route, float degree, int point, int num, Date date) {
        mId = id;
        this.fId = fId;
        mRoute = route;
        mDegree = degree;
        mPoint = point;
        mNum = num;
        mDate = date;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
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

    public int getPoint() {
        return mPoint;
    }

    public void setPoint(int point) {
        mPoint = point;
    }

    public int getNum() {
        return mNum;
    }

    public void setNum(int num) {
        mNum = num;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
