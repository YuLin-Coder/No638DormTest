<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function checkForm(){
		//通过ID获取输入框中用户输入的值
		var name=document.getElementById("name").value;
		var password=document.getElementById("passWord").value;
		var rPassword=document.getElementById("rPassword").value;
		
		var sex=document.getElementById("sex").value;
		var tel=document.getElementById("tel").value;
		
		//获取所有的宿舍楼复选框
		var dormBuildIdCheckBox = document.getElementsByName("dormBuildId");
		var checkBoxValue = new Array();
		for (var i = 0; i < dormBuildIdCheckBox.length; i++) {
			if(dormBuildIdCheckBox[i].checked){
				//复选框被选中，dormBuildIdCheckBox[i].checked返回值为true，将复选框中的值添加到数组中
				checkBoxValue.push(dormBuildIdCheckBox[i].value);
			}
		}
		
		if(name==""||password==""||rPassword==""||sex==""||tel==""|| checkBoxValue.length<1){
			document.getElementById("error").innerHTML="信息填写不完整！";
			return false;
			
		} else if(password!=rPassword){
			document.getElementById("error").innerHTML="密码填写不一致！";
			return false;
		}else if(!/^1[34578]\d{9}$/.test(tel)){ 
			document.getElementById("error").innerHTML="手机号码格式错误！";
	        return false; 
	    } 
		return true;
	}
	
	//文档加载完成后
	window.onload = function(){
		//获取所有的复选框 
		var dormBuildIdInputs = document.getElementsByName("dormBuildId");
		
		//获取当前宿舍管理员管理的楼栋ID  通过"${userBuildids}"获取得到的是一个String类型的数据
		var userBuildids = JSON.parse("${userBuildids}");
		
		for (var i = 0; i < dormBuildIdInputs.length; i++) {
			$.each(userBuildids, function(j, userBuildid){
				if(dormBuildIdInputs[i].value == userBuildid){
					dormBuildIdInputs[i].checked = true;
				}
				
			});
		}
	}
	
	
	$(document).ready(function(){
		$("#dormManager").addClass("active");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
			<c:if test="${not empty user.id}">
				修改管理员
			</c:if>
			<c:if test="${empty user.id}">
				添加管理员
			</c:if>
		</div>
		<form action="dormManager.action?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
				<input type="hidden" id="id" name="id" value="${user.id}"/>
					<div align="center">
						<font id="error" color="red"></font>
					</div>
					<table align="center">
						<tr>
							<td><font color="red">*</font>登录名：</td>
							<td><input type="text" id="stuCode"  name="stuCode" value="${user.stuCode}"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>姓名：</td>
							<td><input type="text" id="name"  name="name" value="${user.name}"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>密码：</td>
							<td><input type="password" id="passWord"  name="passWord" value="${user.passWord}"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>重复密码：</td>
							<td><input type="password" id="rPassword"  name="rPassword" value=""  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>性别：</td>
							<td>
								<select id="sex" name="sex" style="width: 90px;">
									<option value="男" ${user.sex eq "男" ? "selected" : ''}>男</option>
									<option value="女" ${user.sex eq "女" ? "selected" : ''}>女</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><font color="red">*</font>联系电话：</td>
							<td><input type="text" id="tel"  name="tel" value="${user.tel}"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>管理楼栋：</td>
							<td>
								<!--遍历所有的宿舍楼  -->
								<c:forEach items="${builds}"  var="build">
									<input name="dormBuildId" value="${build.id}"  style="heigth:14px;vertical-align:top"  type="checkbox" >
									${build.name}  &nbsp;
								</c:forEach>
							</td>
						</tr>
					</table>
					<div align="center">
						<input type="submit" class="btn btn-primary" value="保存"/>
						&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
					</div>
					
			</div>
		</form>
</div>