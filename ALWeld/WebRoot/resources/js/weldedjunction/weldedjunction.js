$(function(){
	weldedJunctionDatagrid();
});

function weldedJunctionDatagrid(){
	$("#weldedJunctionTable").datagrid( {
//		fitColumns : true,
		height : $("#body").height()-120,
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "weldedjunction/getWeldedJunctionList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 30,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'weldedJunctionno',
			title : '编号',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'serialNo',
			title : '序列号',
//			width : 90,
			halign : "center",
			align : "left",
//			hidden:true
		}, {
			field : 'pipelineNo',
			title : '管线号',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'roomNo',
			title : '房间号',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'unit',
			title : '机组',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'area',
			title : '区域',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'systems',
			title : '系统',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'children',
			title : '子项',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'specification',
			title : '规格',
//			width : 90,
			halign : "center",
			align : "left"
//			hidden:true
		}, {
			field : 'maxElectricity',
			title : '电流上限',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'minElectricity',
			title : '电流下限',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'maxValtage',
			title : '电压上限',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'minValtage',
			title : '电压下限',
//			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'itemname',
			title : '所属项目',
//			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'creatTime',
			title : '创建时间',
			width : 90,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'updateTime',
			title : '修改时间',
			width : 90,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'updatecount',
			title : '修改次数',
			width : 90,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'valtage_unit',
			title : '电压单位',
			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'electricity_unit',
			title : '电流单位',
			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'startTime',
			title : '开始时间',
			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'endTime',
			title : '完成时间',
			width : 90,
			halign : "center",
			align : "left"
		}, {
			field : 'edit',
			title : '编辑',
			width : 150,
			halign : "center",
			align : "left",
			formatter: function(value,row,index){
				var str = '<a id="edit" class="easyui-linkbutton" href="weldedjunction/goEditWeldedJunction?id='+row.id+'"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="weldedjunction/goRemoveWeldedJunction?id='+row.id+'"/>';
//				var rows = row.weldedJunctionno+","+row.serialNo+","+row.pipelineNo+","+row.roomNo+","+row.unit
//				+","+row.area+","+row.systems+","+row.children+","+row.externalDiameter+","+row.wallThickness+","+row.dyne+","+row.specification+","+row.maxElectricity+","+
//				row.minElectricity+","+row.maxValtage+","+row.minValtage+","+row.material+","+row.nextexternaldiameter+","+row.itemname+","+row.startTime+","+row.endTime+
//				","+row.creatTime+","+row.updateTime+","+row.updatecount+","+row.nextwall_thickness+","+row.next_material+","+row.valtage_unit+","+row.electricity_unit;
//				str += '<a id="look" class="easyui-linkbutton" href="weldedjunction/goShowMoreJunction?rows='+rows+'"/>';
				return str;
			}
		}] ],
		toolbar : '#disctionaryTable_btn',
		pagination : true,
		rowStyler: function(index,row){
            if ((index % 2)!=0){
            	//处理行代背景色后无法选中
            	var color=new Object();
                color.class="rowColor";
                return color;
            }
        },
		onLoadSuccess: function(data){
	        $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	        $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
//			$("a[id='look']").linkbutton({text:'查看更多',plain:true,iconCls:'icon-add'});
		}
	});
}


//导入
function importclick(){
	$("#importdiv").dialog("open").dialog("setTitle","从excel导入数据");
}

function importWeldingMachine(){
	var file = $("#file").val();
	if(file == null || file == ""){
		$.messager.alert("提示", "请选择要上传的文件！");
		return false;
	}else{
		$('#importfm').form('submit', {
			url : "import/importWeldedJunction",
			success : function(result) {
				if(result){
					var result = eval('(' + result + ')');
					if (!result.success) {
						$.messager.show( {
							title : 'Error',
							msg : result.msg
						});
					} else {
						$('#importdiv').dialog('close');
						$('#weldingmachineTable').datagrid('reload');
						$.messager.alert("提示", result.msg);
					}
				}
				
			},  
		    error : function(errorMsg) {  
		        alert("数据请求失败，请联系系统管理员!");  
		    } 
		});
	}
}


//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#weldedJunctionTable").datagrid('resize', {
		height : $("#body").height()-120,
		width : $("#body").width()
	});
}

