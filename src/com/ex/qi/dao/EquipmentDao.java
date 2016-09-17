package com.ex.qi.dao;


import com.ex.qi.entity.Equipment;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/8/18.
 */
public interface EquipmentDao {
    //name,port,rate,addr,switch,delay
    boolean addEquipment(Equipment equipment) throws SQLException;
    ResultSet queryDevice(String id) throws SQLException;
    ResultSet queryAllDevice() throws SQLException;
}
