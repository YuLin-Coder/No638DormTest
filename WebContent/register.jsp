<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>宿舍管理系统注册</title>
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
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
	//提交表单
	document.getElementById("registerform").submit();
}

	
</script>

<style type="text/css">
	  body {
        padding-top: 200px;
        padding-bottom: 40px;
        background-image: url('images/bg.jpg');
        background-position: center;
		background-repeat: no-repeat;
		background-attachment: fixed;
      }
      
      
      .form-signin-heading{
      	text-align: center;
      }

      .form-signin {
        max-width: 450px;
        padding: 19px 29px 0px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
</style>

</head>
<body>
<div class="container">
      <form action="register.do?action=save" id="registerform" method="post"  class="form-signin">
        <h2 class="form-signin-heading"><font color="gray">宿舍管理系统学生注册</font></h2>
      		 <table align="center">
				<tr>
					<td><font color="red">*</font>学号：</td>
					<td><input type="text" id="stuCode"  name="stuCode"   style="margin-top:5px;height:30px;" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>姓名：</td>
					<td><input type="text" id="name"  name="name"   style="margin-top:5px;height:30px;" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>性别：</td>
					<td>
						<select id="sex" name="sex" style="width: 90px;">
							<option value="男" >男</option>
							<option value="女" >女</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><font color="red">*</font>联系电话：</td>
					<td><input type="text" id="tel"  name="tel" value=""  style="margin-top:5px;height:30px;" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>密码：</td>
					<td><input type="password" id="passWord"  name="passWord" value=""  style="margin-top:5px;height:30px;" /></td>
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
					<td><input type="text" id="dormCode"  name="dormCode" "  style="margin-top:5px;height:30px;" /></td>
				</tr>
				
			</table>
			<div align="center">
				<input type="button" class="btn btn-primary" value="保存" onclick="checkForm()"/>
				&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
			</div>
			<div align="center">
				<font id="error" color="red">${error}</font>  
			</div>
      </form>
</div>
</body>
</html>