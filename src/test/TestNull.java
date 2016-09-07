package test;

import com.ex.qi.dao.daoImpl.DayKwhDaoImpl;
import com.ex.qi.dao.daoImpl.DeviceInfoDaoImpl;

/**
 * Created by sunline on 2016/8/26.
 */
public class TestNull {
    public static void main(String[] args) {
        //System.out.println(Integer.parseInt(""));
        DayKwhDaoImpl dayKwhDao = new DayKwhDaoImpl();
        DeviceInfoDaoImpl infoDao = new DeviceInfoDaoImpl();
        calculatePue(dayKwhDao,infoDao);
    }
    public static float calculatePue(Object obj,DeviceInfoDaoImpl infoDao){

        if (obj instanceof DayKwhDaoImpl){
            System.out.println(true);
        }else
            System.out.println(false);
        return 0f;
    }
}
