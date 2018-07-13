$(function(){
	typeCombobox();
	equipmentCombobox();
	updatetext();
	$("#fm").form("disableValidation");
})


var url = "";
var maintainfalg = true;
function addMaintain(){
	maintainfalg = true;
	url = "maintain/addMaintain";
	saveMaintain();
}

function editMaintain(){
	maintainfalg = false;
	var mid = $("#mid").val();
	url = "maintain/editMaintain?mid=" + mid;
	saveMaintain();
}
//提交
function saveMaintain(){
	var wid = $("#equipmentNo").combobox('getValue');
	var tid = $("#typeId").combobox('getValue');
	var url2 = "";
	if(maintainfalg){
		messager = "新增成功！";
		url2 = url+"?tId="+tid+"&wId="+wid;
	}else{
		messager = "修改成功！";
		url2 = url+"&tId="+tid+"&wid="+wid;;
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
					var url = "maintain/goMaintain";
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
	//隐藏文本框
	$("#mid").next().hide();
	$("#wid").next().hide();
	$("#type").next().hide();
	var type = $("#type").val();
	var wid = $("#wid").val();
	$("#typeId").combobox('select',type);
	$("#equipmentNo").combobox('select',wid);
}

//维修类型
function typeCombobox(){
	$.ajax({  
        type : "post",  
        async : false,
        url : "maintain/getComboboxValue",  
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {
            if (result) {
                var optionStr = '';  
                for (var i = 0; i < result.ary2.length; i++) {  
                    optionStr += "<option value=\"" + result.ary2[i].typeid + "\" >"  
                            + result.ary2[i].typename + "</option>";  
                }  
                $("#typeId").html(optionStr);
            }  
        },  
        error : function(errorMsg) {  
            alert("数据请求失败，请联系系统管理员!");  
        }  
   }); 
	$("#typeId").combobox();
}

//设备编号
function equipmentCombobox(){
	$.ajax({  
      type : "post",  
      async : false,
      url : "maintain/getComboboxValue",  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {  
          if (result) {
              var optionStr = '';
              for (var i = 0; i < result.ary1.length; i++) {  
                  optionStr += "<option value=\"" + result.ary1[i].mid + "\" >"  
                          + result.ary1[i].equipmentNo + "</option>";
              }
              $("#equipmentNo").html(optionStr);
          }  
      },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      }  
	});
	$("#equipmentNo").combobox();
}
