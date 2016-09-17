package com.ex.qi.entity;

import java.util.List;

/**
 * Created by qi on 2016/9/16.
 */
public class InfoJson {
    public List<EquipmentInfo> equipmentInfos;

    public InfoJson() {
    }

    public InfoJson(List<EquipmentInfo> list) {
        this.equipmentInfos = list;
    }

    public List<EquipmentInfo> getList() {
        return equipmentInfos;
    }

    public void setList(List<EquipmentInfo> list) {
        this.equipmentInfos = list;
    }
}
