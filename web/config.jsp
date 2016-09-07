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
    <form action="AddConfigServlet" method="post" name="addiecconfig">
        <table width="90%" border="1" cellspacing="0" cellpadding="3">
            <caption>
                <h1>测试服务器添加设备配置</h1>
                <tr>
                    <td width="3%" align="center" valign="middle">ID</td>
                    <td width="8%" align="center" valign="middle"><input type="text" name="id"
                                                                       value=<%=IDUtils.getId(IDUtils.DEVICEINFO)%>></td>

                    <td width="4%" align="center" valign="middle">通道号</td>
                    <td width="4%" align="center" valign="middle"><input type="text" name="route"></td>

                    <td width="4%" align="center" valign="middle">通道名称</td>
                    <td width="4%" align="center" valign="middle"><input type="text" name="pathname"></td>

                    <td width="4%" align="center" valign="middle">通道属性</td>
                    <td width="4%" align="center" valign="middle"><input type="text" name="pathattr" value="1"></td>

                    <td width="4%" align="center" valign="middle">设备ID</td>
                    <td width="8%" align="center" valign="middle"><input type="text" name="fid"></td>
                    <td width="4%" align="center" valign="middle">占比</td>
                    <td width="4%" align="center" valign="middle"><input type="text" name="per" value="100"></td>
                    <td width="4%" align="center" valign="middle">symbol</td>
                    <td width="4%" align="center" valign="middle"><input type="text" name="symbol" value="1"></td>
                    <td width="4%" align="center"><input type="submit" value="提交"></td>
                </tr>

            </caption>
        </table>
    </form>
</center>