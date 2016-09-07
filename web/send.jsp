<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<center>
	<form action="/ManagerDeviceServlet" method="post" name="collection">
		<table border="1" cellspacing="0" width="40%" cellpadding="0px">
			<caption><h1>测试服务器采集数据的功能</h1></caption>
			<tr>
				<td width="20%" height="40px" align="right">key</td>
				<td><input name="key" type="text" style="width:100%;height:100%"></td>
			</tr>
			<tr>
				<td width="20% " height="40px" align="right">json格式的命令</td>
				<td><input  name="cmd" type="text" style="width:100%;height:100%"></td>
			</tr>
			<tr>
				<td width="20%" height="40px" align="right">采集的次数</td>
				<td><input name="count" type="text" style="width:100%;height:100%"></td>
			</tr>
			<tr>
				<td width="100%" height="40px" align="center" colspan="2"><input type="submit" value="提交查询"></td>
			</tr>
		</table>
	</form>
</center>