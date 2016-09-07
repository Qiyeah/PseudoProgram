package com.ex.qi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/8/17.
 */
public interface BaseDao {

    /**
     * ??????е???
     * @param sql
     * @param obj
     * @return
     */
    ResultSet query(String sql, Object... obj) throws SQLException;

    /**
     * ???±???????????????
     * @param sql
     * @param obj
     * @return
     */
    boolean update(String sql, Object... obj) throws SQLException;
}
