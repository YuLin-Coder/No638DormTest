<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function userDeleteOrActive(id,disabled) {
		if(confirm("您确定要删除或激活这个宿管吗？")) {
			window.location="dormManager.action?action=deleteOrAcive&id="+id+"&disabled="+disabled;
		}
	}
	
	//文档加载完后
	window.onload=function(){
		//获取后台保存的当前要修改的foodTypeId值
		var searchType = "${searchType}";
		//获取菜系select标签
		var searchTypeSelect = document.getElementById("searchType");
		//获取下拉框中所有的option
		var  options = searchTypeSelect.options;
		
		//遍历菜系select标签中所有的option标签
		$.each( options, function(i, option){
		  $(option).attr("selected",option.value == searchType);
		});
	}
	
	$(document).ready(function(){
		$("#dormManager").addClass("active");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
			宿舍管理员管理
		</div>
		<form name="myForm" class="form-search" method="post" action="dormManager.action?action=list">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" 
					onclick="javascript:window.location='dormManager.action?action=preAdd'">添加</button>
				<span class="data_search">
					<select id="searchType" name="searchType" style="width: 80px;">
						<option value="name">姓名</option>
						<option value="sex">性别</option>
						<option value="tel">电话号码</option>
					</select>
					&nbsp;<input id="keyword" name="keyword" type="text" value="${keyword}" style="width:120px;height: 30px;" class="input-medium search-query" ">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tr>
					<th>登录名</th>
					<th>姓名</th>
					<th>性别</th>
					<th>电话</th>
					<th>宿舍楼</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${users}"  var="user" varStatus="stat">
					<tr>
						<td>${user.stuCode }</td>
						<td>${user.name }</td>
						<td>${user.sex }</td>
						<td>${user.tel }</td>
						<td>
							<!-- 遍历宿舍管理员管理的所有宿舍楼 -->
							<c:forEach items="${user.dormBuilds}" var="dormBuild">
								${dormBuild.name} &nbsp;
							</c:forEach>
						</td>
						<td>
							<button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='dormManager.action?action=preUpdate&id=${user.id}'">修改</button>&nbsp;
							<c:if test="${user.disabled == 0 }">
								<button class="btn btn-mini btn-danger" type="button" onclick="userDeleteOrActive(${user.id },1)">删除</button>
							</c:if>
							<c:if test="${user.disabled == 1}">
								<button class="btn btn-mini btn-danger" type="button" onclick="userDeleteOrActive(${user.id },0)">激活</button>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div align="center"><font color="red"></font></div>
</div>