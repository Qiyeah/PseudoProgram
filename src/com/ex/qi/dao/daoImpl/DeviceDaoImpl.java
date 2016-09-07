package com.ex.qi.dao.daoImpl;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.dao.DeviceDao;
import com.ex.qi.entity.Device;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/8/19.
 */
public class DeviceDaoImpl extends BaseDaoImpl implements DeviceDao {
    @Override
    public boolean addDevice(Device device) {
        String sql = "";
        Object[] params = null;
        if (device.getTimeOut().equals("") || device.getDataBits().equals("") || device.getStopBits().equals("")
                || device.getParity().equals("") || device.getState().equals("") || device.getDelay().equals("")) {
            //_id,name,port,rate,addr,switch,delay
            sql = "insert into Device(_id,name,port,rate,addr) values(?,?,?,?,?)";
            params = new Object[]{device.getId(), device.getName(), device.getPort(),
                    device.getRate(), device.getAddr()};
        } else {
            //_id, name, port, rate, addr,timeout,databits,stopbits,parity,switch,delay
            sql = "insert into Device(_id, name, port, rate, addr,timeout,data,stop,parity,switch,delayed) values(?,?,?,?,?,?,?,?,?,?,?)";
            params = new Object[]{device.getId(), device.getName(), device.getPort(),
                    device.getRate(), device.getAddr(), device.getTimeOut(), device.getDataBits(), device.getStopBits()
                    , device.getParity(), device.getState(), device.getDelay()};
        }
        try {
            return update(sql, params);
        } catch (SQLException e) {

        }
        return false;
    }

    @Override
    public ResultSet queryDevice(String id) throws SQLException {
        String sql = "select * from Device where _id = ?";
        return query(sql, id);
    }

    @Override
    public ResultSet queryAllDevice() {
        String sql = "SELECT * from Device;";
        return query(sql);
    }

    @Override
    public boolean delDevice(String id) throws SQLException {
        String sql = "delete from Device where _id = ?";
        return update(sql, id);
    }

    @Override
    public boolean updateAll(Device device) throws SQLException {
        String sql = "update Device(name,port,rate,addr,switch,delay) values(?,?,?,?,?,?) where _id = ? ";
        Object[] params = new Object[]{device.getName(), device.getPort(),
                device.getRate(), device.getAddr(), device.getState(), device.getDelay(), device.getId()};
        return update(sql, params);
    }

    @Override
    public boolean updateName(String id, String name) throws SQLException {
        String sql = "update Device(name) values(?) where _id = ? ";
        Object[] params = new Object[]{name, id};
        return update(sql, params);
    }

    @Override
    public boolean updatePort(String id, String port) throws SQLException {
        String sql = "update Device(port) values(?) where _id = ? ";
        Object[] params = new Object[]{port, id};
        return update(sql, params);
    }

    @Override
    public boolean updateRate(String id, String rate) throws SQLException {
        String sql = "update Device(rate) values(?) where _id = ? ";
        Object[] params = new Object[]{rate, id};
        return update(sql, params);
    }

    @Override
    public boolean updateAddr(String id, String addr) throws SQLException {
        String sql = "update Device(addr) values(?) where _id = ? ";
        Object[] params = new Object[]{addr, id};
        return update(sql, params);
    }

    @Override
    public boolean updateState(String id, String state) throws SQLException {
        String sql = "update Device(switch) values(?) where _id = ? ";
        Object[] params = new Object[]{state, id};
        return update(sql, params);
    }

    @Override
    public boolean updateTimeout(String id, String timeout) throws SQLException {
        String sql = "update Device(timeout) values(?) where _id = ? ";
        Object[] params = new Object[]{timeout, id};
        return update(sql, params);
    }

    @Override
    public boolean updateDataBits(String id, String databits) throws SQLException {
        String sql = "update Device(timeout) values(?) where _id = ? ";
        Object[] params = new Object[]{databits, id};
        return update(sql, params);
    }

    @Override
    public boolean updateStopBits(String id, String stopbits) throws SQLException {
        String sql = "update Device(timeout) values(?) where _id = ? ";
        Object[] params = new Object[]{stopbits, id};
        return update(sql, params);
    }

    @Override
    public boolean updateParity(String id, String parity) throws SQLException {
        String sql = "update Device(parity) values(?) where _id = ? ";
        Object[] params = new Object[]{parity, id};
        return update(sql, params);
    }

    @Override
    public boolean updateDelay(String id, String delay) throws SQLException {
        String sql = "update Device(delay) values(?) where _id = ? ";
        Object[] params = new Object[]{delay, id};
        return update(sql, params);
    }
}
