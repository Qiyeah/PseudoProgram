package com.ex.qi.servlet;

import com.ex.qi.dao.daoImpl.DeviceInfoDaoImpl;
import com.ex.qi.entity.DeviceInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by sunline on 2016/8/22.
 */
@WebServlet(name = "configServlet", urlPatterns = "/AddConfigServlet")
public class AddConfigServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration<String> paramNames = req.getParameterNames();
        PrintWriter out = resp.getWriter();
        DeviceInfoDaoImpl dao = new DeviceInfoDaoImpl();
        DeviceInfo deviceInfo = new DeviceInfo();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement().trim();
            String value = req.getParameter(name).trim();
            if (name.equals("id")) {
                deviceInfo.setId(value);
                out.append(name + " = " + value);
                out.append("<br>");
            }else if (name.equals("route")) {
                deviceInfo.setPath(Integer.parseInt(value));
                out.append(name + " = " + value);
                out.append("<br>");
            }else if (name.equals("pathname")) {
                deviceInfo.setPathName(value);
                out.append(name + " = " + value);
                out.append("<br>");
            }else if (name.equals("pathattr")) {
                deviceInfo.setPathAttr(Integer.parseInt(value));
                out.append(name + " = " + value);
                out.append("<br>");
            }else if (name.equals("fid")) {
                deviceInfo.setfId(value);
                out.append(name + " = " + value);
                out.append("<br>");
            }else if (name.equals("per")) {
                try {
                    deviceInfo.setPer(Integer.parseInt(value));
                    out.append(name + " = " + value);
                    out.append("<br>");
                } catch (NumberFormatException e) {
                    deviceInfo.setPer(100);
                    out.append(name + " = " + deviceInfo.getPer());
                    out.append("<br>");
                }
            }else if (name.equals("symbol")){
                try {
                    deviceInfo.setSymbol(Integer.valueOf(value));
                    out.append(name + " = " + value);
                    out.append("<br>");
                } catch (NumberFormatException e) {
                    deviceInfo.setSymbol(1);
                    out.append(name + " = " + deviceInfo.getSymbol());
                    out.append("<br>");
                }
            }
        }
        out.append("<hr>");
        System.out.println(deviceInfo.getPath());
        boolean flag = dao.addConfig(deviceInfo);
        if (flag){
            out.append("设备配置添加成功");
        }else {
            out.append("设备配置添加失败");
        }
        out.flush();
        out.close();
    }
}
