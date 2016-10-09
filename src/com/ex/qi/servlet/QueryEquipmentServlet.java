package com.ex.qi.servlet;

import com.ex.qi.dao.daoImpl.EquipmentDaoImpl;
import com.ex.qi.dao.daoImpl.EquipmentInfoDaoImpl;
import com.ex.qi.entity.Equipment;
import com.ex.qi.entity.EquipmentInfo;
import com.ex.qi.entity.json.EquipmentArray;
import com.ex.qi.entity.json.EquipmentConfig;
import com.ex.qi.entity.json.EquipmentJSON;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunlines on 2016/10/9.
 */
public class QueryEquipmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EquipmentJSON list = new EquipmentJSON();
        List<EquipmentArray> configs1 = new ArrayList<>();
        EquipmentDaoImpl equipmentDao = new EquipmentDaoImpl();
        Equipment[] equipments = equipmentDao.findAllEquipments();

        int length = equipments.length;

        EquipmentConfig[] configs = new EquipmentConfig[length];

        EquipmentInfoDaoImpl infoDao = new EquipmentInfoDaoImpl();
        for (int i = 0; i < length; i++) {
            String key = equipments[i].getId();
            EquipmentInfo[] infos = infoDao.findEquipmentInfos(equipments[i].getId());
            configs[i] = new EquipmentConfig(equipments[i],infos);
            configs1.add(new EquipmentArray(key,configs));
        }

        Gson gson = new Gson();
        String json = gson.toJson(new EquipmentJSON(configs1),EquipmentJSON.class);
        PrintWriter out = resp.getWriter();
        out.append(json);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
