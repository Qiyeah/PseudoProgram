package com.ex.qi.entity;

import java.util.List;

/**
 * Created by sunline on 2016/9/23.
 */
public class InfoList {
    List<EquipmentInfo> infos;

    public InfoList() {
    }

    public InfoList(List<EquipmentInfo> infos) {
        this.infos = infos;
    }

    public List<EquipmentInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<EquipmentInfo> infos) {
        this.infos = infos;
    }
}
