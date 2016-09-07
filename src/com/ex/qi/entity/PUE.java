package com.ex.qi.entity;

/**
 * Created by sunline on 2016/9/2.
 */
public class PUE {
    float mDayPue;
    float mMonthPue;
    float mYearPue;
    float mPastDayPue;
    float mPastMonthPue;
    float mPastYearPue;

    public PUE(float dayPue, float monthPue, float yearPue, float pastDayPue, float pastMonthPue, float pastYearPue) {
        mDayPue = dayPue;
        mMonthPue = monthPue;
        mYearPue = yearPue;
        mPastDayPue = pastDayPue;
        mPastMonthPue = pastMonthPue;
        mPastYearPue = pastYearPue;
    }

    public PUE() {
    }

    public float getDayPue() {
        return mDayPue;
    }

    public void setDayPue(float dayPue) {
        mDayPue = dayPue;
    }

    public float getMonthPue() {
        return mMonthPue;
    }

    public void setMonthPue(float monthPue) {
        mMonthPue = monthPue;
    }

    public float getYearPue() {
        return mYearPue;
    }

    public void setYearPue(float yearPue) {
        mYearPue = yearPue;
    }

    public float getPastDayPue() {
        return mPastDayPue;
    }

    public void setPastDayPue(float pastDayPue) {
        mPastDayPue = pastDayPue;
    }

    public float getPastMonthPue() {
        return mPastMonthPue;
    }

    public void setPastMonthPue(float pastMonthPue) {
        mPastMonthPue = pastMonthPue;
    }

    public float getPastYearPue() {
        return mPastYearPue;
    }

    public void setPastYearPue(float pastYearPue) {
        mPastYearPue = pastYearPue;
    }
}
