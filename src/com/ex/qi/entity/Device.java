package com.ex.qi.entity;

import java.util.Date;

/**
 * Created by sunline on 2016/8/22.
 */
public class Device {
    private String mId = "";
    private String mName = "";
    private String mPort = "";
    private String mRate = "";
    private String mAddr = "";
    private String mTimeOut = "";
    private String mDataBits = "";
    private String mStopBits = "";
    private String mParity = "";
    private String mState = "";
    private String mDelay = "";

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    private Date mDate;

    public Device() {
    }

    public Device(String id, String name, String port, String rate, String addr) {
        mId = id;
        mName = name;
        mPort = port;
        mRate = rate;
        mAddr = addr;
    }

    public Device(String id, String name, String port,
                  String rate, String addr, String timeOut, String dataBits,
                  String stopBits, String parity, String state, String delay) {
        mId = id;
        mName = name;
        mPort = port;
        mRate = rate;
        mAddr = addr;
        mTimeOut = timeOut;
        mDataBits = dataBits;
        mStopBits = stopBits;
        mParity = parity;
        mState = state;
        mDelay = delay;
    }

    public void setTimeOut(String timeOut) {
        mTimeOut = timeOut;
    }

    public void setDataBits(String dataBits) {
        mDataBits = dataBits;
    }

    public void setStopBits(String stopBits) {
        mStopBits = stopBits;
    }

    public void setParity(String parity) {
        mParity = parity;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPort() {
        return mPort;
    }

    public void setPort(String port) {
        mPort = port;
    }

    public String getRate() {
        return mRate;
    }

    public void setRate(String rate) {
        mRate = rate;
    }

    public String getAddr() {
        return mAddr;
    }

    public void setAddr(String addr) {
        mAddr = addr;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getDelay() {
        return mDelay;
    }

    public void setDelay(String delay) {
        mDelay = delay;
    }

    public String getTimeOut() {
        return mTimeOut;
    }

    public String getDataBits() {
        return mDataBits;
    }

    public String getStopBits() {
        return mStopBits;
    }

    public String getParity() {
        return mParity;
    }
}