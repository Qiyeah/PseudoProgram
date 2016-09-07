package com.ex.qi.dao.daoImpl;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.dao.DeviceInfoDao;
import com.ex.qi.entity.DeviceInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunline on 2016/8/22.
 */
public class DeviceInfoDaoImpl extends BaseDaoImpl implements DeviceInfoDao {

    @Override
    public boolean addConfig(DeviceInfo deviceInfo){
        String sql = "";
        Object[] params = null;
        sql = "insert into DeviceInfo(_id,route,name,attr,fk,per,symbol) values(?,?,?,?,?,?,?)";
        System.out.println(deviceInfo.getPath());
        params = new Object[]{deviceInfo.getId(), deviceInfo.getPath(), deviceInfo.getPathName(),
                deviceInfo.getPathAttr(), deviceInfo.getfId(), deviceInfo.getPer(), deviceInfo.getSymbol()};
        try {
            return update(sql, params);
        } catch (SQLException e) {

        }
        return false;
    }

    @Override
    public boolean updateConfig(DeviceInfo deviceInfo) throws SQLException {
        String sql = "update DeviceInfo(name,attr,per) values(?,?,?) where fk = ? and path = ? ";
        Object[] params = new Object[]{deviceInfo.getPathName(), deviceInfo.getPathAttr(), deviceInfo.getPer(), deviceInfo.getfId(), deviceInfo.getPath()};
        return update(sql, params);
    }

    @Override
    public boolean deleteConfig(String fId) throws SQLException {
        String sql = "delete from Device where fk = fId";
        return update(sql, fId);
    }

    @Override
    public boolean updatePathName(String name, String fId, int route) throws SQLException {
        String sql = "update DeviceInfo set name=? WHERE fk = ? and route = ?";
        Object[] params = new Object[]{name, fId, route};
        return update(sql, params);
    }

    @Override
    public boolean updatePathAttr(int attr, String fId, int route) throws SQLException {
        String sql = "update DeviceInfo set attr=? WHERE fk = ? and route = ?";
        Object[] params = new Object[]{attr, fId, route};
        return update(sql, params);
    }

    @Override
    public boolean updatePathPer(int per, String fId, int path) throws SQLException {
        String sql = "update DeviceInfo set per=? WHERE fk = ? and path = ?";
        Object[] params = new Object[]{per, fId, path};
        return update(sql, params);
    }

    @Override
    public boolean updateSymbol(int symbol, String fId, int path) throws SQLException {
        String sql = "update DeviceInfo set symbol = ? WHERE fk = ? and path = ?";
        Object[] params = new Object[]{symbol, fId, path};
        return update(sql, params);
    }

    @Override
    public int getRoutes(String id) {
        String sql = "select count(route) as routes from DeviceInfo where fk = ? ";
        ResultSet set = query(sql,id);
        try {
            if (set.next()){
                return set.getInt("count");
            }
        } catch (SQLException e) {
           // e.printStackTrace();
        }
        return 0;
    }

    public List<DeviceInfo> findConfigByForeign(String foreign){
        List<DeviceInfo> list = new ArrayList<>();
        String sql = "select fk,route,attr,per,symbol from DeviceInfo";
        ResultSet set = query(sql);
        try {
            while (set.next()){
                String fk = set.getString("fk");
                int route = set.getInt("route");
                int attr = set.getInt("attr");
                int per = set.getInt("per");
                int symbol = set.getByte("symbol");
                list.add(new DeviceInfo(fk,route,attr,per,symbol));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return list;
    }
}
