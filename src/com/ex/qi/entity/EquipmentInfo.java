package com.ex.qi.entity;

/**
 * Created by sunline on 2016/8/22.
 */
public class EquipmentInfo {
    private String mId;
    private int mRoute;
    private String mPathName;
    private int mPathAttr;
    private String fId;
    private int mPer;
    private int mSymbol;

    public EquipmentInfo() {
    }

    public EquipmentInfo(String foreign, int route, int pathAttr, int per, int symbol) {
        fId = foreign;
        mRoute = route;
        mPathAttr = pathAttr;
        mPer = per;
        mSymbol = symbol;
    }

    public EquipmentInfo(String id , String fId,  int route, String pathName, int pathAttr,int per, int symbol) {
        mId = id;
        mRoute = route;
        mPathName = pathName;
        mPathAttr = pathAttr;
        this.fId = fId;
        mPer = per;
        mSymbol = symbol;
    }

    public int getSymbol() {
        return mSymbol;
    }

    public void setSymbol(int symbol) {
        mSymbol = symbol;
    }

    public int getPer() {
        return mPer;
    }

    public void setPer(int per) {
        this.mPer = per;
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

    public String getPathName() {
        return mPathName;
    }

    public void setPathName(String pathName) {
        mPathName = pathName;
    }

    public int getPathAttr() {
        return mPathAttr;
    }

    public void setPathAttr(int pathAttr) {
        mPathAttr = pathAttr;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }
}
