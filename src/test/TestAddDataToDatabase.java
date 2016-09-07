package test;

import com.ex.qi.dao.daoImpl.AccumKwhDao;
import com.ex.qi.dao.daoImpl.*;
import com.ex.qi.entity.AccumKwh;
import com.ex.qi.entity.Device;
import com.ex.qi.entity.PresentKwh;
import com.ex.qi.entity.RealKwh;
import com.ex.qi.utils.DateUtils;
import com.ex.qi.utils.DeviceUtils;
import com.ex.qi.utils.IDUtils;
import com.ex.qi.utils.SerialPortUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by sunline on 2016/8/31.
 */
@SuppressWarnings("all")
public class TestAddDataToDatabase {
    public static final int AC_TIMEOUT = 30;
    public static final int DC_TIMEOUT = 1;

    public static void main(String[] args) {
        for (int i = 1; i < 5000; i++) {
            System.out.println("采集程序第 【 " + i + " 】次运行");
            addDataToDatabase();
        }
    }

    /**
     * 添加数据到数据库，包括DayKwh,MonthKwh,YearKwh,AccumDayKwh,AccumMonthKwh,AccumYearKwh.
     */
    public static void addDataToDatabase() {
        long start = System.currentTimeMillis();
        /*----------------------------------------------------------------*/
        Map<String, Comparable> params = null;//用来设置设备的通信参数
        SerialPortUtils portUtils = new SerialPortUtils();//操作串口的工具类
        DeviceUtils deviceUtils = new DeviceUtils();//操作设备的工具类
        List<Device> devices = deviceUtils.loadDevice();//加载所有配置好的设备
        int deviceSize = devices.size();//设备数量
        if (null != devices && 0 < deviceSize) {//判断是否已经配置设备
            for (int i = 0; i < deviceSize; i++) {//遍历所有设备
                Device device = devices.get(i);//得到设备实体
                String id = device.getId();//当前操作的设备ID
                byte[] cmd = deviceUtils.generateCommandsViaDevice(device);
                params = deviceUtils.parseToParams(device);//解析设备的通信参数、
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
                        addDataToRealKwh(id, degrees);//添加数据到RealKwh
//                        System.out.println("\n-***************************************************-");
                    }
                } else if (id.startsWith("DC")) {//判断为直流电间
                    portUtils.sendMessage(cmd, DC_TIMEOUT);
                    if (flag) {
                        float[] degrees = portUtils.parseDCDegrees();//把数据解析成电度数值
                        addDataToRealKwh(id, degrees);//添加数据到RealKwh
//                        System.out.println("\n-***************************************************-");
                    }
                }
                portUtils.close();//每次采集完毕，关闭串口
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
    }

    /**
     * 添加数据到RealKwh数据表中
     *
     * @param id
     * @param degrees
     */
    private static void addDataToRealKwh(String id, float[] degrees) {
        float history;
        float current;
        int route = 0;
        for (int j = 0; j < degrees.length; j++) {
            route = j + 1;
            current = degrees[j];
//            System.out.print(degrees[j] + " ");
            /**
             * 更新ReakKwh中的数据
             */
            RealKwhDaoImpl realKwhDao = new RealKwhDaoImpl();//操作表RealKwh
            RealKwh realKwh = new RealKwh(IDUtils.getId(IDUtils.REAL_KWH), id, route, current);
            realKwhDao.addRealDegree(realKwh);
            /**
             * 更新DayKwh中的数据
             */
            DayKwhDaoImpl dayKwhDao = new DayKwhDaoImpl();
            history = dayKwhDao.queryHistoryData(id, route);
            current = current - history > 1 ? current : history - current > 1 ? history + current : current;
            updateDayKwh(dayKwhDao, id, current, route);
            /**
             * 更新MonthKwh中的数据
             */
            MonthKwhDaoImpl monthKwhDao = new MonthKwhDaoImpl();
            history = monthKwhDao.queryHistoryData(id, route);
            current = current - history > 1 ? current : history - current > 1 ? history + current : current;
            updateMonthKwh(monthKwhDao, id, current, route);
            /**
             * 更新MonthKwh中的数据
             */
            YearKwhDaoImpl yearKwhDao = new YearKwhDaoImpl();
            history = yearKwhDao.queryHistoryData(id, route);
            current = current - history > 1 ? current : history - current > 1 ? history + current : current;
            updateYearKwh(yearKwhDao, id, current, route);

            /**----------------------------------------------------------------------*/
            AccumKwhDao accumDao = new AccumKwhDao();
            String table = "";
            /**
             * 更新AccumDayKwh
             */
            table = AccumKwhDao.KWH_ACCUM_DAY;
            history = accumDao.findLatestData(table, id, route);
            current = current - history > 1 ? current : history - current > 1 ? history + current : current;
            int hour = DateUtils.getHours();
            updateAccumKwh(accumDao, table, id, route, current, hour, 25);
            /**
             * 更新AccumDayKwh
             */
            int day = DateUtils.getDay();
            table = AccumKwhDao.KWH_ACCUM_MONTH;
            history = accumDao.findLatestData(table, id, route);
            current = current - history > 1 ? current : history - current > 1 ? history + current : current;
            updateAccumKwh(accumDao, table, id, route, current, day, 31);
            /**
             * 更新AccumDayKwh
             */
            table = AccumKwhDao.KWH_ACCUM_YEAR;
            history = accumDao.findLatestData(table, id, route);
            current = current - history > 1 ? current : history - current > 1 ? history + current : current;
            updateAccumKwh(accumDao, table, id, route, current, day, 366);
        }
    }


