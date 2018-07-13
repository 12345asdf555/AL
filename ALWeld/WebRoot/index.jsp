<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%response.setHeader("Cache-Control","no-store");%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>云智能焊接管控系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="icon" href="resources/images/title.ico" type="img/x-icon" />
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/common.css">
	<link rel="stylesheet" type="text/css" href="resources/css/iconfont.css">
	
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/index.js"></script>
	<style type="text/css">
		a{text-decoration:none;color:inherit;outline:none;}
		.imgdiv{float:left;margin-top:8px;}
	</style>
  </head>

  <body class="easyui-layout">
  	<!-- 头部 -->
  	<div region="north" split="true" style="height: 100px;" id="north">
		<div class="head-wrap" style="background-color: #eaf2f5">
			<a href="" class="logo"></a>
			<div class="search-wrap">
                    <img src="resources/images/1_06.png" />
					<a href="user/logout" id="username">welcome </a>&nbsp;|
					<a href="user/logout">注销 logout</a>
			</div>					
		</div>
	</div>
  	
  	<div region="west" hide="true" split="true" title="导航菜单 navigate" style="width: 260px;" id="west" data-options="iconCls:'icon-setting'">
	  	<div class="easyui-accordion" border="false" id="accordiondiv">
			<div title="信息管理中心<br/>information management" data-options="iconCls:'icon-manage'">
				<ul>
			        <li><a href="javascript:openPerson()" ><div class="imgdiv"><img src="resources/images/1_35.png" /></div><div>焊工管理<br/>welder management</div></a></li>
			        <li><a href="javascript:openWedJunction()"><div class="imgdiv"><img src="resources/images/1_38.png" /></div><div>焊缝管理<br/>welding bead management</div></a></li>
					<li><a href="javascript:openWeldingMachine()"><div class="imgdiv"><img src="resources/images/1_40.png" /></div><div>焊机设备管理<br/>welding equipment management</div></a></li>
			        <li class="libottom"><a href="javascript:openGather()"><div class="imgdiv"><img src="resources/images/1_42.png" /></div><div>采集模块管理<br/>gather module management</div></a></li>
		        </ul>
		    </div>
		    
			<div title="报表统计中心<br/>report form statistics" data-options="iconCls:'icon-statistics'">
				<ul>
					<li><a href="javascript:openCompanyTd()"><div class="imgdiv"><img src="resources/images/1_48.png" /></div><div>实时监控<br/>real monitoring</div></a></li>
					<li><a href="javascript:openCompanyLoads()"><div class="imgdiv"><img src="resources/images/1_53.png" /></div><div>负荷率<br/>loading rate</div></a></li>
					<li><a href="javascript:openCompanyNoLoads()"><div class="imgdiv"><img src="resources/images/1_59.png" /></div><div>空载率<br/>no loading rate</div></a></li>
					<li><a href="javascript:openSingleLoads()"><div class="imgdiv"><img src="resources/images/1_53.png" /></div><div>单台负载率<br/>single load rate</div></a></li>
					<li><a href="javascript:openHistory()"><div class="imgdiv"><img src="resources/images/1_62.png" /></div><div>历史曲线<br/>history curve</div></a></li>
					<li><a href="javascript:openCompanyHour()"><div class="imgdiv"><img src="resources/images/1_69.png" /></div><div>焊缝焊接工时<br/>welding working time</div></a></li>
					<li><a href="javascript:openCompanyoverproof()"><div class="imgdiv"><img src="resources/images/1_67.png" /></div><div>焊接工艺超标<br/>welding process exceeding</div></a></li>
				    <li><a href="javascript:openWeldingmachineMax()"><div class="imgdiv"><img src="resources/images/1_69.png" /></div><div>焊机工时最高<br/>max working time(machine)</div></a></li>
			        <li><a href="javascript:openWeldingmachineMin()"><div class="imgdiv"><img src="resources/images/1_69.png" /></div><div>焊机工时最低<br/>min working time(machine)</div></a></li>
			        <li><a href="javascript:openWelderMax()"><div class="imgdiv"><img src="resources/images/1_69.png" /></div><div>焊工工时最高<br/>max working time(welder)</div></a></li>
			        <li class="libottom"><a href="javascript:openWelderMin()"><div class="imgdiv"><img src="resources/images/1_69.png" /></div><div>焊工工时最低<br/>min working time(welder)</div></a></li>
		        </ul>
		    </div>
	    </div>
	</div>
    
	<div id="mainPanle" region="center" style="background: white; overflow-y: hidden;width:400px">
		<div id="tabs" class="easyui-tabs" fit="true" border="false"></div>
		<div id="tabMenu" class="easyui-menu" style="width:150px">
			<div id="refreshtab">刷新</div>
			<div id="closetab">关闭标签页</div>
			<div id="closeLeft">关闭左侧标签页</div>
			<div id="closeRight">关闭右侧标签页</div>
			<div id="closeOther">关闭其他标签页</div>
			<div id="closeAll">关闭全部标签页</div>
	    </div>
	</div>
  </body>
</html>