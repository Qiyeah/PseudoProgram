<%@ page import="com.ex.qi.utils.IDUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: sunline
  Date: 2016/7/19
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    input {
        width: 90%;
    }

</style>
<center>
    <form action="/Server/AddEquipmentInfoServlet" method="post" name="addiecconfig">
        <table width="50%"  border="1" cellspacing="0" cellpadding="3">
            <caption>
                <h1>测试服务器添加设备配置</h1>
                <tr>
                    <td width="8%" align="right" valign="middle" style="font-size:22px">JSON</td>
                    <td width="42%" align="right" valign="middle"><input type="text" id="json" name="json" style="width: 99%;"></td>
                </tr>
                <tr><td  colspan="2"  align="center"><input type="submit" value="提交" style="width:200px;font-size:22px"></td></tr>
            </caption>
        </table>
    </form>
</center>