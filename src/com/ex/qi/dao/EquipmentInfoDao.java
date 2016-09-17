package com.ex.qi.dao;

import com.ex.qi.entity.EquipmentInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sunline on 2016/8/22.
 */
public interface EquipmentInfoDao {
    boolean addConfig(EquipmentInfo equipmentInfo) throws SQLException;
    boolean updateConfig(EquipmentInfo equipmentInfo) throws SQLException;
    boolean deleteConfig(String fId) throws SQLException;
    int getRoutes(String id);
    List<EquipmentInfo> findConfigByForeign(String foreign);
}
