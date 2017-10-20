<%@page import="exchange.db.*"%>
<%@page import="exchangesys.panel.ItemPanel"%>
<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat,java.sql.Connection" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'updateForm.jsp' starting page</title>
    
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
    <body>
  	<%
  		DBForm form = null;
  		SimpleDateFormat sdf =null;
    	form = (DBForm)session.getAttribute("form");
  		sdf = new SimpleDateFormat(ItemPanel.DATE_FORMAT);
    %>
    	<center>
	  	<h2 align="center">标题:<%=form.title%></h2>
	  	<font size="4">更新时间:<%=sdf.format(form.sub_time)%></font>&nbsp;&nbsp;
	  	<font size="4">发布人:<%=form.user.name%></font> &nbsp;&nbsp;
	  	<font size="4">热度:<%=form.hot%></font><br/>
	  	<font size="4">物品名:<%=form.item.name%></font>
	  	<font size="4">物品类别:<%=form.item.cat.name%></font>
		<h4>物品描述:</h4>
		<textarea rows="5" cols="40" ><%=form.item.desc%></textarea>
		<h4>表单描述:</h4>
		<textarea rows="8" cols="60"><%=form.desc%></textarea><br/>
	<button onclick="location='login.jsp?action=update' ">修改</button>
	<button onclick="location='login.jsp?action=delete' ">删除</button>
	</center>
  </body>
  </body>
</html>
