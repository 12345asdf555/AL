$(function(){
	InsframeworkCombobox();
	$("#fm").form("disableValidation");
	$("input",$("#weldedjunctionno").next("span")).blur(function(){
		var wjno = $("#weldedjunctionno").val();
		var len = $("#weldedjunctionno").val().length;
		if(len<6){
			for(var i=0;i<6-len;i++){
				wjno = "0"+wjno;
			}
		}
		$("#weldedjunctionno").textbox('setValue',wjno);
	})  
})

var url = "";
var flag = 1;
function addWeldedjunction(){
	flag = 1;
	url = "weldedjunction/addWeldedJunction";
	saveWeldingMachine();
}

function editWeldedjunction(){
	flag = 2;
	var wid = $("#wid").val();
	url = "weldedjunction/editWeldedJunction";
	saveWeldingMachine();
}

//提交
function saveWeldingMachine(){
	var messager = "";
	var url2 = "";
	if(flag==1){
		messager = "新增成功！";
		url2 = url;
	}else{
		messager = "修改成功！";
		url2 = url;
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
				}else{
					$.messager.alert("提示", messager);
					var url = "weldedjunction/goWeldedJunction";
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

//所属项目
function InsframeworkCombobox(){
	$.ajax({  
      type : "post",  
      async : false,
      url : "weldingMachine/getInsframeworkAll",  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {  
          if (result) {
              var optionStr = '';
              for (var i = 0; i < result.ary.length; i++) {  
                  optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                          + result.ary[i].name + "</option>";
              }
              $("#itemname").html(optionStr);
          }  
      },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      }  
	}); 
	$("#itemname").combobox();
    $("#itemname").combobox('setValue',$("#itemid").val());
}


