<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改焊机设备</title>
    
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
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
<!-- 	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script> -->
	<script type="text/javascript" src="resources/js/weldingMachine/addeditweldingmachine.js"></script>
  </head>
  
  <body  class="easyui-layout" style="background:#ffffff;">
  	<div class="divborder">
  		<div class="divtitle">修改焊机设备</div>
  	</div>
<%--   	<jsp:include  page="../insframeworktree.jsp"/> --%>
    <div  id="body" region="center"  hide="true"  split="true" style="background: white; height: 335px;margin-top: 70px;">
		<div style="text-align: center ">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
				<div class="fitem">
					<lable><span class="required">*</span>固定资产编号</lable>
					<input type="hidden" id="wid" value="${w.id }">
					<input type="hidden" id="valideno" value="${w.equipmentNo }">
					<input class="easyui-textbox" name="equipmentNo" id="equipmentNo"  value="${w.equipmentNo }" data-options="validType:['wmEnoValidate'],required:true"/>
				</div>
				<div class="fitem">
					<lable><span class="required">*</span>设备类型</lable>
					<input type="hidden"  id="type" value="${w.typeId }">
					<select class="easyui-combobox" name="typeId" id="tId" data-options="required:true,editable:false"></select>
				</div>
				<div class="fitem">
					<lable>入厂时间</lable>
					<input class="easyui-datetimebox" name="joinTime" id="joinTime" value="${w.joinTime }"/>
				</div>
				<div class="fitem">
					<lable><span class="required">*</span>所属项目</lable>
					<input type="hidden"  id="insframework" value="${w.insframeworkId.id }">
					<select class="easyui-combobox" name="insframeworkId" id="iId" data-options="required:true,editable:false"></select>
				</div>
				<div class="fitem">
					<lable><span class="required">*</span>生产厂商</lable>
					<input type="hidden"  id="manu" value="${w.manufacturerId.id }">
					<select class="easyui-combobox" name="manuno" id="manuno" data-options="required:true,editable:false"></select>
				</div>
				<div class="fitem">
					<lable>采集序号</lable>
					<input type="hidden" id="validgid" value="${w.gatherId.gatherNo }">
					<input type="hidden" id="gid" value="${w.gatherId.id }">
					<select class="easyui-combobox" name="gatherId" id="gatherId" value="${w.gatherId.gatherNo }" data-options="validType:['checkNumber','wmGatheridValidate'],editable:false"></select>
				</div>
				<div class="fitem">
					<lable>设备位置</lable>
					<input class="easyui-textbox" name="position" id="position" value="${w.position }"/>
				</div>
				<div class="fitem">
					<lable>ip地址</lable>
					<input class="easyui-textbox" name="ip" id="ip" value="${w.ip }"/>
				</div>
				<div class="fitem">
					<lable>设备型号</lable>
					<input class="easyui-textbox" name="model" id="model" value="${w.model }"/>
				</div>
				<div class="fitem" >
					<lable>是否联网</lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="hidden" id="isnw" value="${w.isnetworking }"/>
					<input type="radio" class="radioStyle" name="isnetworking" value="0"/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" class="radioStyle" name="isnetworking" value="1"/>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<div class="fitem">
					<input type="hidden"  id="status" value="${w.statusId }"/>
					<lable>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态</lable>
	   				<span id="radios"></span>
				</div>
				<div class="weldbutton">
					<lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:editWeldingMachine();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="weldingMachine/goWeldingMachine" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
					</lable>
				</div>
			</form>
		</div>
  		<jsp:include  page="../tenghanbottom.jsp"/>
	</div>
  </body>
</html>
