package com.ex.qi.dao.daoImpl;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.entity.PresentKwh;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/9/6.
 */
public class PresentKwhDao extends BaseDaoImpl {
    public static final String KWH_DAY = "DayKwh";
    public static final String KWH_MONTH = "MonthKwh";
    public static final String KWH_YEAR= "YearKwh";
    public boolean addDataAtPoint(String table,PresentKwh kwh) {
        String sql = "insert into "+table+" (_id,fk,route,degree,point) values(?,?,?,?,?) ";
        try {
            return update(sql, new Object[]{kwh.getId(), kwh.getfId(), kwh.getRoute(), kwh.getDegree(),kwh.getPoint()});
        } catch (SQLException e) {
            System.out.println("数据添加失败！");
        }
        return false;
    }
    public boolean updateDataAtPoint(String table, float degree, String foreign, int route, int point) {
        String sql = "update  "+table+" set degree = ? where fk = ? and route = ? and point = ? ";
        try {
            return update(sql, new Object[]{degree, foreign, route, point});
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("数据更新失败！");
        }
        return false;
    }
    public float getDegreeByInfo(String table,String foreign, int route, int point) {
        String sql = "select degree from  "+table+"  where fk = ? and route = ? and point = ? ";
        ResultSet resultSet = query(sql, new Object[]{foreign, route, point});
        try {
            while (resultSet.next()) {
                float degree = resultSet.getFloat("degree");
                if (0 != degree) {
                    return degree;
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return 0f;
    }
    public float findLatestData(String table, String foreign, int route,int point) {
        String sql = "select degree from "+table+" where fk = ? and route = ? and point = ? ";
        ResultSet set = query(sql, new Object[]{foreign, route,point});
        try {
            while (set.next()) {
                float degree = set.getFloat("degree");
                if (0 != degree) {
                    return degree;
                }
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("历史数据查询失败！");
        }
        return 0f;
    }
    /*public int findTotalByRoute(String table,String foreign, int route) {
        int total = 0;
        String sql = "select count(_id) as total from "+table+" where fk = ? and route = ?";
        ResultSet result = query(sql, new Object[]{foreign, route});
        try {
            if (result.next()) {
                total = result.getInt("total");
            }
        } catch (SQLException e) {
            return total;
        }
        return total;
    }*/
    /*public Date findLatestDay(String table,String foreign, int route){
        String sql = "select top 1 dt from "+table+"where fk = ? and route = ? ";
        ResultSet result = query(sql,new Object[]{foreign,route});
        try {
            if (result.next()){
                Date date = result.getDate("dt");
                return date;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
