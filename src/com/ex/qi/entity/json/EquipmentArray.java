package com.ex.qi.entity.json;

/**
 * Created by sunlines on 2016/10/9.
 */
public class EquipmentArray {
    private String key;
    private EquipmentConfig[] equipments;

    public EquipmentArray() {

    }

    public EquipmentArray(String key, EquipmentConfig[] equipments) {
        this.key = key;
        this.equipments = equipments;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public EquipmentConfig[] getEquipments() {
        return equipments;
    }

    public void setEquipments(EquipmentConfig[] equipments) {
        this.equipments = equipments;
    }
}
