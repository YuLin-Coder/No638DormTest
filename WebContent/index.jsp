<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>宿舍管理系统登录</title>
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script type="text/javascript">
//top表示最顶级的窗口，也就是最外层的窗口。self指代当前窗口对象
/* if ( top.location != self.location ){
	alert("不是顶层窗口");
	top.location =self.location;
} */

if(window.location != parent.window.location){
	   //将当前页面作为最顶级页面
	   parent.window.location = window.location;
	   alert("不是顶层窗口"); 
}

	function checkForm() {
		//判断用户是否输入的学号和密码
		var stuCode = document.getElementById("stuCode").value;
		var password = document.getElementById("password").value;
		if (stuCode == null || stuCode == "") {
			document.getElementById("error").innerHTML = "账号不能为空";
			return false;
		}
		if (password == null || password == "") {
			document.getElementById("error").innerHTML = "密码不能为空";
			return false;
		}
		
		return true;
	}
	
	$(document).ready(function(){
		$("#login").addClass("active");
	});
	
	
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
      
      .radio {
      	padding-top: 10px;
       	padding-bottom:10px;
      }
      
      .form-signin-heading{
      	text-align: center;
      }

      .form-signin {
        max-width: 300px;
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
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
</style>

</head>
<body>
<div class="container">
      <form name="myForm" class="form-signin" action="login" method="post" onsubmit="return checkForm()">
        <h2 class="form-signin-heading"><font color="gray">宿舍管理系统</font></h2>
        <input type="hidden"  name="action"  value="submit">
        <!-- 如果想让控件像块级元素一样占满容器，就可以为它添加 .input-block-level 类。这样，控件不仅可以占满容器，还可以根据浏览器窗口自动调整尺寸 -->
        <input id="stuCode" name="stuCode" value="" type="text" class="input-block-level" placeholder="账号...">
        <input id="password" name="password" value="" type="password" class="input-block-level" placeholder="密码..." >
        <div>
			<input type="radio" name="roleId"  value="0" style="height:10px; vertical-align:top;"><span>超级管理员</span>
		
			<input type="radio" name="roleId"  value="1" style="height:10px; vertical-align:top;"><span>宿舍管理员</span>
		
			<input type="radio" name="roleId"  value="2" checked="checked"  style="height:10px; vertical-align:top;"><span>学生</span>
		</div>
        <label class="checkbox" style="margin-top: 10px">
          <input id="remember" name="remember" type="checkbox" value="remember-me" >记住我 &nbsp;&nbsp;&nbsp;&nbsp; 
          <font id="error" color="red">${error}</font>  
        </label>
        <div style="text-align: center;">
        <!--按钮类型为submit：点击按钮，会将表单的内容提交到 action属性值login的请求处理类中 -->
        <button class="btn btn-large btn-primary" type="submit">登录</button> &nbsp; <a href="register.do"    >学生注册</a>
		 &nbsp;&nbsp;&nbsp;&nbsp;
        </div>
		<p align="center" style="padding-top: 15px;">版权所有  2021 </p>
      </form>
</div>
</body>
</html>