package com.ex.qi.entity.json;

import java.util.List;

/**
 * Created by sunlines on 2016/10/9.
 */
public class EquipmentJSON {
    private List<EquipmentArray> list;

    public EquipmentJSON() {
    }

    public EquipmentJSON(List<EquipmentArray> list) {
        this.list = list;
    }

    public  List<EquipmentArray> getList() {
        return list;
    }

    public void setList( List<EquipmentArray> list) {
        this.list = list;
    }
}
