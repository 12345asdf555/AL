$(function(){
	GatherDatagrid();
	insframeworkTree();
});

function GatherDatagrid(){
	$("#gatherTable").datagrid( {
		fitColumns : true,
		height : $("#body").height()-120,
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "gather/getGatherList",
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
			field : 'gatherNo',
			title : '采集模块编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'itemid',
			title : '项目id',
//			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'itemname',
			title : '所属项目',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'status',
			title : '采集模块状态',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'protocol',
			title : '采集模块通讯协议',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'ipurl',
			title : '采集模块IP地址',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'macurl',
			title : '采集模块MAC地址',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'leavetime',
			title : '采集模块出厂时间',
			width : 160,
			halign : "center",
			align : "left"
		}, {
			field : 'edit',
			title : '编辑',
			width : 150,
			halign : "center",
			align : "left",
			formatter:function(value,row,index){
				var str = "";
				str += '<a id="edit" class="easyui-linkbutton" href="javascript:editGather('+row.itemid+','+row.id+','+true+')"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="javascript:editGather('+row.itemid+','+row.id+','+false+')"/>';
				return str;
			}
		}] ],
		toolbar : '#gather_btn',
		pagination : true,
		nowrap : false,
		rowStyler: function(index,row){
            if ((index % 2)!=0){
            	//处理行代背景色后无法选中
            	var color=new Object();
                color.class="rowColor";
                return color;
            }
        },
		onLoadSuccess:function(data){
	        $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	        $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
		}
	});
}

function editGather(id,gid,flags){
	$.ajax({  
        type : "post",  
        async : false,
        url : "insframework/getUserAuthority?id="+id,  
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {
            if (result) {
        		if(result.afreshLogin==null || result.afreshLogin==""){
            		if(result.flag){
            			var url = "";
            			if(flags){
            				url = "gather/goeditGather?id="+gid;
            			}else{
            				url = "gather/goremoveGather?id="+gid;
            			}
	       				var img = new Image();
	       			    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
	       			    url = img.src;  // 此时相对路径已经变成绝对路径
	       			    img.src = null; // 取消请求
	       				window.location.href = encodeURI(url);
            		}else{
            			alert("对不起，您不能对你的上级或同级部门的数据进行编辑");
            		}
            	}else{
            		$.messager.confirm("提示",result.afreshLogin,function(data){
		        		 if(data){
		        			var url = "login.jsp";
		       				var img = new Image();
		       			    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
		       			    url = img.src;  // 此时相对路径已经变成绝对路径
		       			    img.src = null; // 取消请求
		     				 top.location.href = url;
		     			 }
		     		 });
		        }
           }
        },  
        error : function(errorMsg) {  
            alert("数据请求失败，请联系系统管理员!");  
        }  
   });
}


//树形菜单点击事件
function insframeworkTree(){
	$("#myTree").tree({  
		onClick : function(node){
			$("#gatherTable").datagrid('load',{
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
	$("#gatherTable").datagrid('resize', {
		height : $("#body").height()-120,
		width : $("#body").width()
	});
}

