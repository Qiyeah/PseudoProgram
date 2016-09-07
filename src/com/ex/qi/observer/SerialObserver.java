package com.ex.qi.observer;

import com.ex.qi.dao.BaseDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by sunline on 2016/7/20.
 */
public class SerialObserver implements Observer {
    public SerialObserver() {
    }

    int count = 0;

    /**
     * 接收数据
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    /*public float[] getDegrees() {
        String temp = "";
        float[] degrees = new float[(data.size()-5)/4];
        for (int i = 3; i < data.size() -2; i++) {
            temp += data.get(i);
            if ((i - 2)%4 == 0){
                degrees[(i-3)/4] = Integer.parseInt(temp,16)/10f;
                temp = "";
            }
        }
        return degrees;
    }*/
}
