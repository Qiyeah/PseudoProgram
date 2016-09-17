package com.ex.qi.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunline on 2016/8/27.
 */
@WebServlet(name = "alertDeviceServlet",urlPatterns = "/AlertDeviceServlet")
public class AlertEquipmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] value = req.getParameterValues("device");

    }
}
