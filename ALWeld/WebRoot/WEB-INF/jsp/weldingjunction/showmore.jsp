<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊缝信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
  </head>
  
  <body  class="easyui-layout" style="background:#ffffff;">
  	<div class="divborder">
  		<div class="divtitle">焊缝信息</div>
  	</div>
    <div id="body" region="north" hide="true"  split="true" style="background: witch; height: 80%;margin-top: 70px;">
    	<div style="text-align:center;height:100%">
			<div class="fitem">
				<lable>编号</lable>
				<input class="easyui-textbox" readonly="readonly" value="${weldedJunctionno }"/>
				<lable>序列号</lable>
				<input class="easyui-textbox" readonly="readonly" value="${serialNo}"/>
			</div>
			<div class="fitem">
				<lable>机组</lable>
				<input class="easyui-textbox" readonly="readonly" value="${unit }"/>
				<lable>区域</lable>
				<input class="easyui-textbox" readonly="readonly" value="${area }"/>
			</div>
			<div class="fitem">
				<lable>系统</lable>
				<input class="easyui-textbox" readonly="readonly" value="${systems }"/>
				<lable>子项</lable>
				<input class="easyui-textbox" readonly="readonly" value="${children }"/>
			</div>
			<div class="fitem">
				<lable>达因</lable>
				<input class="easyui-textbox" readonly="readonly" value="${dyne }"/>
				<lable>规格</lable>
				<input class="easyui-textbox" readonly="readonly" value="${specification }"/>
			</div>
			<div class="fitem">
				<lable>管线号</lable>
				<input class="easyui-textbox" readonly="readonly" value="${pipelineNo }"/>
				<lable>房间号</lable>
				<input class="easyui-textbox" readonly="readonly" value="${roomNo }"/>
			</div>
			<div class="fitem">
				<lable>上游外径</lable>
				<input class="easyui-textbox" readonly="readonly" value="${externalDiameter }"/>
				<lable>下游外径</lable>
				<input class="easyui-textbox" readonly="readonly" value="${nextexternaldiameter }"/>
			</div>
			<div class="fitem">
				<lable>上游璧厚</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wallThickness }"/>
				<lable>下游璧厚</lable>
				<input class="easyui-textbox" readonly="readonly" value="${nextwall_thickness }"/>
			</div>
			<div class="fitem">
				<lable>上游材质</lable>
				<input class="easyui-textbox" readonly="readonly" value="${material }"/>
				<lable>下游材质</lable>
				<input class="easyui-textbox" readonly="readonly" value="${next_material }"/>
			</div>
			<div class="fitem">
				<lable>电流上限</lable>
				<input class="easyui-textbox" readonly="readonly" value="${maxElectricity }"/>
				<lable>电流下限</lable>
				<input class="easyui-textbox" readonly="readonly" value="${minElectricity }"/>
			</div>
			<div class="fitem">
				<lable>电压上限</lable>
				<input class="easyui-textbox" readonly="readonly" value="${maxValtage }"/>
				<lable>电压下限</lable>
				<input class="easyui-textbox" readonly="readonly" value="${minValtage }"/>
			</div>
			<div class="fitem">
				<lable>电流单位</lable>
				<input class="easyui-textbox" readonly="readonly" value="${electricity_unit }"/>
				<lable>电压单位</lable>
				<input class="easyui-textbox" readonly="readonly" value="${valtage_unit }"/>
			</div>
			<div class="fitem">
				<lable>所属项目</lable>
				<input class="easyui-textbox" readonly="readonly" value="${itemname }"/>
				<lable>开始时间</lable>
				<input class="easyui-textbox" readonly="readonly" value="${startTime }"/>
			</div>
			<div class="fitem">
				<lable>完成时间</lable>
				<input class="easyui-textbox" readonly="readonly" value="${endTime }"/>
				<lable>创建时间</lable>
				<input class="easyui-textbox" readonly="readonly" value="${creatTime }"/>
			</div>
			<div class="fitem">
				<lable>修改时间</lable>
				<input class="easyui-textbox" readonly="readonly" value="${updateTime }"/>
				<lable>修改次数</lable>
				<input class="easyui-textbox" readonly="readonly" value="${updatecount }"/>
			</div>
			<div style="margin-left:50px">
				<lable>
					<a href="weldedjunction/goWeldedJunction" class="easyui-linkbutton" iconCls="icon-cancel">返回</a>
				</lable>
			</div>
		<jsp:include  page="../tenghanbottom.jsp"/>
		</div>
	</div>
  </body>
</html>
