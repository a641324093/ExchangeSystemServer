<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>���ﱦ��¼����</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
  	function check()
  	{
  		//alert("1");
		var user_name = document.getElementById("user_name").value;
  		var pw = document.getElementById("password").value;
  		//alert(user_name+" "+pw);
  		if(user_name==""||pw=="")
  		{
  			alert("�û��������벻��Ϊ��");
  			return false;
  		}
  		return true;
  	}
  </script>
  </head>
  
  <body>
    <center>
    <form method="post" action="servlet/LoginServlet" name="log_form" onsubmit="return check()">
    <h2><font color="blue">��ӭ��½���ﱦ</font></h2>
    	<font>�û�����<font><input type="text" name="user_name" id="user_name"><br/>
    	<font>&nbsp&nbsp&nbsp���룺<font><input type="password" name="password" id="password"><br/>
    	<input type="submit" value="��¼"> 
    	<input type="button" value="ע��" onclick="window.location='MyJsp.jsp'">
    </form>
    </center>
  </body>
</html>
