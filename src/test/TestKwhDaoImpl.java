package test;

import com.ex.qi.dao.daoImpl.RealKwhDaoImpl;
import com.ex.qi.entity.Device;
import com.ex.qi.utils.DeviceUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by sunline on 2016/8/28.
 */
public class TestKwhDaoImpl {
    public static void main(String[] args) {
        DeviceUtils utils = new DeviceUtils();
        List<Device> list = utils.loadDevice();
        for (Device device : list) {
            testFindRecentLogs(device.getId());
        }

    }
    public static void testFindRecentLogs(String id){
        RealKwhDaoImpl dao = new RealKwhDaoImpl();
        System.out.println(id);

    }
    public void findLastLogs(String id){
        if (null != id){
            if (id.startsWith("AC")){
                
            }else if (id.startsWith("DC")){

            }
        }
    }
}
