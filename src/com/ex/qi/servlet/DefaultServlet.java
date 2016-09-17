package com.ex.qi.servlet;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.utils.TableUtils;
import com.google.gson.Gson;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunline on 2016/8/18.
 */

@WebServlet(name = "index", urlPatterns = "/DefaultServlet",loadOnStartup = 0)
public class DefaultServlet extends HttpServlet {
    public static final int KEY_DEGREE = 0x01;
    public static final int IEC = 0x01;
    public static final int MODBUS = 0x02;
    public byte[] mCmdBytes;
    private int key = 0;
    private String jsonStr;

    private JSONObject mJSONObject = null;
    private Gson gson = new Gson();
    private BaseDaoImpl mBaseDao = null;
    private TableUtils mTableUtils = null;
    /*@Override
    public void init() throws ServletException {
        super.init();
        mBaseDao = new BaseDaoImpl();
        mTableUtils = new TableUtils();
        if (!mBaseDao.tableIsExists("Equipment")){
            mTableUtils.newDeviceTable();
        }
        if (!mBaseDao.tableIsExists("DeviceInfo"))
            mTableUtils.newDeviceInfoTable();
        if (!mBaseDao.tableIsExists("RealKwh")){
            mTableUtils.newRealKwhTable();
        }
        if (!mBaseDao.tableIsExists("DayKwh")){
            mTableUtils.newDayKwhTable();
        }
        if (!mBaseDao.tableIsExists("YearKwh")){
            mTableUtils.newYearKwhTable();
        }
        if (!mBaseDao.tableIsExists("MonthKwh")){
            mTableUtils.newMonthKwhTable();
        }
        if (!mBaseDao.tableIsExists("AccumDayKwh")){
            mTableUtils.newAccumDayKwhTable();
        }
         if (!mBaseDao.tableIsExists("AccumMonthKwh")){
            mTableUtils.newAccumMonthKwhTable();
        }
        if (!mBaseDao.tableIsExists("AccumYearKwh")){
            mTableUtils.newAccumYearKwhTable();
        }
        mBaseDao = null;
        mTableUtils = null;
    }*/

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
