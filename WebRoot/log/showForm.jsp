<%@page import="exchange.db.*"%>
<%@page import="exchangesys.frame.*"%>
<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat,java.sql.Connection" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 	 <%
    	DBForm form = (DBForm)session.getAttribute("form");
  		SimpleDateFormat sdf = new SimpleDateFormat(ItemShowFrame.DATE_FORMAT);
  		Connection conn = DBConnect.getConnection();
  		//�����ȶ�
  		DBForm.addFormHot(conn, form.form_id);
    %>
    <base href="<%=basePath%>">
    
    <title><%=form.title%></title>
    
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
  	
    	<center>
	  	<h2 align="center">����:<%=form.title%></h2>
	  	<font size="4">����ʱ��:<%=sdf.format(form.sub_time)%></font>&nbsp;&nbsp;
	  	<font size="4">������:<%=form.user.name%></font> &nbsp;&nbsp;
	  	<font size="4">�ȶ�:<%=form.hot%></font><br/>
	  	<font size="4">��Ʒ��:<%=form.item.name%></font>
	  	<font size="4">��Ʒ���:<%=form.item.cat.name%></font>
		<h4>��Ʒ����:</h4>
		<textarea rows="5" cols="40" disabled="disabled"><%=form.item.desc%></textarea>
		<h4>������:</h4>
		<textarea rows="8" cols="60" disabled="disabled"><%=form.desc%></textarea><br/>
	<%
		
		if(DBUser.isOwnerOfForm(conn, form.user_id, form.item_id))
		{
			//out.print();
		}
	%>
	<button onclick="location='' ">�޸�</button>
	<button onclick="location='servlet/OperactionFormServlet?action=delete' ">ɾ��</button>
	<button onclick="location='log/main.jsp' ">����</button>
	</center>
  </body>
</html>
