<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
function checkForm(){
	var oldPassword=document.getElementById("oldPassword").value;
	var newPassword=document.getElementById("newPassword").value;
	var rPassword=document.getElementById("rPassword").value;
	
	 $("#error").html("");
	if(oldPassword==""||newPassword==""||rPassword==""){
		document.getElementById("error").innerHTML="信息填写不完整！";
		return false;
	} else if(newPassword == oldPassword){
		document.getElementById("error").innerHTML="修改前后密码不能一致！";
		return false;
	}else if(newPassword!=rPassword){
		document.getElementById("error").innerHTML="新密码两次填写不一致！";
		return false;
	}
	/* 
	if(onblurFn(oldPassword)){
		return false;
	}
	 */
	window.location.href="password.action?action=change&newPassword="+newPassword;
}


function onblurFn(oldPassWord){
	$("#error").html(""); 
	if(oldPassWord != null && oldPassWord != ""){
		//校验用户输入的原密码正确
		$.ajax({
			   type: "POST",
			   url: "${pageContext.request.contextPath}/password.action?action=ajaxOldPassWord",
			   data: "oldPassWord="+oldPassWord,
			   async:false,//同步请求
			   success: function(msg){
			     if(msg){
			    	 //用户输入的原密码不正确
			    	 document.getElementById("oldPassword").value="";
			    	$("#error").html(msg); 
			    	return true;
			     }else{
			    	 return false;
			     }
			   }
			});
	}else{
		document.getElementById("error").innerHTML="请输入原密码！";
		return false;
	}
}
 
	
	$(document).ready(function(){
		$("ul li:eq(5)").addClass("active");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
			修改密码
		</div>
		<form action=" ">
			<div class="data_form" >
					<table align="center">
						<tr>
							<td><font color="red">*</font>原密码：</td>
							<td><input type="password"  id="oldPassword"  name="oldPassword" onblur="onblurFn(this.value)"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>新密码：</td>
							<td><input type="password" id="newPassword"  name="newPassword" value="" style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>重复密码：</td>
							<td><input type="password" id="rPassword"  name="rPassword" value="" style="margin-top:5px;height:30px;" /></td>
						</tr>
					</table>
					<div align="center">
						<input type="button" class="btn btn-primary" onclick="checkForm()" value="提交"/>
					</div>
					<div align="center">
						<font id="error" color="red"></font>
					</div>
			</div>
		</form>
</div>