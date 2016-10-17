package com.ex.qi.servlet;

import com.ex.qi.entity.PUE;
import com.ex.qi.utils.LogUtils;
import com.ex.qi.utils.PUEUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by sunline on 2016/8/25.
 */
//@WebServlet(name = "querypue",urlPatterns = "/QueryPueServlet")
public class QueryPueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PUEUtils utils = new PUEUtils();
        Gson gson = new Gson();
        PUE pue = new PUE();
        pue.setDayPue(utils.calculatePue(PUEUtils.TABLE_DAYKWH));
        pue.setMonthPue(utils.calculatePue(PUEUtils.TABLE_MONTHKWH));
        pue.setYearPue(utils.calculatePue(PUEUtils.TABLE_YEARKWH));
        pue.setPastDayPue(utils.calculatePue(PUEUtils.TABLE_ACCDAYKWH));
        pue.setPastMonthPue(utils.calculatePue(PUEUtils.TABLE_ACCMONKWH));
        pue.setPastYearPue(utils.calculatePue(PUEUtils.TABLE_ACCYEARKWH));
        String json = gson.toJson(pue,PUE.class);
        PrintWriter out = resp.getWriter();
        boolean flag = null != json && !json.equals("");
        if (flag){
            out.write(json);
        }else
            out.write("Failed to get data!");
        out.flush();
        out.close();
       // printLog(pue,flag);
    }
    private void printLog(PUE pue, boolean flag){
        StringBuffer sb = new StringBuffer();
        sb.append("day_pue = " + pue.getDayPue());
        sb.append(LogUtils.CRLF);
        sb.append("day_pue = " + pue.getMonthPue());
        sb.append(LogUtils.CRLF);
        sb.append("day_pue = " + pue.getYearPue());
        sb.append(LogUtils.CRLF);
        sb.append("day_pue = " + pue.getPastDayPue());
        sb.append(LogUtils.CRLF);
        sb.append("day_pue = " + pue.getPastMonthPue());
        sb.append(LogUtils.CRLF);
        sb.append("day_pue = " + pue.getPastYearPue());
        sb.append(LogUtils.CRLF);
        try {
            if (flag){
                sb.append(new String("查询pue成功！".getBytes(),"gbk"));
            }else {
                sb.append(new String("查询pue失败！".getBytes(),"gbk"));
            }
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        }
        sb.append(LogUtils.CRLF);
        LogUtils utils = new LogUtils();
        utils.writeLog("pue.log",sb);
    }
}
