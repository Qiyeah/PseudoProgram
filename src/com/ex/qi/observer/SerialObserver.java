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

}
