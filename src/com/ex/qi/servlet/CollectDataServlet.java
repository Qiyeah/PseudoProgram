package com.ex.qi.servlet;

import com.ex.qi.dao.daoImpl.RealKwhDaoImpl;
import com.ex.qi.entity.RealKwh;
import com.ex.qi.entity.Device;
import com.ex.qi.entity.AC;
import com.ex.qi.entity.DC;
import com.ex.qi.utils.DeviceUtils;
import com.ex.qi.utils.IDUtils;
import com.ex.qi.utils.SerialPortUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;

/**
 * Created by sunline on 2016/8/24.
 */
@WebServlet(name = "collect", urlPatterns = "/CollectDataServlet")
public class CollectDataServlet extends HttpServlet {
    public static final int AC_TIMEOUT = 30;
    public static final int DC_TIMEOUT = 1;
    private int delayRead = 1;
    boolean isOpen = false;
    private static String COLLECT_DATA = "1";
    private static String UNCOLLECT_DATA = "2";

    @Override
    public void init() throws ServletException {
        super.init();
        delayRead = 1;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cmd = req.getParameter("key").trim();
        PrintWriter out = resp.getWriter();
        if (COLLECT_DATA.equals(cmd)) {
            /*执行定时任务，第二个参数是首次执行任务的延时，第三个参数是执行定时任务的
            * 间隔时间，第四个参数是时间的类型
            * */
            collectDataByConfig(out);

        }
        if (UNCOLLECT_DATA.equals(cmd)) {

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private byte[] generateIECMessage(Device device) {
        AC iec = new AC();
        int addr = Integer.parseInt(device.getAddr().trim());
        if (!(addr > 0xf)) {
            iec.setADRHi((byte) 0x30);
            iec.setADRLo(Integer.toHexString(addr).getBytes()[0]);
        } else {
            iec.setADRHi(Integer.toHexString(addr / 16).getBytes()[0]);
            iec.setADRLo(Integer.toHexString(addr % 16).getBytes()[0]);
        }
        iec.parseCheksum();
        return iec.produceCmdBytes();
    }

    private byte[] generateMODMessage(Device device) {
        DC mod = new DC();
        int addr = Integer.parseInt(device.getAddr().trim());
        mod.setAddr((byte) addr);
        mod.setFunction((byte) 0x03);
        mod.setMemeryHi((byte) 0x02);
        mod.setMemeryLo((byte) 0x0E);
        mod.setCountHi((byte) 0x00);
        mod.setCountLo((byte) 0x20);
        mod.parseCRC();
        return mod.produceCmdBytes();
    }

    private List<RealKwh> parse2List(float[] degrees, String fId) {
        List<RealKwh> mKwhs = new ArrayList<>();
        for (int j = 0; j < degrees.length; j++) {
            mKwhs.add(new RealKwh(IDUtils.getId(IDUtils.REAL_KWH), fId, j + 1, degrees[j], new Date(System.currentTimeMillis())));
        }
        return mKwhs;
    }

    private void collectDataByConfig(PrintWriter out) {
        /**
         * 创建加载配置文件的工具,并加载配置表
         */
        long start = System.currentTimeMillis();
        DeviceUtils deviceUtils = new DeviceUtils();
        List<Device> devices = deviceUtils.loadDevice();
        /**
         * 遍历配置表
         */
        System.out.println(devices.size());
        for (int i = 0; i < devices.size(); i++) {

            float[] degrees = new float[0];//临时存储计算好的电度数据
            SerialPortUtils serialPortUtils = new SerialPortUtils();
            Device device = devices.get(i);
            /**
             * 通过配置工具解析设备通讯参数
             */
            Map<String, Comparable> params = deviceUtils.parseToParams(device);



            /**
             * 串口辅助工具,打开串口、发送命令、接收设备数据并解析、关闭串口
             */
            serialPortUtils.open(params);//打开串口
            RealKwhDaoImpl dao = new RealKwhDaoImpl();
            if (device.getId().startsWith("AC")) {
                AC iec = new AC();
                int addr = Integer.parseInt(device.getAddr().trim());
                if (!(addr > 0xf)) {
                    iec.setADRHi((byte) 0x30);
                    iec.setADRLo(Integer.toHexString(addr).getBytes()[0]);
                } else {
                    iec.setADRHi(Integer.toHexString(addr / 16).getBytes()[0]);
                    iec.setADRLo(Integer.toHexString(addr % 16).getBytes()[0]);
                }
                iec.parseCheksum();
                byte[] msg = iec.produceCmdBytes();
                serialPortUtils.sendMessage(msg, AC_TIMEOUT);
                if (serialPortUtils.getPortState()) {
                    degrees = serialPortUtils.parseACDegrees();
                    out.println("这里是正常使用的多路表的电度数据：");
                    out.print("<br>");
                    out.print("<hr>");
                    out.print("<br>");
                    for (int j = 0; j < degrees.length; j++) {
                        System.out.print(degrees[j] + " ");
                        out.print(degrees[j] + " ");
                        RealKwh d = new RealKwh(IDUtils.getId(IDUtils.REAL_KWH), device.getId(), j + 1, degrees[j]);
                        dao.addRealDegree(d);
                    }
                    out.print("<br>");
                    System.out.println();
                }
            } else if (device.getId().startsWith("DC")) {
                DC mod = new DC();
                int addr = Integer.parseInt(device.getAddr().trim());
                mod.setAddr((byte) addr);
                mod.setFunction((byte) 0x03);
                mod.setMemeryHi((byte) 0x02);
                mod.setMemeryLo((byte) 0x0E);
                mod.setCountHi((byte) 0x00);
                mod.setCountLo((byte) 0x20);
                mod.parseCRC();
                byte[] msg = mod.produceCmdBytes();
                serialPortUtils.sendMessage(msg, DC_TIMEOUT);
                if (serialPortUtils.getPortState()) {
                    degrees = serialPortUtils.parseDCDegrees();
                    out.println("这里是正常使用的直流电间的电度数据：");
                    out.print("<br>");
                    out.print("<hr>");
                    out.print("<br>");
                    for (int j = 0; j < degrees.length; j++) {
                        System.out.print(degrees[j] + " ");
                        out.print(degrees[j] + " ");
                        RealKwh d = new RealKwh(IDUtils.getId(IDUtils.REAL_KWH), device.getId(), j + 1, degrees[j]);
                        dao.addRealDegree(d);
                    }
                    out.print("<br>");
                }
            }


            serialPortUtils.close();//关闭串口
        }
        long stop = System.currentTimeMillis();
        out.append("耗时：" + (stop - start));
    }
}
