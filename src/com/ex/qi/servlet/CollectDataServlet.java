package com.ex.qi.servlet;

import com.ex.qi.dao.daoImpl.AccumKwhDao;
import com.ex.qi.dao.daoImpl.PresentKwhDao;
import com.ex.qi.dao.daoImpl.RealKwhDaoImpl;
import com.ex.qi.entity.*;
import com.ex.qi.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by sunline on 2016/8/24.
 */
@WebServlet(name = "collect", urlPatterns = "/CollectDataServlet")
public class CollectDataServlet extends HttpServlet implements Runnable {
    public static final int AC_TIMEOUT = 30;
    public static final int DC_TIMEOUT = 1;
    ScheduledExecutorService service = null;
    @Override
    public void init() throws ServletException {
        super.init();
        service = Executors
                .newSingleThreadScheduledExecutor();
                /*执行定时任务，第二个参数是首次执行任务的延时，第三个参数是执行定时任务的
                 间隔时间，第四个参数是时间的类型
                 */
        service.scheduleAtFixedRate(this, 1, 10, TimeUnit.SECONDS);
    }

    PrintWriter out = null;
    int count = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("key").trim();
        long start = System.currentTimeMillis();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        out = resp.getWriter();
        out.append("<center>");
        out.append("<table width=\"30%\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\" bgcolor=\"#B5F900\">");
        out.append("<tr><td align=\"right\">Key</td>" +
                "<td>" + cmd + "</td></tr>");
        out.append("<tr><td align=\"right\">请求次数统计</td>" +
                "<td>" + (count++) + "</td></tr>");
        printPue(out);
        long end = System.currentTimeMillis();
        out.append("<tr><td align=\"right\">耗时</td>" +
                "<td>" + (end - start) + "</td></tr>");
        out.append("</table></center>");
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (!service.isShutdown()){
            service.shutdown();
        }
        service = null;
        out = null;
    }

    /**
     * 添加数据到数据库，包括DayKwh,MonthKwh,YearKwh,AccumDayKwh,AccumMonthKwh,AccumYearKwh.
     */
    public void collectDataFromDevice(PrintWriter out) {
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
                        addData2Database(id, degrees);//添加数据到RealKwh
//                        System.out.println("-***************************************************-");
                    }
                } else if (id.startsWith("DC")) {//判断为直流电间
                    portUtils.sendMessage(cmd, DC_TIMEOUT);
                    /*if (flag) {
                        float[] degrees = portUtils.parseDCDegrees();//把数据解析成电度数值
                       if (null != degrees && 0 != degrees.length){
                           addData2Database(id, degrees);
                       }
//                        System.out.println("-***************************************************-");
                    }*/
                }
                portUtils.close();//每次采集完毕，关闭串口
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
    }

    /**
     * 添加数据到数据库中的六张表
     *
     * @param id
     * @param degrees
     */
    private void addData2Database(String id, float[] degrees) {
        float curDegree;
        int route = 0;
        for (int j = 0; j < degrees.length; j++) {
            route = j + 1;
            curDegree = degrees[j];

            /**
             * 更新ReakKwh中的数据
             */
            RealKwhDaoImpl realKwhDao = new RealKwhDaoImpl();//操作表RealKwh
            RealKwh realKwh = new RealKwh(IDUtils.getId(IDUtils.REAL_KWH), id, route, curDegree);
            realKwhDao.addRealDegree(realKwh);
            /**
             * 更新DayKwh中的数据
             */
            updatePresentKwh(PresentKwhDao.KWH_DAY, id, curDegree, route);
            /**
             * 更新MonthKwh中的数据
             */
            updatePresentKwh(PresentKwhDao.KWH_MONTH, id, curDegree, route);
             /**
             * 更新MonthKwh中的数据
             */
            updatePresentKwh(PresentKwhDao.KWH_YEAR, id, curDegree, route);
            /**
             * ----------------------------------------------------------------------
             * 更新AccumDayKwh
             */
            updateAccumKwh(AccumKwhDao.KWH_ACCUM_DAY, id, route, curDegree, 25);
            /**
             * 更新AccumDayKwh
             */
            updateAccumKwh(AccumKwhDao.KWH_ACCUM_MONTH, id, route, curDegree, 31);
            /**
             * 更新AccumDayKwh
             */
            updateAccumKwh(AccumKwhDao.KWH_ACCUM_YEAR, id, route, curDegree, 366);
        }
    }

    /**
     * 更新累计电度值，包括AccumDayKwh,AccumMonthKwh,AccumYearKwh
     *
     * @param table     更新数据库中的表名称
     * @param fk        当前通道对应的设备ID
     * @param route     当前通道号
     * @param curDegree 当前通道采集到的电度值
     * @param count     累计数据需要在数据库中保存的记录数量
     */
    private void updateAccumKwh(String table, String fk, int route, float curDegree, int count) {
        int curPoint = 0;
        String id = "";
        /**
         * 以下操作，计算出数据在数据库中保存的时间刻度,及产生
         * 如：累计24小时，计算的结果为当前的小时数
         *      累计30天，计算的结果为当前天在一年中的第几天，然后模以31
         *      累计365天，计算的结果为当前天在一年中的第几天
         */
        if (table.equalsIgnoreCase(AccumKwhDao.KWH_ACCUM_DAY)) {
            id = IDUtils.getId(IDUtils.KWH_ACCUM_DAY);
            curPoint = DateUtils.getHours();
        } else if (table.equalsIgnoreCase(AccumKwhDao.KWH_ACCUM_MONTH)) {
            id = IDUtils.getId(IDUtils.KWH_ACCUM_MONTH);
            curPoint = DateUtils.getDay() % 31;
        } else if (table.equalsIgnoreCase(AccumKwhDao.KWH_ACCUM_YEAR)) {
            id = IDUtils.getId(IDUtils.KWH_YEAR);
            curPoint = DateUtils.getDay();
        }
        //System.out.println("id = "+id +" curPoint = "+curPoint);
        AccumKwhDao dao = new AccumKwhDao();
        /**
         * 查找到对应数据表中的最近一条记录
         */
        AccumKwh kwh = dao.findInfoByNum(table, fk, route);
        float latestData = kwh.getDegree();
        int lastPoint = kwh.getPoint();
        latestData = curDegree - latestData > 1 ? curDegree : latestData - curDegree > 1 ? latestData + curDegree : curDegree;
        int num = kwh.getNum();
        /**
         * 查找到对应数据表中的记录总数
         */
        int total = dao.findTotalByRoute(table, fk, route);
        if (0 != total) {//总数不等于0，表示数据表中已经有了记录
            //System.out.println(" --if->> total = "+total);
            if (curPoint != lastPoint) {//当前时刻数不等于最近一条时刻
                //System.out.println(" --if->> curPoint = "+curPoint+" lastPoint"+lastPoint);
                if (count > total) {//数据库中的记录总数没有超过定义好的记录总数
                    // System.out.println(" --if->> count = "+count+" total"+total);
                    num += 1;
                    dao.addDataAtNum(table, new AccumKwh(id, fk, route, latestData, num, curPoint));
                } else {//数据库中的记录总数超过定义好的记录总数
                    //System.out.println("--else->> count = "+count+" total"+total);
                    dao.deleteEarliestDataByInfo(table, fk, route);
                }
            } else {
                //System.out.println(" --else->> curPoint = "+curPoint+" lastPoint"+lastPoint);
                dao.updateDataAtNum(table, latestData, fk, route, num, curPoint);
            }
        } else {//总数等于0，表示数据表中没有记录
            //System.out.println(" --else->> total = "+total);
            num += 1;
            dao.addDataAtNum(table, new AccumKwh(id, fk, route, latestData, num, curPoint));
        }
    }

    /**
     * 更新当前电度值，包括DayKwh,MonthKwh,YearKwh.
     *
     * @param table     更新数据库中的表名称
     * @param fk        当前通道对应的设备ID
     * @param curDegree 当前通道采集到的电度值
     * @param route     当前通道号
     */
    private void updatePresentKwh(String table, String fk, float curDegree, int route) {
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

    private void printPue(PrintWriter out) {
        PUEUtils utils = new PUEUtils();
        float pue = utils.calculatePue(PUEUtils.DAY_PUE);
        //System.out.println("   当日PUE:" + pue);
        out.print("<tr>" +
                "<td align=\"right\">当日</td>" +
                "<td>" + pue + "</td>" +
                "</tr>");
        pue = utils.calculatePue(PUEUtils.MONTH_PUE);
        out.append("<tr>" +
                "<td align=\"right\">当月</td>" +
                "<td>" + pue + "</td>" +
                "</tr>");
        pue = utils.calculatePue(PUEUtils.YEAR_PUE);
        out.append("<tr>" +
                "<td align=\"right\">当年</td>" +
                "<td>" + pue + "</td>" +
                "</tr>");
        pue = utils.calculatePue(PUEUtils.ACCUMDAY_PUE);
        out.append("<tr>" +
                "<td align=\"right\">24小时累计</td>" +
                "<td>" + pue + "</td>" +
                "</tr>");
        pue = utils.calculatePue(PUEUtils.ACCUMMONTH_PUE);
        out.append("<tr>" +
                "<td align=\"right\">30天累计PUE</td>" +
                "<td>" + pue + "</td>" +
                "</tr>");
        pue = utils.calculatePue(PUEUtils.ACCUMYEAR_PUE);
        out.append("<tr>" +
                "<td align=\"right\">365天累计PUE</td>" +
                "<td>" + pue + "</td>" +
                "</tr>");
    }

    @Override
    public void run() {
        collectDataFromDevice(out);
    }
}
