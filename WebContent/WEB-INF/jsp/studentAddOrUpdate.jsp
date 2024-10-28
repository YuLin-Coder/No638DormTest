<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function checkForm(){
		var stuCode=document.getElementById("stuCode").value;
		var name=document.getElementById("name").value;
		var sex=document.getElementById("sex").value;
		var tel=document.getElementById("tel").value;
		var password=document.getElementById("passWord").value;
		var rPassword=document.getElementById("rPassword").value;
		var dormBuildId=document.getElementById("dormBuildId").value;
		var dormCode=document.getElementById("dormCode").value;
		
		if(stuCode=="" ||name==""||tel=="" ||password==""||rPassword==""||dormBuildId==""||dormCode==""){
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
		//获取当前要修改的学生居中的宿舍楼id
		var studentBuildId = "${userUpdate.dormBuildId}";
		
		var dormBuildIdSelect = document.getElementById("dormBuildId");
		var options = dormBuildIdSelect.options;
		
		//遍历所有的option，如果option中的值=学生居中的宿舍楼id，则该option被选中
		$.each(options,function(i,option){
			$(option).attr("selected",option.value == studentBuildId);
		})
	}
	
	$(document).ready(function(){
		$("#student").addClass("active");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
			<c:if test="${not empty userUpdate.id}">
				修改学生
			</c:if>
			<c:if test="${ empty userUpdate.id}">	
				添加学生
			</c:if>
		</div>
		<form action="student.action?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
					<div align="center">
						<font id="error" color="red">${error}</font>
						<input type="hidden" id="id"  name="id" value="${userUpdate.id}" />
					</div>
					<table align="center">
						<tr>
							<td><font color="red">*</font>学号：</td>
							<td><input type="text" id="stuCode"  name="stuCode" value="${userUpdate.stuCode}"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>姓名：</td>
							<td><input type="text" id="name"  name="name" value="${userUpdate.name}"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>性别：</td>
							<td>
								<select id="sex" name="sex" style="width: 90px;">
									<option value="男" ${userUpdate.sex == "男 " ? 'selected' : ""}>男</option>
									<option value="女" ${userUpdate.sex == "女" ? 'selected' : ""}>女</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><font color="red">*</font>联系电话：</td>
							<td><input type="text" id="tel"  name="tel" value="${userUpdate.tel}"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>密码：</td>
							<td><input type="password" id="passWord"  name="passWord" value="${userUpdate.passWord}"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>重复密码：</td>
							<td><input type="password" id="rPassword"  name="rPassword" value=""  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>宿舍楼：</td>
							<td>
								<select id="dormBuildId" name="dormBuildId" style="width: 90px;">
									<c:forEach items="${builds}" var="build">
										<option value="${build.id}">${build.name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td><font color="red">*</font>寝室编号：</td>
							<td><input type="text" id="dormCode"  name="dormCode" value="${userUpdate.dormCode}"  style="margin-top:5px;height:30px;" /></td>
						</tr>
					</table>
					<div align="center">
						<input type="submit" class="btn btn-primary" value="保存"/>
						&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
					</div>
			</div>
		</form>
</div>