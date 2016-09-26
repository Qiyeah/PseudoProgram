package test;

import com.ex.qi.dao.daoImpl.EquipmentDaoImpl;
import com.ex.qi.entity.Equipment;
import com.ex.qi.entity.EquipmentInfo;
import com.ex.qi.entity.InfoList;
import com.google.gson.Gson;

/**
 * Created by sunline on 2016/9/10.
 */
public class TestData {
    public static void main(String[] args) {
        String str  = "{\"mAddr\":\"03\",\"mDataBits\":\"8\",\"mDelay\":\"0\",\"mId\":\"AC201609260220084555978453047033\",\"mName\":\"ac01\",\"mParity\":\"0\",\"mPort\":\"COM3\",\"mRate\":\"9600\",\"mState\":\"1\",\"mStopBits\":\"1\",\"mTimeOut\":\"200\"}";
        Equipment equipment = conver2Equipment(str);
        EquipmentDaoImpl dao = new EquipmentDaoImpl();
        boolean flag = dao.updateEquipment(equipment);
        if (flag) {
            System.out.println("添加设备成功！");
        } else {
            System.out.println("添加设备失败！");
        }
    }
    public static void conver2EquipmentInfo(String str){
        Gson gson = new Gson();
        InfoList obj = gson.fromJson(str, InfoList.class);
        EquipmentInfo[] infos = obj.getInfos();
        for (EquipmentInfo info : infos) {
            System.out.println(info.getId());
        }
    }
    public static Equipment conver2Equipment(String str){
        Gson gson = new Gson();
        Equipment obj = gson.fromJson(str, Equipment.class);
        System.out.println(obj.getId());
        return obj;
    }
}
