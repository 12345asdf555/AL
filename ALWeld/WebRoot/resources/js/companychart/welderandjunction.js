$(function(){
	weldDatagrid();
	insframeworkTree();
});
var chartStr = "";
function setParam(){
	var dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	var dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	chartStr = "?dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2;
}

function weldDatagrid(){
	$("#welderTable").datagrid( {
		height : $("#northbody").height(),
		width : $("#northbody").width(),
		idField : 'id',
		pageSize : 5,
		pageList : [ 5,10, 15, 20],
		url : "welders/getAllWelder",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		pagination : true,
		fitColumns : true,
		columns : [ [  {
		    field:'ck',
			checkbox:true
		},{
			field : 'id',
			title : '序号',
			width : 100,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'name',
			title : '焊工姓名',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'welderno',
			title : '编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'cellphone',
			title : '手机',
			width : 100,
			halign : "center",
			align : "left"
		},{
			field : 'cardnum',
			title : '卡号',
			width : 100,
			halign : "center",
			align : "left"
		},{
			field : 'owner',
			title : '部门',
			width : 150,
			halign : "center",
			align : "left"
		},{
			field : 'back',
			title : '备注',
			width : 100,
			halign : "center",
			align : "left"
		}] ],
		rowStyler: function(index,row){
            if ((index % 2)!=0){
            	//处理行代背景色后无法选中
            	var color=new Object();
                color.class="rowColor";
                return color;
            }
        },
        onCheck : function(index,row){
    		chartStr = "";
        	setParam();
        	chartStr+="&welder="+row.welderno;
    		weldedJunctionDatagrid();
        },
        onClickRow : function(index,row){
    		chartStr = "";
        	setParam();
        	chartStr+="&welder="+row.welderno;
    		weldedJunctionDatagrid();
        },
        onLoadSuccess : function(index,row){
        	$("#welderTable").datagrid('selectRow',0);
        },
		nowrap : false,
		toolbar : '#commit_btn'
	});
}


function weldedJunctionDatagrid(){
	$("#weldedJunctionTable").datagrid( {
		height : $("#southbody").height()-30,
		width : $("#southbody").width(),
		idField : 'id',
		pageSize : 5,
		pageList : [ 5,10, 15, 20],
		url : "companyChart/getJunctionByWelder"+chartStr,
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		fitColumns : true,
		columns : [ [  {
		    field:'ck',
			checkbox:true
		},{
			field : 'weldedJunctionno',
			title : '焊缝编号',
			halign : "center",
			width : 100,
			align : "left"
		}, {
			field : 'maxElectricity',
			title : '电流上限',
			halign : "center",
			width : 100,
			align : "left"
		}, {
			field : 'minElectricity',
			title : '电流下限',
			halign : "center",
			width : 100,
			align : "left"
		}, {
			field : 'maxValtage',
			title : '电压上限',
			halign : "center",
			width : 100,
			align : "left"
		}, {
			field : 'minValtage',
			title : '电压下限',
			halign : "center",
			width : 100,
			align : "left"
		}, {
			field : 'itemname',
			title : '所属项目',
			halign : "center",
			width : 100,
			align : "left"
		},{
			field : 'edit',
			title : '编辑',
			width : 100,
			halign : "center",
			align : "left",
			formatter : function(value,row,index){
				return  '<a id="getoverproof" class="easyui-linkbutton" href="javascript:searchoverproof();"/>';
			}
		}] ],
		nowrap : false,
		pagination : true,
		rowStyler: function(index,row){
            if ((index % 2)!=0){
            	//处理行代背景色后无法选中
            	var color=new Object();
                color.class="rowColor";
                return color;
            }
        },
	    onLoadSuccess : function(index,row){
	        $("a[id='getoverproof']").linkbutton({text:'查看超标',plain:true,iconCls:'icon-search'});
	    }
	});
}

function serachjunction(){
	var row = $("#welderTable").datagrid('getSelected');
	if(row==null){
		alert("请先选择焊工！");
	}else{
		chartStr = "";
		setParam();
		chartStr += "&welder="+row.welderno;
		weldedJunctionDatagrid();
	}
}

function searchoverproof(){
	var row1 = $("#welderTable").datagrid('getSelected');
	var row2 = $("#weldedJunctionTable").datagrid('getSelected');
	if(row1==null || row2==null){
		alert("请先选择焊工、焊缝！");
	}else{
		var url = "companyChart/goCompanyOverproof?welder="+row1.welderno+"&junction="+row2.weldedJunctionno;
		var img = new Image();
	    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
	    url = img.src;  // 此时相对路径已经变成绝对路径
	    img.src = null; // 取消请求
		window.location.href = encodeURI(url);
	}
}

function insframeworkTree(){
	$("#myTree").tree({  
		onClick : function(node){
			$("#welderTable").datagrid('load',{
				"parent" : node.id
			})
		 }
	})
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#welderTable").datagrid('resize', {
		height : $("#northbody").height(),
		width : $("#northbody").width()
	});
	$("#weldedJunctionTable").datagrid('resize', {
		height : $("#southbody").height()-30,
		width : $("#southbody").width()
	});
}

