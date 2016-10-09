package com.ex.qi.dao.daoImpl;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.dao.EquipmentDao;
import com.ex.qi.entity.Equipment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sunline on 2016/8/19.
 */
public class EquipmentDaoImpl extends BaseDaoImpl implements EquipmentDao {
    @Override
    public boolean addEquipment(Equipment equipment) {
        String sql = "";
        Object[] params = null;
        sql = "insert into Equipment(id, name, port, rate, addr,timeout,data,stop,parity,switch,delayed,dt) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        params = new Object[]{equipment.getId(), equipment.getName(), equipment.getPort(),
                equipment.getRate(), equipment.getAddr(), equipment.getTimeOut(), equipment.getDataBits(), equipment.getStopBits()
                , equipment.getParity(), equipment.getState(), equipment.getDelay(),equipment.getDate()};
        try {
            return update(sql, params);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return false;
    }
    public boolean updateEquipment(Equipment equipment) {
//        System.out.println("id = "+equipment.getId());
//        System.out.println("name = "+equipment.getName());
//        System.out.println("port = "+equipment.getPort());
//        System.out.println("rate = "+equipment.getRate());
//        System.out.println("addr = "+equipment.getAddr());
//        System.out.println("timeout = "+equipment.getTimeOut());
//        System.out.println("data = "+equipment.getDataBits());
//        System.out.println("stop = "+equipment.getStopBits());
//        System.out.println("parity = "+equipment.getParity());
//        System.out.println("state = "+equipment.getState());
//        System.out.println("delay = "+equipment.getDelay());
        String sql = "";
        Object[] params = null;
        sql = "update Equipment set name = ?, port = ?, rate = ?, addr = ?,timeout = ?" +
                ",data = ?,stop = ?,parity = ?,switch = ?,delayed = ? where id = ? ";
        params = new Object[]{equipment.getName(),
                equipment.getPort(),
                equipment.getRate(),
                equipment.getAddr(),
                equipment.getTimeOut(),
                equipment.getDataBits(),
                equipment.getStopBits() ,
                equipment.getParity(),
                equipment.getState(),
                equipment.getDelay(),
                equipment.getId()};
        try {
            return update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public Equipment findEquipment(String id){
        String sql = "select * from Equipment where id = ?";
        ResultSet result = query(sql,id);
        Equipment equipment = new Equipment();
        try {
            if (result.next()){
                equipment.setId(result.getString("id"));
                equipment.setName(result.getString("name"));
                equipment.setPort(result.getString("port"));
                equipment.setRate(result.getString("rate"));
                equipment.setAddr(result.getString("addr"));
                equipment.setTimeOut(result.getString("timeout"));
                equipment.setDataBits(result.getString("data"));
                equipment.setStopBits(result.getString("stop"));
                equipment.setParity(result.getString("parity"));
                equipment.setDelay(result.getString("delay"));
                equipment.setState(result.getString("switch"));
                equipment.setDate(result.getDate("dt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipment;
    }

    @Override
    public Equipment[] findAllEquipments() {
        String sql = "SELECT * from Equipment";
        ResultSet result = query(sql);
        List<Equipment> list = new ArrayList<Equipment>();
        Equipment equipment = null;
        try {
            while (result.next()){
                equipment = new Equipment();
                equipment.setId(result.getString("id"));
                equipment.setName(result.getString("name"));
                equipment.setPort(result.getString("port"));
                equipment.setRate(result.getString("rate"));
                equipment.setAddr(result.getString("addr"));
                equipment.setTimeOut(result.getString("timeout"));
                equipment.setDataBits(result.getString("data"));
                equipment.setStopBits(result.getString("stop"));
                equipment.setParity(result.getString("parity"));
                equipment.setDelay(result.getString("delayed"));
                equipment.setState(result.getString("switch"));
                equipment.setDate(result.getDate("dt"));
                list.add(equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new Equipment[0]);
    }
    public boolean isExsits(String id){
        String sql = "select count(id) as num from Equipment where id = ?";
        ResultSet set = query(sql,id);
        try {
            while (set.next()){
                int num = set.getInt("num");
                if (0 < num){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public boolean deleteEquipment(String id){
        String sql = "delete from Equipment where id = ? ";
        try {
            return update(sql,id);
        } catch (SQLException e) {

        }
        return false;
    }
}
