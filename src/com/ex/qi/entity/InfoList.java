package com.ex.qi.entity;

import java.util.List;

/**
 * Created by sunline on 2016/9/23.
 */
public class InfoList {
    EquipmentInfo[] infos;

    public InfoList() {
    }

    public InfoList(EquipmentInfo[] infos) {
        this.infos = infos;
    }

    public EquipmentInfo[] getInfos() {
        return infos;
    }

    public void setInfos(EquipmentInfo[] infos) {
        this.infos = infos;
    }
}
