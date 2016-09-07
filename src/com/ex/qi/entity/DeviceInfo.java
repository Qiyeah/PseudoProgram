package com.ex.qi.entity;

/**
 * Created by sunline on 2016/8/22.
 */
public class DeviceInfo {
    private String mId;
    private int mPath;
    private String mPathName;
    private int mPathAttr;
    private String fId;
    private int mPer;
    private int mSymbol;

    public DeviceInfo() {
    }

    public DeviceInfo(String foreign,int path, int pathAttr, int per, int symbol) {
        fId = foreign;
        mPath = path;
        mPathAttr = pathAttr;
        mPer = per;
        mSymbol = symbol;
    }

    public DeviceInfo(String id, int path, String pathName, int pathAttr, String fId, int per, int symbol) {
        mId = id;
        mPath = path;
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

    public int getPath() {
        return mPath;
    }

    public void setPath(int path) {
        mPath = path;
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
