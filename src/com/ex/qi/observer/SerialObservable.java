package com.ex.qi.observer;

import java.util.Observable;

/**
 * Created by sunline on 2016/7/20.
 */
public class SerialObservable extends Observable {
    private int data = 0;
    public int getData(){
        return data;
    }
    public void setData(int value){
        if (this.data != value){
            this.data = value;
            setChanged();
            notifyObservers();//只有在setChanged()之后调用，notifyObservers()才会去调用update()，否则什么都不做。
        }
    }
}
