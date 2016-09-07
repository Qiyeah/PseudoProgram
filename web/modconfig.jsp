<%--
  Created by IntelliJ IDEA.
  User: sunline
  Date: 2016/8/19
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<center>
  <form action="ManagerDeviceServlet" method="post" name="addiecconfig">
    <table width="30%" height="350px" border="1" cellspacing="1" cellpadding="3">
      <caption><h1>测试服务器添加多路表配置</h1></caption>
      <tr>
        <td width="35%" align="right" valign="middle">key</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="key" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td width="35%" align="right" valign="middle">配置ID</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="id" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td width="35%" align="right" valign="middle">波特率</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="rate" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td width="35%" align="right" valign="middle">通讯地址</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="addr" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td width="35%" align="right" valign="middle">通道1属性</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="path1" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td width="35%" align="right" valign="middle">通道2属性</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="path2" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td width="35%" align="right" valign="middle">通道3属性</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="path3" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td width="35%" align="right" valign="middle">通道4属性</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="path4" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td width="35%" align="right" valign="middle">通道5属性</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="path5" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td width="35%" align="right" valign="middle">通讯延时</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="delay" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td width="35%" align="right" valign="middle">设备ID</td>
        <td width="65%" align="right" valign="middle"><input type="text" name="device" style="width:100%;height:100%"></td>
      </tr>
      <tr>
        <td align="center" width="40%" colspan="2"><input type="submit" value="添加配置"></td>
      </tr>
    </table>
  </form>
</center>
