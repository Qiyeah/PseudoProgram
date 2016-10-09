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

}
