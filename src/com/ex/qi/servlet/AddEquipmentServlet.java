package com.ex.qi.servlet;

import com.ex.qi.dao.daoImpl.EquipmentDaoImpl;
import com.ex.qi.entity.Equipment;
import com.ex.qi.http.HttpUtils;
import com.ex.qi.utils.LogUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.Base64;
import java.util.Base64.Decoder;

/**
 * Created by sunline on 2016/8/22.
 */
public class AddEquipmentServlet extends HttpServlet {
    public int KEY_IEC = 0x01;
    public int KEY_MOD = 0x02;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String className = getClass().getName();
        new HttpUtils().handlerAndroidRequest(className,req);
        //handlerAndroidRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req, resp);
    }
    /*private void printLog(Equipment equipment, boolean flag){
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
        utils.writeLog("equipment.log", sb);
    }
    private void parseJson(HttpServletRequest req, HttpServletResponse resp){
        InputStream is = null;
        String encoding = req.getCharacterEncoding();
        String contentType = req.getContentType();
        String uri= req.getRequestURI();
        System.out.println(uri);
        System.out.println(contentType);
        int len = 0;
        try {
            req.setCharacterEncoding(encoding);
            len = req.getContentLength();
            is = req.getInputStream();
            System.out.println("len = " + len);
            byte[] buf = new byte[len];
            is.read(buf);
            String res = new String(buf);
            String res1 = new String(buf,encoding);
            res =  new URLDecoder().decode(res,encoding);
            System.out.println(res);
            System.out.println(res1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String parseParameter( HttpServletRequest req, HttpServletResponse resp){
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader=null;
        try{
            reader = new BufferedReader(new InputStreamReader(req.getInputStream(),"UTF-8"));
            String line=null;
            while((line = reader.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(null!=reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String json = buffer.toString();
        System.out.println(json);
        return json;
    }
    private void handlerAndroidRequest(HttpServletRequest req, HttpServletResponse resp){
        //从客户端读数据

        try {
            InputStream is = req.getInputStream();
            byte[] buf=new byte[1024];
            int len = 0 ;
            String str = "";
            while (-1 != (len = is.read(buf))){
                str += new String(buf,0,len);
            }
            if (null != str && !"".equals(str)){
                System.out.println("str = "+str);
                Gson gson = new Gson();
                Equipment equipment = gson.fromJson(str, Equipment.class);
                equipment.setDate(new Date(System.currentTimeMillis()));
                EquipmentDaoImpl dao = new EquipmentDaoImpl();
                boolean flag = false;
                flag = dao.addEquipment(equipment);
                printLog(equipment,flag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
