package com.ex.qi.dao.daoImpl;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.dao.EquipmentInfoDao;
import com.ex.qi.entity.EquipmentInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunline on 2016/8/22.
 */
public class EquipmentInfoDaoImpl extends BaseDaoImpl implements EquipmentInfoDao {

    @Override
    public boolean addConfig(EquipmentInfo equipmentInfo){
        String sql = "";
        Object[] params = null;
        sql = "insert into EQUIPMENTINFO(id,route,name,attr,fk,per,symbol) values(?,?,?,?,?,?,?)";
        params = new Object[]{equipmentInfo.getId(), equipmentInfo.getRoute(), equipmentInfo.getPathName(),
                equipmentInfo.getPathAttr(), equipmentInfo.getfId(), equipmentInfo.getPer(), equipmentInfo.getSymbol()};
        try {
            return update(sql, params);
        } catch (SQLException e) {

        }
        return false;
    }

    @Override
    public boolean updateConfig(EquipmentInfo equipmentInfo) throws SQLException {
        String sql = "update EQUIPMENTINFO(name,attr,per) values(?,?,?) where fk = ? and route = ? ";
        Object[] params = new Object[]{equipmentInfo.getPathName(), equipmentInfo.getPathAttr(), equipmentInfo.getPer(), equipmentInfo.getfId(), equipmentInfo.getRoute()};
        return update(sql, params);
    }

    @Override
    public boolean deleteConfig(String fId) throws SQLException {
        String sql = "delete from Equipment where fk = fId";
        return update(sql, fId);
    }


    @Override
    public int getRoutes(String id) {
        String sql = "select count(route) as routes from EQUIPMENTINFO where fk = ? ";
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

    public List<EquipmentInfo> findConfigByForeign(String foreign){
        List<EquipmentInfo> list = new ArrayList<>();
        String sql = "select fk,route,attr,per,symbol from EQUIPMENTINFO";
        ResultSet set = query(sql);
        try {
            while (set.next()){
                String fk = set.getString("fk");
                int route = set.getInt("route");
                int attr = set.getInt("attr");
                int per = set.getInt("per");
                int symbol = set.getByte("symbol");
                list.add(new EquipmentInfo(fk,route,attr,per,symbol));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return list;
    }
}
