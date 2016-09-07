package com.ex.qi.dao;

import com.ex.qi.entity.DeviceInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sunline on 2016/8/22.
 */
public interface DeviceInfoDao {
    boolean addConfig(DeviceInfo deviceInfo) throws SQLException;
    boolean updateConfig(DeviceInfo deviceInfo) throws SQLException;
    boolean deleteConfig(String fId) throws SQLException;
    boolean updatePathName(String name,String fId,int path) throws SQLException;
    boolean updatePathAttr(int attr,String fId,int path) throws SQLException;
    boolean updatePathPer(int per,String fId,int path) throws SQLException;
    boolean updateSymbol(int symbol,String fId,int path) throws SQLException;
    int getRoutes(String id);
    List<DeviceInfo> findConfigByForeign(String foreign);
}
