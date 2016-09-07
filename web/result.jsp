<%@ page import="com.ex.qi.dao.daoImpl.DeviceDaoImpl" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.ex.qi.entity.Device" %>
<%--
  Created by IntelliJ IDEA.
  User: sunline
  Date: 2016/8/22
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String id = request.getParameter("id");
    DeviceDaoImpl dao = new DeviceDaoImpl();
    ResultSet set = dao.queryDevice(id);
    Device device = null;
   while (set.next()) {
        String name = set.getString("name");
        String port = set.getString("port");
        int rate = set.getInt("rate");
        int addr = set.getInt("addr");
        int state = set.getInt("state");
        int delay = set.getInt("delay");
        device = new Device(id, name, port, rate, addr, state, delay);
    }
%>
设备ID:<%=request.getParameter("id")%><br>
设备名称：<%=device.getName()%><br>
端口名称：<%=device.getPort()%><br>
波特率：<%=device.getRate()%><br>
设备地址：<%=device.getAddr()%><br>
设备状态：<%=device.getState()%><br>
设备延时：<%=device.getDelay()%><br>
