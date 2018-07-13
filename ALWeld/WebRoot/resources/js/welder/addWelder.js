$(function(){
	insframworkCombobox();
//	leveCombobox();
//	quaidCombobox();
	$("#fm").form("disableValidation");
})

function insframworkCombobox(){
	   $.ajax({
	   type: "post", 
	   async : false,
	   url: "weldingMachine/getInsframeworkAll",
	   dataType: "json",
	   data: {},
	   success: function (result) {
	      if (result) {
	         var optionstring = "";
	         //循环遍历 下拉框绑定
             for (var i = 0; i < result.ary.length; i++) {  
            	 optionstring += "<option value=\"" + result.ary[i].id + "\" >" + result.ary[i].name + "</option>";
             }
	         $("#Fowner").html(optionstring);
	      } else {
	         alert('部门加载失败');
	      }
	      $("#Fowner").combobox();
	   },
	   error: function () {
	      alert('error');
	   }
	});
	
}

function leveCombobox(){
	var we=8;
	   $.ajax({
	   type: "post", 
	   async : false,
	   url: "welders/getLeve?we="+we,
	   dataType: "json",
	   data: {},
	   success: function (result) {
	      if (result) {
	         var optionstring = "";
	         //循环遍历 下拉框绑定
	         for(var k=0;k<result.rows.length;k++){
	         optionstring += "<option value=\"" + result.rows[k].leveid + "\" >" + result.rows[k].levename + "</option>";
	         }
	         $("#leveid").html(optionstring);
	      } else {
	         alert('部门加载失败');
	      }
	      $("#leveid").combobox();
	   },
	   error: function () {
	      alert('error');
	   }
	});
}
		
function quaidCombobox(){
	var wee=7;
		   $.ajax({
		   type: "post", 
		   async : false,
		   url: "welders/getLeve?we="+wee,
		   dataType: "json",
		   data: {},
		   success: function (result) {
		      if (result) {
		         var optionstring = "";
		         //循环遍历 下拉框绑定
		         for(var k=0;k<result.rows.length;k++){
		         optionstring += "<option value=\"" + result.rows[k].quaid + "\" >" + result.rows[k].quaname + "</option>";
		         }
		         $("#quali").html(optionstring);
		      } else {
		         alert('部门加载失败');
		      }
		      $("#quali").combobox();
		   },
		   error: function () {
		      alert('error');
		   }
		});
}

var flag = 1; 
function saveWelder(){
    flag = 1;
    var insframework = $('#Fowner').combobox('getValue');
//    var leve = $('#leveid').combobox('getValue');
//    var qua = $('#quali').combobox('getValue');
     var url;
      url = "welders/addWelder"+"?ins="+insframework;
        $('#fm').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('enableValidation').form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.errorMsg){
                    $.messager.show({
                        title: 'Error',
                        msg: result.errorMsg
                    });
                } else {
				$.messager.alert("提示", "新增成功");
				var url = "welders/AllWelder";
				var img = new Image();
			    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
			    url = img.src;  // 此时相对路径已经变成绝对路径
			    img.src = null; // 取消请求
				window.location.href = encodeURI(url);
                }
            }
        });
    }
        