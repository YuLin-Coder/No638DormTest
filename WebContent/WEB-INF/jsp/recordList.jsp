<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="wdlPager" uri="wdl-page-tag" %>
<script type="text/javascript">
$(document).ready(function(){
	$('.form_date').datetimepicker({
		language:'zh-CN',/*语言  默认值：’en’ */
	    weekStart: 1,/* 一周从哪一天开始。0（星期日）到6（星期六） */
	    todayBtn:  1,/*当天日期将会被选中。  */
		autoclose: 1,//选择后自动关闭当前时间控件
		todayHighlight: 1,/*高亮当天日期。  */
		startView: 2,/* 从月视图开始，选中那一天  3为选月份*/
		minView: 2,/* 从月视图开始，选天   选完天后，不在出现下级时分秒时间选择 */
		forceParse: 0,/*就是你输入的可能不正规，但是它胡强制尽量解析成你规定的格式（format）  */
		/* format: "yyyy-mm-dd hh:ii:ss", //时间格式  yyyy-mm-dd hh:ii:ss */
	});
	
});

//文档加载完成后
window.onload = function(){
	//获取用户选中查询的宿舍楼id
	var dormBuildId = "${dormBuildId}";
	
	var dormBuildIdSelect = document.getElementById("dormBuildId");
	var options = dormBuildIdSelect.options;
	
	//遍历所有的option，如果option中的值=用户选中查询的宿舍楼id，则该option被选中
	$.each(options,function(i,option){
		$(option).attr("selected",option.value == dormBuildId);
	})
	
	
	//获取用户选中查询的搜索类型
	var searchType = "${searchType}";
	
	var searchTypeSelect = document.getElementById("searchType");
	var options = searchTypeSelect.options;
	
	//遍历所有的option，如果option中的值=用户选中查询的搜索类型，则该option被选中
	$.each(options,function(i,option){
		$(option).attr("selected",option.value == searchType);
	});
}
	
	function deleteOrAcive(recordId,disabled) {
		if(confirm("您确定要删除或激活这条记录吗？")) {
			window.location="record.action?action=deleteOrAcive&id="+recordId+"&disabled="+disabled;
		}
	}
	
	
	
	$(document).ready(function(){
		$("#record").addClass("active");
	});
</script>

<div class="data_list">
		<div class="data_list_title">
			缺勤记录
		</div>
		<form name="myForm"  action="record.action?action=list"  class="form-search" method="post"  style="padding-bottom: 0px">
				<c:if test="${session_user.roleId != 2 }">
					<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='record.action?action=preAdd'">添加</button>
				</c:if>
					
				<span class="data_search">
					<span class="controls input-append date form_date" style="margin-right: 10px" data-date-format="yyyy-mm-dd">
                    	<input id="startDate" name="startDate" style="width:120px;height: 30px;" placeholder="起始日期" type="text" 
                    		value="${startDate}" readonly >
                    	<span class="add-on"><i class="icon-remove"></i></span>
						<span class="add-on"><i class="icon-th"></i></span>
               		</span>
					<span class="controls input-append date form_date" style="margin-right: 10px" data-date-format="yyyy-mm-dd">
                    	<input id="endDate" name="endDate" style="width:120px;height: 30px;" placeholder="终止日期" type="text" 
                    		value="${endDate}" readonly>
                    	<span class="add-on"><i class="icon-remove"></i></span>
						<span class="add-on"><i class="icon-th"></i></span>
               		</span>
               		<c:if test="${session_user.roleId != 2 }">
						<select id="dormBuildId" name="dormBuildId" style="width: 100px;">
							<option value="">所有楼栋</option>
							<c:forEach items="${builds}" var="build">
								<option value="${build.id}">${build.name}</option>
							</c:forEach>
							
						</select>
					</c:if>
						<select id="searchType" name="searchType" style="width: 80px;">
							<option value="name">姓名</option>
							<option value="stuCode">学号</option>
							<option value="dormCode">宿舍编号</option>
							<option value="sex">性别</option>
						</select>
						&nbsp;<input id="keyword" name="keyword" value="${keyword }" type="text"  style="width:120px;height: 30px;" class="input-medium search-query" >
					
					
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-striped table-bordered table-hover datatable">
				<thead>
					<tr>
					<th>日期</th>
					<th>学号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>宿舍楼</th>
					<th>寝室</th>
					<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${records}"  var="record">
					<tr>
						<td><fmt:formatDate value="${record.date}"  pattern="yyyy-MM-dd"/> </td>
						<td>${record.user.stuCode}</td>
						<td>${record.user.name}</td>
						<td>${record.user.sex}</td>
						<td>${record.user.dormBuild.name}</td>
						<td>${record.user.dormCode}</td>
						<td>${record.remark}</td>
							<c:if test="${session_user.roleId != 2 }">
								<td>
									<button class="btn btn-mini btn-success" type="button" onclick="javascript:window.location='record.action?action=preUpdate&id=${record.id }'">修改</button>
									<c:if test="${record.disabled ==0}">
										<button class="btn btn-mini btn-danger" type="button" onclick="deleteOrAcive(${record.id },1)">删除</button>
									</c:if>
									<c:if test="${record.disabled ==1}">
										<button class="btn btn-mini btn-danger" type="button" onclick="deleteOrAcive(${record.id },0)">激活</button>
									</c:if>
								</td>
							</c:if>
							
							<c:if test="${session_user.roleId == 2 }">
								<td>
									<c:if test="${record.disabled == 0 }">正常状态</c:if>
									<c:if test="${record.disabled == 1 }">已删除</c:if>
								</td>
							</c:if>
					</tr>
				</c:forEach>
					
				</tbody>
			</table>
		</div>
		<div align="center"><font color="red"></font></div>
		<div style="text-align: center;">
			<!--totalNum:查询出的总数据量     pageSize：每一页展示的行数    pageIndex:表示当前页面  
				submitUrl：表示点击上一页下一页首页 尾页是发送的请求-->
			<wdlPager:pager 
				totalNum ="${totalNum}"
				pageSize="3"
				pageIndex="${pageIndex}"
				submitUrl="${pageContext.request.contextPath}/record.action?action=list&searchType=${searchType}&keyword=${keyword}&dormBuildId=${dormBuildId}&startDate=${startDate}&endDate=${endDate}"></wdlPager:pager>
		</div>
		
</div>