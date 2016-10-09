package com.ex.qi.http;

import com.ex.qi.dao.daoImpl.EquipmentDaoImpl;
import com.ex.qi.dao.daoImpl.EquipmentInfoDaoImpl;
import com.ex.qi.entity.Equipment;
import com.ex.qi.entity.EquipmentInfo;
import com.ex.qi.entity.InfoList;
import com.ex.qi.utils.LogUtils;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.List;

/**
 * Created by sunlines on 2016/9/23.
 */
public class HttpUtils {
    public HttpUtils() {

    }

    public void handlerAndroidRequest(String className, HttpServletRequest req) {

        /**
         * 从客户端读数据
         */
        InputStream is = null;
        try {
            is = req.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            String str = "";
            while (-1 != (len = is.read(buf))) {
                str += new String(buf, 0, len,"utf-8");
            }
            if (null != str && !"".equals(str)) {
                System.out.println("从Android客户端接收到的数据：\n" + str+"\n");
                operation2Database(className, str);
            } else {
                System.out.println("服务器没有接收到数据！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean operation2Database(String className, String str) {
        boolean flag = false;
        Gson gson = new Gson();
        if (className.equals("com.ex.qi.servlet.AddEquipmentServlet")) {

            Equipment equipment = gson.fromJson(str, Equipment.class);
            String equipmentId = equipment.getId();
            equipment.setDate(new Date(System.currentTimeMillis()));
            EquipmentDaoImpl dao = new EquipmentDaoImpl();
            if (!dao.isExsits(equipmentId)) {
                flag = dao.addEquipment(equipment);
            } else {
                flag = dao.updateEquipment(equipment);
            }
            printLog(flag, equipment);
        } else if (className.equals("com.ex.qi.servlet.AddEquipmentInfoServlet")) {
            InfoList infos = gson.fromJson(str, InfoList.class);
            EquipmentInfoDaoImpl dao = new EquipmentInfoDaoImpl();
            EquipmentInfo[] list = infos.getInfos();
            for (EquipmentInfo info : list) {
                if (!dao.isExists(info.getfId(), info.getRoute())) {
                    flag = dao.addEquipmentInfo(info);
                } else {
                    flag = dao.updateEquipmentInfo(info);
                }
                printLog(flag, info);
            }
        } else if (className.equals("com.ex.qi.servlet.DeleteEquipmentServlet")) {
            EquipmentDaoImpl dao = new EquipmentDaoImpl();
            System.out.println("删除设备信息");
            flag = dao.deleteEquipment(str);
            printLog(flag, str);
        } else if (className.equals("com.ex.qi.servlet.DeleteEquipmentInfoServlet")) {
            System.out.println("删除通道信息");
            EquipmentInfoDaoImpl dao = new EquipmentInfoDaoImpl();
            flag = dao.deleteEquipmentInfos(str);
            printLog(flag, str);
        }
        return flag;
    }

    private void printLog(boolean flag, String str) {
        StringBuffer sb = new StringBuffer();
        LogUtils logUtils = new LogUtils();
        sb.append(str);
        sb.append(LogUtils.CRLF);
        try {
            if (flag) {
                sb.append(new String("删除设备成功".getBytes(), "gbk"));
            } else {
                sb.append(new String("删除设备失败".getBytes(), "gbk"));
            }
            sb.append(LogUtils.CRLF);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logUtils.writeLog("delete_equipment_log.txt", sb);
    }

    private void printLog(boolean flag, EquipmentInfo info) {
        StringBuffer sb = new StringBuffer();
        LogUtils logUtils = new LogUtils();
        sb.append("id = " + info.getId());
        sb.append(LogUtils.CRLF);
        sb.append("fk = " + info.getfId());
        sb.append(LogUtils.CRLF);
        sb.append("route = " + info.getRoute());
        sb.append(LogUtils.CRLF);
        sb.append("name = " + info.getRouteName());
        sb.append(LogUtils.CRLF);
        sb.append("total_symbol = " + info.getTotalSymbol());
        sb.append(LogUtils.CRLF);
        sb.append("total_per = " + info.getTotalPer());
        sb.append(LogUtils.CRLF);
        sb.append("it_symbol = " + info.getITSymbol());
        sb.append(LogUtils.CRLF);
        sb.append("it_per = " + info.getITPer());
        sb.append(LogUtils.CRLF);
        sb.append(LogUtils.SPLIT2);
        sb.append(LogUtils.CRLF);
        try {
            if (flag) {
                sb.append(new String("设备配置添加成功".getBytes(), "gbk"));
            } else {
                sb.append(new String("设备配置添加失败".getBytes(), "gbk"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append(LogUtils.CRLF);
        logUtils.writeLog("add_info_log.txt", sb);
    }

    private void printLog(boolean flag, Equipment equipment) {
        StringBuffer sb = new StringBuffer();
        sb.append("ID = " + equipment.getId());
        sb.append(LogUtils.CRLF);
        sb.append("NAME = " + equipment.getName());
        sb.append(LogUtils.CRLF);
        sb.append("PORT = " + equipment.getPort());
        sb.append(LogUtils.CRLF);
        sb.append("RATE = " + equipment.getRate());
        sb.append(LogUtils.CRLF);
        sb.append("ADDR = " + equipment.getAddr());
        sb.append(LogUtils.CRLF);
        sb.append("TIMEOUT = " + equipment.getTimeOut());
        sb.append(LogUtils.CRLF);
        sb.append("DATA = " + equipment.getDataBits());
        sb.append(LogUtils.CRLF);
        sb.append("STOP = " + equipment.getStopBits());
        sb.append(LogUtils.CRLF);
        sb.append("PARITY = " + equipment.getParity());
        sb.append(LogUtils.CRLF);
        sb.append("SWITCH = " + equipment.getState());
        sb.append(LogUtils.CRLF);
        sb.append("DELAYED = " + equipment.getDelay());
        sb.append(LogUtils.CRLF);
        sb.append("DT = " + equipment.getDate());
        sb.append(LogUtils.CRLF);
        try {
            if (flag) {
                sb.append(new String("添加设备成功！".getBytes(), "gbk"));
            } else {
                sb.append(new String("添加设备失败！".getBytes(), "gbk"));
            }
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        }
        sb.append(LogUtils.CRLF);
        LogUtils utils = new LogUtils();
        utils.writeLog("add_equipment_log.log", sb);
    }
}
