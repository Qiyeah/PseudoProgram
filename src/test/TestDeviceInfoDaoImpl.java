package test;

import com.ex.qi.dao.daoImpl.DeviceInfoDaoImpl;

/**
 * Created by sunline on 2016/8/31.
 */
public class TestDeviceInfoDaoImpl {
    public static void main(String[] args) {

    }

    public static int findRoutes(String id){
        DeviceInfoDaoImpl dao = new DeviceInfoDaoImpl();
        int routes = dao.getRoutes(id);
        return routes;
    }
}
