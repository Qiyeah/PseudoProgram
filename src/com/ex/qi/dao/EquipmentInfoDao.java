package com.ex.qi.dao;

import com.ex.qi.entity.EquipmentInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sunline on 2016/8/22.
 */
public interface EquipmentInfoDao {
    boolean addEquipmentInfo(EquipmentInfo equipmentInfo);
    boolean updateEquipmentInfo(EquipmentInfo equipmentInfo);
    boolean deleteEquipmentInfos(String fId);
    int getRoutes(String id);
    EquipmentInfo[] findEquipmentInfos(String foreign);
}
