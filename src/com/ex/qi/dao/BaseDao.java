package com.ex.qi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/8/17.
 */
public interface BaseDao {


    ResultSet query(String sql, Object... obj);

    boolean update(String sql, Object... obj) throws SQLException;

    boolean execute(String sql);
}
