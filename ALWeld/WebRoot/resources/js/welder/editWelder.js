/**
 * 
 */
		
			$(function(){
				   $("#fm").form("disableValidation");
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
				         $("#owner").html(optionstring);
				      } else {
				         alert('部门加载失败');
				      }
				      $("#owner").combobox();
				      $("#owner").combobox('select',document.getElementById("owners").value);
				   },
				   error: function () {
				      alert('error');
				   }
				});
		})
		
					
		var flag = 2;
		 function saveWps(){
		 flag = 2;
         var insframework = $('#owner').combobox('getValue');
          var url;
          url = "welders/updateWelder"+"?ins="+insframework;
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
              			$.messager.alert("提示", "修改成功");
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
         