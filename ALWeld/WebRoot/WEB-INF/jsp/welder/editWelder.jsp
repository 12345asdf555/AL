<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改焊工</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/welder/editWelder.js"></script>
	
  </head>
  <body  class="easyui-layout" style="background:#ffffff;">
  	<div class="divborder">
  		<div class="divtitle">修改焊工</div>
  	</div>
	<div  id="body" region="center"  hide="true"  split="true" style="background: white; height: 335px;margin-top: 70px;">
	    <div id="toolbar" style="text-align: center ">
	       <form action="" id="fm" method="post" data-options="novalidate:true" style="margin:0;padding:20px 50px">
	            <div style="margin-bottom:10px;display: none;">
	                <input name="FID" id="FID" class="easyui-textbox" type="hidden" value="${welder.id}">
	            </div>
	            <div class="fitem">
	            	<lable><span class="required">*</span>编号</lable>
	            	<input id="validName" type="hidden" value="${welder.welderno}">
	                <input name="welderno" id="welderno" class="easyui-textbox" value="${welder.welderno}" data-options="validType:'welderValidate',required:true">
	            </div>
	            <div class="fitem">
	            	<lable><span class="required">*</span>姓名</lable>
	                <input name="name" class="easyui-textbox" value="${welder.name}" data-options="required:true">
	            </div>
	            <div class="fitem">
	            	<lable><span class="required">*</span>手机</lable>
	                <input name="cellphone" type="easyui-textbox" value="${welder.cellphone}" class="easyui-textbox" data-options="validType:'phoneNum',required:true">
	            </div>
	            <div class="fitem">
	            	<lable><span class="required">*</span>卡号</lable>
	                <input name="cardnum" class="easyui-textbox" value="${welder.cardnum}" data-options="required:true">
	            </div>
<!-- 	                        <div class="fitem"> -->
<!-- 	            	<lable>提交时间</lable> -->
<%-- 	                <input name="createdate" class="easyui-textbox" value="${create}" readonly="true" data-options="required:true" > --%>
<!-- 	            </div> -->
<!-- 	                        <div class="fitem"> -->
<!-- 	            	<lable>修改时间</lable> -->
<%-- 	                <input name="updatedate" class="easyui-textbox" value="${update}" readonly="true" data-options="required:true"> --%>
<!-- 	            </div> -->
<!-- 	            <div class="fitem"> -->
<!-- 					<lable>级别</lable> -->
<%-- 					<input name="leveid" id="leveid" type="hidden" value="${welder.leveid}" data-options="required:true"> --%>
<%-- 					<select class="easyui-combobox" name="leve" id="leve" value="${welder.leveid}" data-options="required:true,editable:false"></select> --%>
<!-- 	        	</div> -->
<!-- 	        	<div class="fitem"> -->
<!-- 					<lable>资质</lable> -->
<%-- 					<input name="quali" id="quali" type="hidden" value="${welder.quali}" data-options="required:true"> --%>
<%-- 					<select class="easyui-combobox" name="qua" id="qua" value="${welder.quali}" data-options="required:true,editable:false"></select> --%>
<!-- 	        	</div> -->
	            <div class="fitem">
					<lable><span class="required">*</span>部门</lable>
					<input name="owners" id="owners" type="hidden" value="${welder.owner}" data-options="required:true">
					<select class="easyui-combobox" name="owner" id="owner" value="${welder.owner}" data-options="required:true,editable:false"></select>
	        	</div>
				<div class="fitem">
	            	<lable>备注</lable>
	                <input name="back" value="${welder.back}" class="easyui-textbox">
	            </div>
		    	<div>
					<lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				        <a href="javascript:saveWps();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				        <a href="welders/AllWelder" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
			        </lable>
		    	</div>
	        </div>
		</form>
	</div> 
  	<jsp:include  page="../tenghanbottom.jsp"/>
    </div>
</body>
</html>