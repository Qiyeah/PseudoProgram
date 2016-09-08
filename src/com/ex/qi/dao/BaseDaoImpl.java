package com.ex.qi.dao;

import com.ex.qi.db.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/8/18.
 */
public class BaseDaoImpl implements BaseDao {
    private Connection conn;
    private PreparedStatement pswitchment;
    private ResultSet resultSet;
    private DBUtils util = new DBUtils();

    @Override
    public ResultSet query(String sql, Object... obj){
        conn = util.getConn();
        try {
            pswitchment = conn.prepareStatement(sql);
            if (null != obj) {
                for (int i = 0; i < obj.length; i++) {
                    pswitchment.setObject((i + 1), obj[i]);
                }
                return pswitchment.executeQuery();
            }
        } catch (SQLException e) {

        }
        util.closeConn(conn, pswitchment, resultSet);
        return null;
    }

    @Override
    public boolean update(String sql, Object... obj) throws SQLException {
        conn = util.getConn();
        pswitchment = conn.prepareStatement(sql);
        for (int i = 0; i < obj.length; i++) {
            pswitchment.setObject((i + 1), obj[i]);
        }
        int temp = pswitchment.executeUpdate();
        if (0 < temp) {
            return true;
        }
        util.closeConn(conn, pswitchment);
        return false;
    }

    public boolean tableIsExists(String table) {

        String sql = "SELECT COUNT(*) as result FROM sysobjects WHERE id = OBJECT_ID('" + table + "')";
        try {
            ResultSet set = query(sql);
            if (set.next()){
                int result = set.getInt("result");
                if (0<result){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
