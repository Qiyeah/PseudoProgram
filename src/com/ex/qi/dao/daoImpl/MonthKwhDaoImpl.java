package com.ex.qi.dao.daoImpl;
import com.ex.qi.dao.BaseKwhDaoImpl;
import com.ex.qi.entity.PresentKwh;
import com.ex.qi.entity.RealKwh;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/9/2.
 */
public class MonthKwhDaoImpl extends BaseKwhDaoImpl {

    public boolean addDataAtPoint(PresentKwh kwh){
        String sql = "insert into MonthKwh (_id,fk,route,degree,point) values(?,?,?,?,?) ";
        try {
            return update(sql,new Object[]{kwh.getId(),kwh.getfId(),kwh.getRoute(),kwh.getDegree(),kwh.getPoint()});
        } catch (SQLException e) {

        }
        return false;
    }
    public float getDegreeByInfo(String foreign, int route, int point) {
        String sql = "select degree from  MonthKwh  where fk = ? and route = ? and point = ? ";
        ResultSet resultSet = query(sql,new Object[]{foreign,route,point});
        try {
            while (resultSet.next()){
                float degree = resultSet.getFloat("degree");
                if (0 != degree){
                    return degree;
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return 0f;
    }
    public boolean updateDataAtpoint(float degree,String foreign, int route, int point) {
        String sql = "update  MonthKwh set degree = ? , dt = ? where fk = ? and route = ? and point = ? ";
        try {
            return update(sql,new Object[]{degree,new Date(System.currentTimeMillis()),foreign,route,point});
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return false;
    }
    public boolean routeHasEnd(String foreign, int route) {
        String sql = "select count(*) as exist from MonthKwh where fk = ? and route = ? and point = 1 ";
        ResultSet resultSet = query(sql,new Object[]{foreign,route});
        try {
            while (resultSet.next()){
                int exist = resultSet.getInt("exist");
                if (0 != exist){
                    return true;
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return false;
    }

    public float queryHistoryData(String foreign, int route) {
        String sql = "select top 1 degree from MonthKwh where fk = ? and route = ? order by point desc ";
        ResultSet set = query(sql,new Object[]{foreign,route});
        try {
            while (set.next()){
                return set.getFloat("degree");
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("MonthKwh?????????????");
        }
        return 0f;
    }
    public boolean routeHasStart(String foreign, int route) {
        String sql = "select count(*) as exist from MonthKwh where fk = ? and route = ? and point = 0 ";
        ResultSet resultSet = query(sql,new Object[]{foreign,route});
        try {
            while (resultSet.next()){
                int exist = resultSet.getInt("exist");
                if (0 != exist){
                    return true;
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return false;
    }
}
