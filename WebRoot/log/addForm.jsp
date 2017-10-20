<%@page import="exchange.db.*"%>
<%@page import="exchange.db.DBCat"%>
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
    
    <title>发布新交换单</title>
    
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
   	<form action="servlet/OperactionFormServlet" method="post">
	  	<font size="4">标题:</font><input type="text" name="title"><br/>
	  	<font size="4">物品名:</font><input type="text" name="item_name"><br/>
	  	<font size="4">物品类别:</font>
	  	<select name="cat">
	  		<% 
	  			Connection conn = DBConnect.getConnection();
	  			ArrayList<DBCat> list_cats = DBCat.getAllCatObj(conn);
	  			int len = list_cats.size();
	  			DBCat cat = null;
	  			for(int i=0;i<len;i++)
	  			{
	  				cat = list_cats.get(i);
	  				out.print("<option value='"+cat.cat_id+"'>"+cat.name+"</option>");
	  			}
	  		%>
	  		
	  	</select>
		<h4>物品描述:</h4>
		<textarea rows="5" cols="40" name="item_desc"></textarea>
		<h4>表单描述:</h4>
		<textarea rows="8" cols="60" name="form_desc"></textarea><br/>
		<input type="submit" value="发布">
		<input type="reset" value="清空">
	</form>
	</center>
  </body>
</html>
