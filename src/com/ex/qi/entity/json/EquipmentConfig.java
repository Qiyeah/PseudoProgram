package com.ex.qi.entity.json;

import com.ex.qi.entity.Equipment;
import com.ex.qi.entity.EquipmentInfo;

/**
 * Created by sunlines on 2016/10/9.
 */
public class EquipmentConfig {
    private Equipment equipments;
    private EquipmentInfo[] infos;

    public EquipmentConfig() {
    }

    public EquipmentConfig(Equipment equipments, EquipmentInfo[] infos) {
        this.infos = infos;
        this.equipments = equipments;
    }

    public Equipment getEquipments() {
        return equipments;
    }

    public void setEquipments(Equipment equipments) {
        this.equipments = equipments;
    }

    public EquipmentInfo[] getInfos() {
        return infos;
    }

    public void setInfos(EquipmentInfo[] infos) {
        this.infos = infos;
    }
}
