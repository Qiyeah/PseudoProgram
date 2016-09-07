package com.ex.qi.entity;

import gnu.io.SerialPort;

/**
 * Created by sunline on 2016/8/18.
 */
public class SerialPortParam {
    private String portName;
    private int rate;
    private int timeout;
    private String databits  = "" + SerialPort.DATABITS_8;
    private String stopbits = "" + SerialPort.STOPBITS_1;
    private int parityInt = SerialPort.PARITY_NONE;
    private int delayread;
    public SerialPortParam() {

    }

    public SerialPortParam(String portName, int rate, int timeout, int delayread) {
        this.portName = portName;
        this.rate = rate;
        this.timeout = timeout;
        this.delayread = delayread;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setDelayread(int delayread) {
        this.delayread = delayread;
    }

    public String getPortName() {
        return portName;
    }

    public int getRate() {
        return rate;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getDatabits() {
        return databits;
    }

    public String getStopbits() {
        return stopbits;
    }

    public int getParityInt() {
        return parityInt;
    }

    public int getDelayread() {
        return delayread;
    }
}
