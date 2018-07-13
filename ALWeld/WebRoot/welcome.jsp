<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>欢迎使用</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	
  </head>
  
  <body style="background:#ffffff;">
	<div style="text-align:center;padding-top: 200px;"> 
		<h1>云智能焊接管控系统欢迎您的到来！<br/>
		Cloud intelligent welding control system welcomes your arrival.</h1> 
		<div class="tenghan-bottom">
	    	Copyright 1998-2018 上海腾悍智能科技有限公司
		</div>
	</div> 
  </body>
</html>
