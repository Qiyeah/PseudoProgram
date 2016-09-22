package com.ex.qi.servlet;

import com.ex.qi.dao.daoImpl.EquipmentInfoDaoImpl;
import com.ex.qi.entity.EquipmentInfo;
import com.ex.qi.entity.InfoJson;
import com.ex.qi.utils.LogUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
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
        PrintWriter out = resp.getWriter();
        EquipmentInfoDaoImpl dao = new EquipmentInfoDaoImpl();
        boolean flag = false;
        System.out.println("doPost is run");
        /**
         * 从Android客户端读数据
         */
        InputStream is = req.getInputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        String str = "";
        while (-1 != (len = is.read(buf))) {
            str += new String(buf, 0, len);
        }
        System.out.println("str = " + str);
        Gson gson = new Gson();

        InfoJson infoJson = gson.fromJson(str, InfoJson.class);

        List<EquipmentInfo> list = infoJson.getList();
        for (int i = 0; i < list.size(); i++) {
            EquipmentInfo info = list.get(i);
            String json = gson.toJson(info,EquipmentInfo.class);
            /**
             * 更新到数据库
             */
            flag = dao.addConfig(info);
            /**
             * 输出到打印日志
             */
            printLog(flag, info);
            /**
             * 输出到客户端
             */
            if (flag){
                out.write("success!");
            }else
                out.write("failed!");
            out.flush();
            out.close();
        }

        StringBuffer sb = new StringBuffer();
        /**
         * 从网页客户端读数据
         */
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement().trim();
            String json = req.getParameter(name);
            System.out.println(json);
            List<EquipmentInfo> infos = gson.fromJson(json, InfoJson.class).getList();
            for (EquipmentInfo info : infos) {
                out.append("id = " + info.getId());
                out.append(CRLF);
                out.append("fk = " + info.getfId());
                out.append(CRLF);
                out.append("route = " + info.getRoute());
                out.append(CRLF);
                out.append("name = " + info.getPathName());
                out.append(CRLF);
                out.append("attr = " + info.getPathAttr());
                out.append(CRLF);
                out.append("per = " + info.getPer());
                out.append(CRLF);
                out.append("symbol = " + info.getSymbol());
                out.append(CRLF);
                out.append("********************************************************************");
                out.append(CRLF);
                /**
                 * 更新到数据库
                 */
                flag = dao.addConfig(info);
                if (flag) {
                    out.append("设备配置添加成功");
                } else {
                    out.append("设备配置添加失败");
                }
                out.append(CRLF);
                out.append(CRLF);
                printLog(flag, info);
            }
        }
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
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
        sb.append("name = " + info.getPathName());
        sb.append(LogUtils.CRLF);
        sb.append("attr = " + info.getPathAttr());
        sb.append(LogUtils.CRLF);
        sb.append("per = " + info.getPer());
        sb.append(LogUtils.CRLF);
        sb.append("symbol = " + info.getSymbol());
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
