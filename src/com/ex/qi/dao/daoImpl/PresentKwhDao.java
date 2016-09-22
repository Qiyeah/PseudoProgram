package com.ex.qi.dao.daoImpl;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.dao.BaseKwhDao;
import com.ex.qi.entity.PresentKwh;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/9/6.
 */
public class PresentKwhDao extends BaseKwhDao {

    public boolean addDataAtPoint(String table,PresentKwh kwh) {
        String sql = "insert into "+table+" (id,fk,route,degree,point,num) values(?,?,?,?,?,?) ";
        try {
            return update(sql, new Object[]{kwh.getId(), kwh.getfId(), kwh.getRoute(), kwh.getDegree(),kwh.getPoint(),kwh.getNum()});
        } catch (SQLException e) {
            //System.out.println("PresentKwhDao数据添加失败！");
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateDataAtPoint(String table, float degree, String foreign, int route,int lastPoint,int currentPoint,int num) {
        String sql = "update  "+table+" set degree = ?,point = ?,dt = ? where fk = ? and route = ? and point = ? and num = ? ";
        try {
            return update(sql, new Object[]{degree,currentPoint,new Date(System.currentTimeMillis()), foreign, route,lastPoint,num});
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("数据更新失败！");
        }
        return false;
    }
    public float getDegreeByNum(String table, String foreign, int route, int num) {
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



    @Override
    public int findLastNum(String table, String foreign, int route) {
        return 1;
    }


    public int findTotalByRoute(String table,String foreign, int route) {
        int total = 0;
        String sql = "select count(id) as total from "+table+" where fk = ? and route = ?";
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


    public PresentKwh findInfoByNum(String table, String foreign, int route) {
        PresentKwh kwh = new PresentKwh();
        String sql = "select top 1 degree,point,num from "+table+" where fk = ? and route = ? order by num desc ";
        ResultSet result = query(sql, new Object[]{foreign, route});
        try {
            if (result.next()) {
                float degree = result.getFloat("degree");
                int point= result.getInt("point");
                int num = result.getInt("num");
                kwh.setDegree(degree);
                kwh.setPoint(point);
                kwh.setNum(num);
            }
        } catch (SQLException e) {

        }
        return kwh;
    }

}

/* public int findLatestPoint(String table,String foreign, int route, int num) {
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
    }*/
/*public int findLatestNum(String table, String foreign, int route) {
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
/*public float findLatestData(String table, String foreign, int route) {
        String sql = "select degree from "+table+" where fk = ? and route = ? order by num desc  ";
        ResultSet set = query(sql, new Object[]{foreign, route});
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
    }*/

/*@Override
    public float getStartDegree(String table, String foreign, int route) {
        String sql = "select degree from  "+table+"  where fk = ? and route = ? and num = 0 ";
        ResultSet resultSet = query(sql, new Object[]{foreign, route});
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

    @Override
    public float getStopDegree(String table, String foreign, int route) {
        String sql = "select degree from  "+table+"  where fk = ? and route = ? and num = 1 ";
        ResultSet resultSet = query(sql, new Object[]{foreign, route});
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
    }*/