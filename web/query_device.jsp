<%--
  Created by IntelliJ IDEA.
  User: sunline
  Date: 2016/8/22
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<center>
	<form action="result.jsp" method="post" name="querydevice" >
		<table width="30%"  border="" 1="" cellspacing="" 0="" cellpadding="" 3="">
			<caption>
				<h1>测试服务器查询设备信息</h1>
				<tr>
					<td width="35%" height="30px" align="right">id</td>
					<td width="65%" height="30px"><input type="text" name="id"></td>
				</tr>
				<tr>
					<td width="35%" height="30px" align="right">name</td>
					<td width="65%" height="30px"><input type="text" name="name"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="查询"></td>
				</tr>
			</caption>
		</table>
	</form>
</center>
