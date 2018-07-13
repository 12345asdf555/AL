<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊工管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/datagrid.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/getTime.js"></script>
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript" src="resources/js/companychart/welderandjunction.js"></script>
	
  </head>
  
  <body  class="easyui-layout" style="background:#ffffff;">
  	<jsp:include  page="../insframeworktree.jsp"/>
  	<div region="center"  hide="true"  split="true" style="background: witch; height: 50%">
	  	<div id="northbody" region="north"  hide="true"  split="true" title="焊工列表" style="background: witch; height: 50%">
	  		<div id="commit_btn">
				<div style="margin-bottom: 5px;">
					时间：
					<input class="easyui-datetimebox" name="dtoTime1" id="dtoTime1">--
					<input class="easyui-datetimebox" name="dtoTime2" id="dtoTime2">
					<a href="javascript:serachjunction();" class="easyui-linkbutton" iconCls="icon-search" >搜索焊缝</a>
				</div>
			</div>
		    <table id="welderTable" style="table-layout: fixed; width:100%;"></table>
		</div>
		
	  	<div id="southbody" region="south"  hide="true"  split="true" title="焊缝列表" style="background: white; height: 50%;">
		    <table id="weldedJunctionTable" style="table-layout: fixed; width:100%;"></table>
	  		<jsp:include  page="../tenghanbottom.jsp"/>
  </body>
</html>
