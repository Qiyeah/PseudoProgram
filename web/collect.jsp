<%--
  Created by IntelliJ IDEA.
  User: sunline
  Date: 2016/8/24
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<center>
    <form action="/CollectDataServlet" method="post" name="collect">
        <table width="30%" border="1" cellspacing="0">
            <tr>
                <td width="20%" align="right">key</td>
                <td width="80%"><input type="text" name="key"></td>
            </tr>
            <tr>
                <td align="center" colspan="2"><input type="submit" name="查询"></td>
            </tr>
        </table>
    </form>
</center>
