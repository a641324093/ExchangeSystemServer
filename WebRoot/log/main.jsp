<%@page import="exchangesys.frame.ItemShowFrame"%>
<%@page import="exchange.db.DBForm"%>
<%@page import="exchange.db.DBConnect"%>
<%@page import="exchange.db.DBUser"%>
<%@page import="exchangesys.panel.ItemPanel"%>
<%@ page language="java" import="java.util.*,java.sql.Connection,java.text.SimpleDateFormat" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>���ﱦ</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
  </head>
  
  <body>
  
    <%
      	out.print("��ӭ��,");
           Integer user_id = (Integer)session.getAttribute("user_id");
           DBUser user =null;
           Connection conn = null;
           if(user_id!=null)
           {
           	conn = DBConnect.getConnection();
           	user = DBUser.getUserObjById(conn,Integer.valueOf(user_id));
           }
           out.println(user.name);
      %>
     
     <center>
     <table border="1">
     	<tr bgcolor="yellow">
     		<td width="150sp">����</td>
     		<td>����ʱ��</td>
     		<td width="100sp">������</td>
     		<td>�ȶ�</td>
     	</tr>
		<%
			ArrayList<DBForm> list_forms = DBForm.getAllForms(conn);
			SimpleDateFormat sdf = new SimpleDateFormat(ItemShowFrame.DATE_FORMAT);
			for(DBForm form:list_forms)
			{
				out.write("<tr>");
				//����������ӱ���
				out.write("<td><a href=\"servlet/QuaryFormServlet?form_id="+form.form_id+"\">"+form.title+"</a>"+"</td>");
				//�������ʱ��
				out.write("<td>"+sdf.format(form.sub_time)+"</td>");
				//���������
				String sub_name = DBUser.getUserObjById(conn,form.user_id).name;
				out.write("<td>"+sub_name+"</td>");
				//����ȶ�
				out.write("<td>"+form.hot+"</td>");
				out.write("</tr>");
			}
		%>
     </table>
     	<button onclick="location='log/addForm.jsp'">����������</button>
     </center>
  </body>
</html>
