package com.ex.qi.servlet;

import com.ex.qi.dao.daoImpl.EquipmentDaoImpl;
import com.ex.qi.entity.Equipment;
import com.ex.qi.utils.LogUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Date;

/**
 * Created by sunline on 2016/8/22.
 */
public class AddEquipmentServlet extends HttpServlet {
    public int KEY_IEC = 0x01;
    public int KEY_MOD = 0x02;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
        System.out.println("doPost is run");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet is run");

        /**
         * 从客户端读数据
         */
        InputStream is = req.getInputStream();
        byte[] buf=new byte[1024];
        int len = 0 ;
        String str = "";
        while (-1 != (len = is.read(buf))){
            str += new String(buf,0,len);

        }
        System.out.println("str = "+str);

        String json = req.getParameter("json");

        //System.out.println(json);

        Gson gson = new Gson();

        Equipment equipment = gson.fromJson(str, Equipment.class);
        equipment.setDate(new Date(System.currentTimeMillis()));

        EquipmentDaoImpl dao = new EquipmentDaoImpl();

        boolean flag = false;



        flag = dao.addEquipment(equipment);
        printLog(equipment,flag);

        PrintWriter out = resp.getWriter();

        if (flag) {
            out.write("添加设备成功！");
        } else
            out.write("添加设备失败！");

        out.close();
    }
    private void printLog(Equipment equipment, boolean flag){
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
            if (flag){
                sb.append(new String("添加设备成功！".getBytes(),"gbk"));
            }else {
                sb.append(new String("添加设备失败！".getBytes(),"gbk"));
            }
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        }
        sb.append(LogUtils.CRLF);
        LogUtils utils = new LogUtils();
        utils.writeLog("equipment.log",sb);
    }
}
