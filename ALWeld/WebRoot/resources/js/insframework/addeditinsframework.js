$(function(){
	parentCombobox();
	typeCombobox();
	updatetext();
	$("#fm").form("disableValidation");
})


var url = "";
var flag = 1;
function addInsframework(){
	flag = 1;
	url = "insframework/addInsframework";
	saveInsframework();
}

function editInsframework(){
	flag = 2;
	var id = $("#id").val();
	url = "insframework/editInsframework?id="+id;
	saveInsframework();
}
//提交
function saveInsframework(){
	var parent = $("#parent").combobox('getValue');
	var type = $("#typeid").combobox('getValue');
	var url2 = "";
	if(flag==1){
		messager = "新增成功！";
		url2 = url+"?parent="+parent+"&type="+type;
	}else{
		messager = "修改成功！";
		url2 = url+"&parent="+parent+"&type="+type;
	}
	$('#fm').form('submit', {
		url : url2,
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(result) {
			if(result){
				var result = eval('(' + result + ')');
				if (!result.success) {
					$.messager.show( {
						title : 'Error',
						msg : result.errorMsg
					});
				} else {
					$.messager.alert("提示", messager);
					var url = "insframework/goInsframework";
					var img = new Image();
				    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
				    url = img.src;  // 此时相对路径已经变成绝对路径
				    img.src = null; // 取消请求
					window.location.href = encodeURI(url);
				}
			}
			
		},  
	    error : function(errorMsg) {  
	        alert("数据请求失败，请联系系统管理员!");  
	    } 
	});
}

function updatetext(){
	var type = $("#type").val();
	var parent = $("#parentid").val();
	$("#typeid").combobox('select',type);
	$("#parent").combobox('select',parent);
}

//父节点
function parentCombobox(){
	$.ajax({  
        type : "post",  
        async : false,
        url : "insframework/getParent",  
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {
            if (result) {
                var optionStr = '';  
                for (var i = 0; i < result.ary.length; i++) {  
                    optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                            + result.ary[i].name + "</option>";  
                }  
                $("#parent").html(optionStr);
            }  
        },  
        error : function(errorMsg) {  
            alert("数据请求失败，请联系系统管理员!");  
        }  
   }); 
	$("#parent").combobox();
}

//类型
function typeCombobox(){
	$.ajax({  
      type : "post",  
      async : false,
      url : "insframework/getType",  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {
          if (result) {
              var optionStr = '';  
              for (var i = 0; i < result.ary.length; i++) {  
                  optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                          + result.ary[i].name + "</option>";  
              }
              $("#typeid").html(optionStr);
          }  
      },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      }  
 }); 
	$("#typeid").combobox();
}

