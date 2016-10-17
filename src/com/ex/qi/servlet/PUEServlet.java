package com.ex.qi.servlet;

import com.ex.qi.entity.PUE;
import com.ex.qi.utils.PUEUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sunlines on 2016/9/27.
 */
public class PUEServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        PUEUtils utils = new PUEUtils();
        PUE pue = utils.getPUE();
        Gson gson = new Gson();
        String json = gson.toJson( pue,PUE.class);
        //System.out.println("发送到安卓："+json);
        out.append(json);
        out.flush();
        out.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
