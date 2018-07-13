$(function(){
	insframeworkTree();
	weldingMachineDatagrid();
});

function weldingMachineDatagrid(){
	$("#weldingmachineTable").datagrid( {
		height : $("#body").height()-30,
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "weldingMachine/getWedlingMachineList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false, 
        columns : [ [ {
		    field:'ck',
			checkbox:true
		},{
			field : 'id',
			title : '序号',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'equipmentNo',
			title : '固定资产编号',
			halign : "center",
			align : "left"
		}, {
			field : 'typeName',
			title : '设备类型',
			halign : "center",
			align : "left"
		}, {
			field : 'jointime',
			title : '入厂时间',
			halign : "center",
			align : "left"
		}, {
			field : 'insframeworkName',
			title : '所属项目',
			halign : "center",
			align : "left"
		}, {
			field : 'statusName',
			title : '状态',
			halign : "center",
			align : "left"
		} , {
			field : 'manufacturerName',
			title : '厂家',
			halign : "center",
			align : "left"
		}, {
			field : 'isnetworking',
			title : '是否在网',
			halign : "center",
			align : "left"
		}, {
			field : 'gatherId',
			title : '采集序号',
			halign : "center",
			align : "left"
		}, {
			field : 'position',
			title : '位置',
			halign : "center",
			align : "left"
		}, {
			field : 'ip',
			title : 'ip地址',
			halign : "center",
			align : "left"
		}, {
			field : 'model',
			title : '设备型号',
			halign : "center",
			align : "left"
		}] ],
		toolbar : '#weldingmachineTable_btn',
		pagination : true,
		rowStyler: function(index,row){
            if ((index % 2)!=0){
            	//处理行代背景色后无法选中
            	var color=new Object();
                color.class="rowColor";
                return color;
            }
        }, onCheck:function(index,row){
    		var url = "companyChart/goSingleLoads?machine="+row.id;
    		var img = new Image();
    	    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
    	    url = img.src;  // 此时相对路径已经变成绝对路径
    	    img.src = null; // 取消请求
    		window.location.href = encodeURI(url);
        }, onClickRow:function(index,row){
    		var url = "companyChart/goSingleLoads?machine="+row.id;
    		var img = new Image();
    	    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
    	    url = img.src;  // 此时相对路径已经变成绝对路径
    	    img.src = null; // 取消请求
    		window.location.href = encodeURI(url);
        }
	});
}


function insframeworkTree(){
	$("#myTree").tree({  
		onClick : function(node){
			$("#weldingmachineTable").datagrid('load',{
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
	$("#weldingmachineTable").datagrid('resize', {
		height : $("#body").height()-30,
		width : $("#body").width()
	});
}

