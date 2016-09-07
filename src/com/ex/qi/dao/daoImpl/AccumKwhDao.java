package com.ex.qi.dao.daoImpl;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.entity.AccumKwh;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/9/6.
 */
public class AccumKwhDao extends BaseDaoImpl {
    public static final String KWH_ACCUM_DAY = "AccumDayKwh";
    public static final String KWH_ACCUM_MONTH = "AccumMonthKwh";
    public static final String KWH_ACCUM_YEAR = "AccumYearKwh";
    public boolean addDataAtNum(String table,AccumKwh kwh) {
        String sql = "insert into "+table+" (_id,fk,route,degree,num,point) values(?,?,?,?,?,?) ";
        try {
            return update(sql, new Object[]{kwh.getId(), kwh.getfId(), kwh.getRoute(), kwh.getDegree(), kwh.getNum(), kwh.getPoint()});
        } catch (SQLException e) {
            System.out.println("数据添加失败！");
        }
        return false;
    }
    public boolean updateDataAtNum(String table, float degree, String foreign, int route, int num, int point) {
        String sql = "update  "+table+" set degree = ? where fk = ? and route = ? and num = ? and point = ? ";
        try {
            return update(sql, new Object[]{degree, foreign, route, num, point});
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("数据更新失败！");
        }
        return false;
    }
    public int findLatestPoint(String table,String foreign, int route, int num) {
        int point = -1;
        String sql = "select point from "+table+" where fk = ? and route = ? and num = ? ";
        ResultSet result = query(sql, new Object[]{foreign, route, num});
        try {
            if (result.next()) {
                point = result.getInt("point");
            }
        } catch (SQLException e) {

        }
        return point;
    }

    public float getDegreeByInfo(String table,String foreign, int route, int num) {
        String sql = "select degree from  "+table+"  where fk = ? and route = ? and num = ? ";
        ResultSet resultSet = query(sql, new Object[]{foreign, route, num});
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
    public float findLatestData(String table, String foreign, int route) {
        String sql = "select top 1 degree from "+table+" where fk = ? and route = ? order by num desc ";
        ResultSet set = query(sql, new Object[]{foreign, route});
        try {
            while (set.next()) {
                return set.getFloat("degree");
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("历史数据查询失败！");
        }
        return 0f;
    }

    public boolean deleteEarliestDataByInfo(String table,String forein, int route) {
        boolean flag = false;
        String sql = "delete "+table+" WHERE num = (" +
                "SELECT TOP 1 num FROM "+table+" WHERE fk=? and route = ? ORDER BY num)";
        try {
            flag = update(sql, new Object[]{forein, route});
        } catch (SQLException e) {
            System.out.println("删除最早一条数据失败");
            return false;
        }
        return flag;
    }

    public int findTotalByRoute(String table,String foreign, int route) {
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
    }
    public int findsLatestNum(String table,String foreign, int route) {
        int num = -1;
        String sql = "select top 1 num from "+table+" where fk = ? and route = ? order by num desc ";
        ResultSet result = query(sql, new Object[]{foreign, route});
        try {
            if (result.next()) {
                num = result.getInt("num");
            }
        } catch (SQLException e) {

        }
        return num;
    }
}
