package com.ex.qi.servlet;

import com.ex.qi.dao.daoImpl.DeviceDaoImpl;
import com.ex.qi.entity.Device;
import com.ex.qi.utils.IDUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sunline on 2016/8/22.
 */
@WebServlet(name = "add",urlPatterns = "/AddDeviceServlet")
public class AddDeviceServlet extends HttpServlet {
    public int KEY_IEC = 0x01;
    public int KEY_MOD = 0x02;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int key = Integer.parseInt(req.getParameter("key"));
        String name = req.getParameter("name");
        String id = "";
        if (KEY_IEC == key){
            id = IDUtils.getId(IDUtils.AC);
        }else if (KEY_MOD == key){
            id = IDUtils.getId(IDUtils.DC);
        }
        String port = req.getParameter("port");
        String rate = req.getParameter("rate");
        String addr = req.getParameter("addr");
        String timeout = req.getParameter("timeout");
        String databits = req.getParameter("databits");
        String stopbits = req.getParameter("stopbits");
        String parity = req.getParameter("parity");
        String state = req.getParameter("state");
        String delay = req.getParameter("delay");
        DeviceDaoImpl dao = new DeviceDaoImpl();
        boolean flag = false;
        //TODO 添加设置了timeout databits stopbits parity state delay
        if (timeout.equals("")||databits.equals("")||stopbits.equals("")||parity.equals("")||state.equals("")||delay.equals("")){
            flag = dao.addDevice(new Device(id, name, port, rate, addr));
        }else {
            flag = dao.addDevice(new Device(id, name, port, rate, addr,timeout,databits,stopbits,parity,state,delay));
        }
        PrintWriter out = resp.getWriter();
        if (flag){
            out.write("添加设备成功！");
        }else
            out.write("添加设备失败！");
        out.close();
    }
}