    /**
     * 更新数据到DayKwh表中
     *
     * @param dayKwhDao
     * @param id
     * @param degree
     * @param route
     */
    private static void updateDayKwh(DayKwhDaoImpl dayKwhDao, String id, float degree, int route) {
        String table = "DayKwh";
        int hour = DateUtils.getHours();//获得当前的小时数
        /**
         * 1、小时数是否为0，如果为零，查找到为0的记录，并用新数据去替换。
         * 2、不为0，查找是否有其他小时的数据存在，如果存在，替换掉数据，如果不存在，增加当前小时数的数据。
         */
        if (0 == hour) {
            // System.out.println("当前为0点整");
            boolean hasStart = dayKwhDao.routeHasStart(id, route);
            if (!hasStart) {
                // System.out.println("当前为0点整，没有开始记录");
                dayKwhDao.addDataAtPoint(new PresentKwh(IDUtils.getId(IDUtils.DAY_KWH), id, route, degree, hour));
            } else {
                // System.out.println("当前为0点整，已经有了记录");
                dayKwhDao.updateDataAtpoint(degree, id, route, hour);
            }
        } else {
            //System.out.println("当前时刻不为0点");
            boolean hasEnd = dayKwhDao.routeHasEnd(id, route);
            if (!hasEnd) {
                //  System.out.println("当前时刻不为0点，没有");
                dayKwhDao.addDataAtPoint(new PresentKwh(IDUtils.getId(IDUtils.DAY_KWH), id, route, degree, 1));
            } else {
                dayKwhDao.updateDataAtpoint(degree, id, route, 1);
            }
        }
    }

    /**
     * 更新数据到MonthKwh表中
     *
     * @param monthKwhDao
     * @param id
     * @param degree
     * @param route
     */
    private static void updateMonthKwh(MonthKwhDaoImpl monthKwhDao, String id, float degree, int route) {
        int day = DateUtils.getDay();
        int hour = DateUtils.getHours();
        if (0 == hour && 1 == day) {
            boolean hasStart = monthKwhDao.routeHasStart(id, route);
            if (!hasStart) {
                monthKwhDao.addDataAtPoint(new PresentKwh(IDUtils.getId(IDUtils.DAY_KWH), id, route, degree, hour));
            } else {
                monthKwhDao.updateDataAtpoint(degree, id, route, hour);
            }
        } else {
            boolean hasEnd = monthKwhDao.routeHasEnd(id, route);
            if (!hasEnd) {
                monthKwhDao.addDataAtPoint(new PresentKwh(IDUtils.getId(IDUtils.DAY_KWH), id, route, degree, 1));
            } else {
                monthKwhDao.updateDataAtpoint(degree, id, route, 1);
            }
        }
    }

    /**
     * 更新数据到YearKwh表中
     *
     * @param yearKwhDao
     * @param id
     * @param degree
     * @param route
     */
    private static void updateYearKwh(YearKwhDaoImpl yearKwhDao, String id, float degree, int route) {
        int month = DateUtils.getMonth();
        int day = DateUtils.getDay();
        int hour = DateUtils.getHours();
        if (0 == hour && 1 == day && 1 == month) {
            boolean hasStart = yearKwhDao.routeHasStart(id, route);
            if (!hasStart) {
                yearKwhDao.addDataAtPoint(new PresentKwh(IDUtils.getId(IDUtils.DAY_KWH), id, route, degree, hour));
            } else {
                yearKwhDao.updateDataAtpoint(degree, id, route, hour);
            }
        } else {
            boolean hasEnd = yearKwhDao.routeHasEnd(id, route);
            if (!hasEnd) {
                yearKwhDao.addDataAtPoint(new PresentKwh(IDUtils.getId(IDUtils.DAY_KWH), id, route, degree, 1));
            } else {
                yearKwhDao.updateDataAtpoint(degree, id, route, 1);
            }
        }
    }


    private static void updateAccumKwh(AccumKwhDao dao, String table, String id, int route, float degree, int point, int count) {

        int num = dao.findsLatestNum(table, id, route);
        int total = dao.findTotalByRoute(table, id, route);
        if (-1 != num) {
            int lastPoint = dao.findLatestPoint(table, id, route, num);
            if (point != lastPoint) {
                if (count > total) {
                    num += 1;
                    dao.addDataAtNum(table, new AccumKwh(IDUtils.getId(IDUtils.PASTDAY_KWH), id, route, degree, num, point));
                } else {
                    dao.deleteEarliestDataByInfo(table, id, route);
                }
            } else {
                dao.updateDataAtNum(table, degree, id, route, num, point);
            }
        } else {
            num += 1;
            dao.addDataAtNum(table, new AccumKwh(IDUtils.getId(IDUtils.PASTDAY_KWH), id, route, degree, num, point));
        }
    }


    private static void updatePresentKwh(PresentKwhDao dao, String table, int currentPoint, String id, float degree, int route) {
        int routes = dao.findTotalByRoute(table, id, route);
        if (table.startsWith("Day")) {
            if (0 != currentPoint) {
                dao.updateDataAtPoint(table, degree, id, route, 1);
            }
        }
    }
}
