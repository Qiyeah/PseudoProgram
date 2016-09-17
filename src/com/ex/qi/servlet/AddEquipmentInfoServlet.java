package com.ex.qi.servlet;

import com.ex.qi.dao.daoImpl.EquipmentInfoDaoImpl;
import com.ex.qi.entity.EquipmentInfo;
import com.ex.qi.entity.InfoJson;
import com.ex.qi.utils.LogUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by sunline on 2016/8/22.
 */
@WebServlet(name = "infoServlet", urlPatterns = "/AddEquipmentInfoServlet")
public class AddEquipmentInfoServlet extends HttpServlet {
    Gson gson = new Gson();
    public static final String CRLF = "<br>";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost is run");
        LogUtils logUtils = new LogUtils();
        StringBuffer sb = new StringBuffer();
        Enumeration<String> paramNames = req.getParameterNames();
        PrintWriter out = resp.getWriter();
        EquipmentInfoDaoImpl dao = new EquipmentInfoDaoImpl();
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
                boolean flag = dao.addConfig(info);
                sb.append(LogUtils.SPLIT2);
                sb.append(LogUtils.CRLF);
                if (flag) {
                    out.append("设备配置添加成功");
                    sb.append(new String("设备配置添加成功".getBytes(),"gbk"));
                } else {
                    out.append("设备配置添加失败");
                    sb.append(new String("设备配置添加失败".getBytes(),"gbk"));
                }
                out.append(CRLF);
                out.append(CRLF);
                sb.append(LogUtils.CRLF);

            }
        }
        out.flush();
        out.close();

        logUtils.writeLog("info.txt",sb);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
