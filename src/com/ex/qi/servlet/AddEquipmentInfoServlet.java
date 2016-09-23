package com.ex.qi.servlet;

import com.ex.qi.dao.daoImpl.EquipmentInfoDaoImpl;
import com.ex.qi.entity.EquipmentInfo;
import com.ex.qi.entity.InfoList;
import com.ex.qi.http.HttpUtils;
import com.ex.qi.utils.LogUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by sunline on 2016/8/22.
 */
//@WebServlet(name = "infoServlet", urlPatterns = "/AddEquipmentInfoServlet")
public class AddEquipmentInfoServlet extends HttpServlet {
    Gson gson = new Gson();
    public static final String CRLF = "<br>";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String className = getClass().getName();
        new HttpUtils().handlerAndroidRequest(className, req);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private void handlerAndroidRequest(HttpServletRequest req, HttpServletResponse resp) {
        /**
         * 从客户端读数据
         */
        try {
            InputStream is = req.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            String str = "";
            while (-1 != (len = is.read(buf))) {
                str += new String(buf, 0, len);
            }
            Gson gson = new Gson();
            boolean flag = false;
            if (null != str && !"".equals(str)) {
                System.out.println("str = " + str);
                InfoList infos = gson.fromJson(str, InfoList.class);
                EquipmentInfoDaoImpl dao = new EquipmentInfoDaoImpl();
                List<EquipmentInfo> list = infos.getInfos();
                for (EquipmentInfo info : list) {
                    if (!dao.isExists(info.getfId(),info.getRoute())){
                        flag = dao.addEquipmentInfo(info);
                    }else {
                        printLog(flag,info);
                    }
                }
            } else {
                System.out.println("服务器没有接收到数据！");
            }
            if (flag) {
                System.out.println("添加设备成功！");
            } else {
                System.out.println("添加设备失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        logUtils.writeLog("info.txt", sb);
    }
}
