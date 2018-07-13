<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>历史曲线</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="resources/css/main.css">
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/datagrid.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/iconfont.css">
	
	<script type="text/javascript" src="resources/js/load.js"></script>
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/highcharts.js"></script>
	<script type="text/javascript" src="resources/js/getTimeADay.js"></script>
	<script type="text/javascript" src="resources/js/exporting.js"></script>
	<script type="text/javascript" src="resources/js/map.js"></script>
	<script type="text/javascript" src="resources/js/td/HistoryCurve.js"></script>

  </head>
  
<body class="easyui-layout">
	<div id="body" region="center"  hide="true"  split="true" title="历史曲线" style="background: #eee; height: 335px;">
	   	 <div id="companyOverproof_btn">
			<div style="margin-bottom: 5px;">
				<input  name="parent" id="parent" type="hidden" value="${parent }"/>
				<input  name="afresh" id="afresh" type="hidden" value="${afreshLogin }"/>
				时间：
				<input class="easyui-datetimebox" name="dtoTime1" id="dtoTime1">--
				<input class="easyui-datetimebox" name="dtoTime2" id="dtoTime2">
				时间跨度:
				<input type="radio" class="radioStyle" name="otype" value="1" />年
				<input type="radio" class="radioStyle" name="otype" value="2" />月
				<input type="radio" class="radioStyle" name="otype" value="3" checked="checked" />日
				<input type="radio" class="radioStyle" name="otype" value="4" />周
				<a href="javascript:serachCompanyOverproof();" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
			</div>
		</div>
		<div id="dgtb" style="width:100%;height:50%">
			<table id="dg" style="table-layout:fixed;width:100%;"></table>
		</div>
		<div id="load" style="width:100%;height:40%;">
		</div>
		<div id="body1" style="position:absolute;top:63%;width:100%;height:20%"></div>
		<div id="body2" style="position:absolute;top:81.5%;width:100%;height:20%"></div>
		<a class="easyui-linkbutton"  href="javascript:addtime()" iconCls="icon-add">加速</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="easyui-linkbutton" href="javascript:reducetime()" iconCls="icon-remove">减速</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="easyui-linkbutton"  href="javascript:starttime()" iconCls="icon-play">开始</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="easyui-linkbutton" href="javascript:stoptime()" iconCls="icon-pause">暂停</a>
		<a style="float: right">温馨提示：鼠标左键选中局部曲线可进行放大；‘ctrl’+鼠标左键可进行曲线平移。</a>
<!-- 		<div id="body6" style="position:absolute;left:16%;top:35%;"><label>焊机编号：</label>
		<input name="macname" id="macname" readonly="true" style="text-align:center">
		</div>
		<div id="body7" style="position:absolute;left:16%;top:45%;"><label>焊工姓名：</label>
		<input name="welname" id="welname" readonly="true" style="text-align:center">
		</div> -->
	</div>
	<style type="text/css">
    #load{ display: none; position: absolute; top: 63%; left: 0%; width: 100%; height: 37%; background-color: black; z-index:1001; -moz-opacity: 0.4; opacity:.40; filter: alpha(opacity=70);}
	#show{display: none; position: absolute; top: 80%; left: 45%; width: 10%; height: 5%; padding: 8px; border: 8px solid #E8E9F7; background-color: white; z-index:1002; overflow: auto;}
	</style>
</body>
</html>
 
 