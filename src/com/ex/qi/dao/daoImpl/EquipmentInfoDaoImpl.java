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
    public boolean addEquipmentInfo(EquipmentInfo equipmentInfo){
        Object[] params = null;
        String sql = "insert into EQUIPMENTINFO(id,fk,route,name,total_symbol,total_per,it_symbol,it_per) values(?,?,?,?,?,?,?,?)";
        params = new Object[]{equipmentInfo.getId(), equipmentInfo.getfId(), equipmentInfo.getRoute(),
                equipmentInfo.getRouteName(),equipmentInfo.getTotalSymbol(),equipmentInfo.getTotalPer(),
                equipmentInfo.getITSymbol(),equipmentInfo.getITPer()};
        try {
            return update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEquipmentInfo(EquipmentInfo info){
        String sql = "update EQUIPMENTINFO set name = ?,total_symbol = ?,total_per = ?,it_symbol = ?,it_per = ? " +
                "where id = ?";
        Object[] params = new Object[]{info.getRouteName(), info.getTotalSymbol(), info.getTotalPer(),
                info.getITSymbol(), info.getITPer(),info.getId()};
        try {
            return update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteEquipmentInfos(String fId) throws SQLException {
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

    public List<EquipmentInfo> findEquipmentInfos(String foreign){
        List<EquipmentInfo> list = new ArrayList<>();
        String sql = "select id,fk,name,route,total_symbol,total_per,it_symbol,it_per from EQUIPMENTINFO where fk = ?";
        ResultSet set = query(sql,foreign);
        try {
            while (set.next()){
                String id = set.getString("id");
                String fk = set.getString("fk");
                String name = set.getString("name");
                int route = set.getInt("route");
                int total_symbol = set.getInt("total_symbol");
                int total_per = set.getInt("total_per");
                int it_symbol = set.getInt("it_symbol");
                int it_per = set.getInt("it_per");
                list.add(new EquipmentInfo(id,fk,route,name,total_symbol,total_per,it_symbol,it_per));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return list;
    }
    public boolean isExists(String foreign,int route){
        String sql = "select count(route) as num from EQUIPMENTINFO where fk = ? and route = ? ";
        ResultSet set = query(sql,new Object[]{foreign,route});
        try {
            while (set.next()){
                int num = set.getInt("num");
                if (0 < num){
                    return true;
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return false;
    }
}
