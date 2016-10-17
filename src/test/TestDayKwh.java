package test;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.dao.daoImpl.PresentKwhDao;
import com.ex.qi.dao.daoImpl.RealKwhDaoImpl;
import com.ex.qi.entity.Equipment;
import com.ex.qi.entity.PresentKwh;
import com.ex.qi.entity.RealKwh;
import com.ex.qi.utils.*;

import java.util.Map;

/**
 * Created by sunline on 2016/9/8.
 */
public class TestDayKwh {
    private static BaseDaoImpl mBaseDao = null;
    private static TableUtils mTableUtils = null;
    public static final int AC_TIMEOUT = 30;
    public static final int DC_TIMEOUT = 1;
    public static void init(){
        mBaseDao = new BaseDaoImpl();
        mTableUtils = new TableUtils();
        if (!mBaseDao.tableIsExists("Equipment")){
            mTableUtils.newEquipmentTable();
        }
        if (!mBaseDao.tableIsExists("DeviceInfo"))
            mTableUtils.newEquipmentInfoTable();
        if (!mBaseDao.tableIsExists("RealKwh")){
            mTableUtils.newRealKwhTable();
        }
        if (!mBaseDao.tableIsExists("DayKwh")){
            mTableUtils.newDayKwhTable();
        }
        if (!mBaseDao.tableIsExists("YearKwh")){
            mTableUtils.newYearKwhTable();
        }
        if (!mBaseDao.tableIsExists("MonthKwh")){
            mTableUtils.newMonthKwhTable();
        }
        if (!mBaseDao.tableIsExists("AccumDayKwh")){
            mTableUtils.newAccumDayKwhTable();
        }
        if (!mBaseDao.tableIsExists("AccumMonthKwh")){
            mTableUtils.newAccumMonthKwhTable();
        }
        if (!mBaseDao.tableIsExists("AccumYearKwh")){
            mTableUtils.newAccumYearKwhTable();
        }
        mBaseDao = null;
        mTableUtils = null;
    }


