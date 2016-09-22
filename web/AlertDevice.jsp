<%@ page import="com.ex.qi.dao.daoImpl.EquipmentDaoImpl" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: sunline
  Date: 2016/8/27
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  EquipmentDaoImpl dao = new EquipmentDaoImpl();
  ResultSet resultSet = dao.queryAllDevice();
  List<String> names = new ArrayList<>();
  List<String> ids = new ArrayList<>();
  while (resultSet.next()){
    String name = resultSet.getString("name");
    String id = resultSet.getString("id");
    names.add(name);
    ids.add(id);
  }
%>
<center>
    		<form action="AlertDeviceServlet" method="post" name="AlertDevice" >
    			<table width="50%" cellspacing="0"  border="1" cellpadding="3">
    			<caption><h1>Test Modify Device Configuration</h1> </caption>
    				<tr>
    					<td align="right" width="20%">
						设备名称
    					</td>
    					<td align="center" width="20%">
    						<select name="Equipment" id="Equipment" size="1"  style="width:95%">
		    					<option value="0">--------请选择--------</option>
		    					<%
                                  for (int i = 1; i < names.size()+1; i++) {
                                    out.append("<option value="+i+">"+names.get(i-1)+"</option>");
                                  }
                                %>
	    					</select>
    					</td>
    					<td align="right" width="20%"> 设备属性</td>
    					<td align="center" width="20%" >
    						<select name="attr" id="attrId" size="1" style="width:95%" >
		    					<option value="0">--------请选择--------</option>
		    					<option value="1">name</option>
		    					<option value="2">port</option>
		    					<option value="3">rate</option>
		    					<option value="4">addr</option>
		    					<option value="5">timeout</option>
		    					<option value="6">data</option>
		    					<option value="7">stop</option>
		    					<option value="8">switch</option>
		    					<option value="9">delayed</option>
	    					</select>
    					</td>
    					<td width="20%"><input type="text" name="value"  style="width:95%"></td>
    					<td width="20%"><input type="submit" name="Alert" value="Update"  style="width:95%"></td>
    				</tr>
    			</table>
    		</form>
    	</center>
