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
    private PreparedStatement preStatement;
    private ResultSet resultSet;
    private DBUtils util = new DBUtils();

    @Override
    public ResultSet query(String sql, Object... obj) {
        conn = util.getConn();
        try {
            preStatement = conn.prepareStatement(sql);
            if (null != obj) {
                for (int i = 0; i < obj.length; i++) {
                    preStatement.setObject((i + 1), obj[i]);
                }
                return preStatement.executeQuery();
            }
        } catch (SQLException e) {

        }
        util.closeConn(conn, preStatement, resultSet);
        return null;
    }

    @Override
    public boolean update(String sql, Object... obj) throws SQLException {
        conn = util.getConn();
        preStatement = conn.prepareStatement(sql);
        for (int i = 0; i < obj.length; i++) {
            preStatement.setObject((i + 1), obj[i]);
        }
        int temp = preStatement.executeUpdate();
        if (0 < temp) {
            return true;
        }
        util.closeConn(conn, preStatement);
        return false;
    }

    @Override
    public boolean execute(String sql) {
        try {
            conn = util.getConn();
            return preStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.closeConn(conn, preStatement);
        }
        return false;
    }

    public boolean tableIsExists(String table) {

        String sql = "SELECT COUNT(*) as result FROM sysobjects WHERE id = OBJECT_ID('" + table + "')";
        try {
            ResultSet set = query(sql);
            if (set.next()) {
                int result = set.getInt("result");
                if (0 < result) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isExists() {
        String sql = "DECLARE num number;" +
                "BEGIN " +
                "SELECT \"COUNT\"(1) into num FROM user_tables where table_name='EQUIPMENT';\n" +
                "end;";
        return false;

    }
}
