$(function(){
	typeCombobox();
	InsframeworkCombobox();
	manuCombobox();
	statusRadio();
	gatherCombobox();
	$("#iId").combobox({
        onChange:function(){  
        	itemid = $("#iId").combobox("getValue");
        	gatherCombobox();
        } 
     });
	editText();
	$("#fm").form("disableValidation");
})


var url = "";
var flag = 1;
function addWeldingMachine(){
	flag = 1;
	url = "weldingMachine/addWeldingMachine";
	saveWeldingMachine();
}

function editWeldingMachine(){
	flag = 2;
	var wid = $("#wid").val();
	url = "weldingMachine/editWeldingMachine?wid="+wid;
	saveWeldingMachine();
}

//提交
function saveWeldingMachine(){
	var tid = $('#tId').combobox('getValue');
	var iid = $('#iId').combobox('getValue');
	var gatherId = $('#gatherId').combobox('getValue');
	var manuno = $('#manuno').combobox('getValue');
	var sid = $("input[name='statusId']:checked").val();
	var isnetworking = $("input[name='isnetworking']:checked").val();
	var messager = "";
	var url2 = "";
	if(flag==1){
		messager = "新增成功！";
		url2 = url+"?tId="+tid+"&iId="+iid+"&sId="+sid+"&isnetworking="+isnetworking+"&manuno="+manuno;
	}else{
		messager = "修改成功！";
		url2 = url+"&tId="+tid+"&iId="+iid+"&sId="+sid+"&isnetworking="+isnetworking+"&manuno="+manuno+"&gatherId="+gatherId;
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
					var url = "weldingMachine/goWeldingMachine";
					var img = new Image();
				    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
				    url = img.src;  // 此时相对路径已经变成绝对路径
				    img.src = null; // 取消请求
					window.location.href = encodeURI(url);
					$("#valideno").val("");
					$("#validgid").val("");
					$("#valideid").val("");
				}
			}
			
		},  
	    error : function(errorMsg) {  
	        alert("数据请求失败，请联系系统管理员!");  
	    } 
	});
}

function editText(){	
	//隐藏文本处理传值
	var isnw = $("#isnw").val();
	var status = $("#status").val();
	var type = $("#type").val();
	var insframework = $("#insframework").val();
	var manu = $("#manu").val();
	var gid = $("#gid").val();
	$('[name="isnetworking"]:radio').each(function() { 
		if (this.value ==isnw ) { 
			this.checked = true;
		} 
	});
	$('[name="statusId"]:radio').each(function() { 
		if (this.value ==status ) { 
			this.checked = true;
		} 
	});
	$('#tId').combobox('select',type);
	$('#iId').combobox('select',insframework);
	$('#manuno').combobox('select',manu);
	
	$('#gatherId').combobox('select',gid);
}

var itemid = "";
//采集序号
function gatherCombobox(){
	$.ajax({  
      type : "post",  
      async : false,
      url : "weldingMachine/getGatherAll?itemid="+itemid,  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {
          var optionStr = "";
          if (result) {
        	  if(result.ary.length<=0){
        		  optionStr += "<option></option>";  
        	  }else{
	              for (var i = 0; i < result.ary.length; i++) {  
	                  optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
	                          + result.ary[i].name + "</option>";  
	              }
        	  }
              $("#gatherId").html(optionStr);
          }  
      }
	}); 
	$("#gatherId").combobox();
}

//设备类型
function typeCombobox(){
	$.ajax({  
        type : "post",  
        async : false,
        url : "weldingMachine/getTypeAll",  
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {
            if (result) {
                var optionStr = '';  
                for (var i = 0; i < result.ary.length; i++) { 
                    optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                            + result.ary[i].name + "</option>";  
                } 
                $("#tId").append(optionStr);
            }  
        },  
        error : function(errorMsg) {  
            alert("数据请求失败，请联系系统管理员!");  
        }  
   }); 
	$("#tId").combobox();
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
              $("#iId").html(optionStr);
          }  
      },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      }  
	}); 
	$("#iId").combobox();
}

//厂商
function manuCombobox(){
	$.ajax({  
	  type : "post",  
	  async : false,
	  url : "weldingMachine/getManuAll",  
	  data : {},  
	  dataType : "json", //返回数据形式为json  
	  success : function(result) {  
	      if (result) {
	          var optionStr = '';
	          for (var i = 0; i < result.ary.length; i++) {  
	              optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
	                      + result.ary[i].name + " - " +result.ary[i].type + "</option>";
	          }
	          $("#manuno").html(optionStr);
	      }  
	  },  
	  error : function(errorMsg) {  
	      alert("数据请求失败，请联系系统管理员!");  
	  }  
	}); 
	$("#manuno").combobox();
}

//焊机状态
function statusRadio(){
	$.ajax({  
	    type : "post",  
	    async : false,
	    url : "weldingMachine/getStatusAll",  
	    data : {},  
	    dataType : "json", //返回数据形式为json  
	    success : function(result) {
	    	if (result) {
	    		var str = "";
	    		for (var i = 0; i < result.ary.length; i++) {
	    			str += "<input type='radio' class='radioStyle' name='statusId' id='sId' value=\"" + result.ary[i].id + "\" />"  
                    + result.ary[i].name;
	    		}
	            $("#radios").html(str);
	            $("input[name='statusId']").eq(0).attr("checked",true);
	        }  
	    },  
	    error : function(errorMsg) {  
	        alert("数据请求失败，请联系系统管理员!");  
	    }  
	});
}
