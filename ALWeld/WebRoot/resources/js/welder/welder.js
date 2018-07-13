$(function(){
	weldDatagrid();
});

function weldDatagrid(){
	$("#welderTable").datagrid( {
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "welder/getWelderList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 100,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'name',
			title : '姓名',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'welderno',
			title : '编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'itemname',
			title : '所属项目',
			width : 100,
			halign : "center",
			align : "left"
		}] ],
		toolbar : '#welderTable_btn',
		pagination : true,
		fitColumns : true
	});
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#welderTable").datagrid('resize', {
		height : $("#body").height(),
		width : $("#body").width()
	});
}