    private static void updatePresentKwh( String table, String fk, float curDegree, int route) {
        PresentKwhDao dao = new PresentKwhDao();
        int curHour = DateUtils.getHours();
        int curPoint = DateUtils.getDay();
        String id = "";
        if (table.equalsIgnoreCase(PresentKwhDao.KWH_DAY)) {
            id = IDUtils.getId(IDUtils.KWH_DAY);
        } else if (table.equalsIgnoreCase(PresentKwhDao.KWH_MONTH)) {
            id = IDUtils.getId(IDUtils.KWH_MONTH);
        } else if (table.equalsIgnoreCase(PresentKwhDao.KWH_YEAR)) {
            id = IDUtils.getId(IDUtils.KWH_YEAR);
        }
        PresentKwh kwh = dao.findInfoByNum(table, fk, route);
        float latestDegree = kwh.getDegree();
        int num = kwh.getNum();
        int lastPoint = kwh.getPoint();
        int routes = dao.findTotalByRoute(table, fk, route);
        latestDegree = curDegree - latestDegree > 1 ? curDegree : latestDegree - curDegree > 1 ? latestDegree + curDegree : curDegree;
        if (0 != routes) {//判断数据库中有记录
            //System.out.println(" --if->> routes = "+routes);
          /*  System.out.println(" -- if (0 != routes)->> curHour = "+curHour);
            System.out.println(" -- if (0 != routes)->> updatePoint = "+curPoint);
            System.out.println(" -- if (0 != routes)->> lastPoint = "+lastPoint);*/
            System.out.println();
            if (0 == curHour && curPoint > lastPoint) {//时间为0点，天数已经过去一天
                dao.updateDataAtPoint(table, latestDegree, fk, route, lastPoint, curPoint, 0);//更新0点的数据
            }
            dao.updateDataAtPoint(table, latestDegree, fk, route, lastPoint, curPoint, 1);
        } else {//判断数据库中没有记录
            // System.out.println(" --else->> routes = "+routes);
            if (0 == curHour) {
                //System.out.println(" --if->> curHour = "+curHour);
                dao.addDataAtPoint(table, new PresentKwh(id, fk, route, latestDegree, curPoint, 0));
            } else {
                //System.out.println(" --else->> curHour = "+curHour);
                dao.addDataAtPoint(table, new PresentKwh(id, fk, route, 0f, curPoint, 0));
            }
            if (table.equalsIgnoreCase(PresentKwhDao.KWH_DAY)) {
                id = IDUtils.getId(IDUtils.KWH_DAY);
            } else if (table.equalsIgnoreCase(PresentKwhDao.KWH_MONTH)) {
                id = IDUtils.getId(IDUtils.KWH_MONTH);
            } else if (table.equalsIgnoreCase(PresentKwhDao.KWH_YEAR)) {
                id = IDUtils.getId(IDUtils.KWH_YEAR);
            }
            dao.addDataAtPoint(table, new PresentKwh(id, fk, route, latestDegree, curPoint, 1));
        }
    }
    public static void updateDatabase(String fk, float[] degrees){
        float curDegree;
        int route = 0;
        for (int j = 0; j < degrees.length; j++) {
            route = j + 1;
            curDegree = degrees[j];
            /**
             * 更新ReakKwh中的数据
             */
            RealKwhDaoImpl realKwhDao = new RealKwhDaoImpl();//操作表RealKwh
            RealKwh realKwh = new RealKwh(IDUtils.getId(IDUtils.REAL_KWH), fk, route, curDegree);
            realKwhDao.addRealDegree(realKwh);
            /**
             * 更新DayKwh中的数据
             */
            updatePresentKwh(PresentKwhDao.KWH_DAY,fk,curDegree,route);
        }
    }
    public static void handlerDatabase() {
        long start = System.currentTimeMillis();
        /*----------------------------------------------------------------*/
        Map<String, Comparable> params = null;//用来设置设备的通信参数
        SerialPortUtils portUtils = new SerialPortUtils();//操作串口的工具类
        EquipmentUtils equipmentUtils = new EquipmentUtils();//操作设备的工具类
        Equipment[] equipments = equipmentUtils.loadDevice();//加载所有配置好的设备
        int deviceSize = equipments.length;//设备数量
        if (null != equipments && 0 < deviceSize) {//判断是否已经配置设备
            for (int i = 0; i < deviceSize; i++) {//遍历所有设备
                Equipment equipment = equipments[i];//得到设备实体
                String id = equipment.getId();//当前操作的设备ID
                byte[] cmd = equipmentUtils.generateCommandsViaDevice(equipment);
                params = equipmentUtils.parseToParams(equipment);//解析设备的通信参数、
                portUtils.open(params);//依据生成的通信参数打开串口，通信开始
                boolean flag = portUtils.getPortState();
                /**
                 * 1、依据设备ID判断设备类型
                 * 2、接收设备数据
                 */
                if (id.startsWith("AC")) {//判断为多路表
                    portUtils.sendMessage(cmd, AC_TIMEOUT);
                    if (flag) {
                        float[] degrees = portUtils.parseACDegrees();//把数据解析成电度数值
                        updateDatabase(id, degrees);//添加数据到RealKwh
//                        System.out.println("\n-***************************************************-");
                    }
                } else if (id.startsWith("DC")) {//判断为直流电间
                    portUtils.sendMessage(cmd, DC_TIMEOUT);
                    if (flag) {
                        float[] degrees = portUtils.parseDCDegrees();//把数据解析成电度数值
                        updateDatabase(id, degrees);//添加数据到RealKwh
//                        System.out.println("\n-***************************************************-");
                    }
                }
                portUtils.close();//每次采集完毕，关闭串口
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
    }
    public static void main(String[] args) {
//        init();
        for (int i = 0; i < 1000; i++) {
            handlerDatabase();
        }
    }
}
