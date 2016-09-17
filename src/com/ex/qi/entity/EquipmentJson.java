package com.ex.qi.entity;

import java.util.List;

/**
 * Created by qi on 2016/9/17.
 */
public class EquipmentJson {
    public List<Equipment> equipments;

    public EquipmentJson() {
    }

    public EquipmentJson(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
}
