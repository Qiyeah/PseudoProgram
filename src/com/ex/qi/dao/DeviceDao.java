package com.ex.qi.dao;


import com.ex.qi.entity.Device;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/8/18.
 */
public interface DeviceDao {
    //name,port,rate,addr,switch,delay
    boolean addDevice(Device device) throws SQLException;
    ResultSet queryDevice(String id) throws SQLException;
    ResultSet queryAllDevice() throws SQLException;
    boolean delDevice(String id) throws SQLException;
    boolean updateAll(Device device) throws SQLException;
    boolean updateName(String id,String name) throws SQLException;
    boolean updatePort(String id,String port) throws SQLException;
    boolean updateRate(String id,String rate) throws SQLException;
    boolean updateAddr(String id,String addr) throws SQLException;
    boolean updateState(String id,String state) throws SQLException;
    boolean updateTimeout(String id,String timeout) throws SQLException;
    boolean updateDataBits(String id,String databits) throws SQLException;
    boolean updateStopBits(String id,String stopbits) throws SQLException;
    boolean updateParity(String id,String parity) throws SQLException;
    boolean updateDelay(String id,String delay) throws SQLException;
}
