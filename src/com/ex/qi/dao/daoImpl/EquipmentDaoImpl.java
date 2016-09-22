package com.ex.qi.dao.daoImpl;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.dao.EquipmentDao;
import com.ex.qi.entity.Equipment;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    @Override
    public ResultSet queryDevice(String id) throws SQLException {
        String sql = "select * from Equipment where id = ?";
        return query(sql, id);
    }

    @Override
    public ResultSet queryAllDevice() {
        String sql = "SELECT * from Equipment;";
        return query(sql);
    }

}
