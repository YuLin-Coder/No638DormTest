<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		format: "yyyy-mm-dd", //时间格式  yyyy-mm-dd hh:ii:ss */
	});
	
});

function checkForm(){
	//检查用户是否输入学号和缺勤日期
	var stuCode=document.getElementById("stuCode").value;
	var date=document.getElementById("date").value;
	
	if(stuCode==null||stuCode=="" || date == null|| date ==""){
		document.getElementById("error").innerHTML="学号和缺勤日期不能为空！";
		return false;
	}
	return true;
	
}
	
	$(document).ready(function(){
		$("#record").addClass("active");
		
		var recordId = $("#id").val();
		if(recordId != null && recordId!=""){
			$('#stuCode').attr("readonly","readonly")//将input元素设置为readonly
		}else{
			$('#stuCode').removeAttr("readonly");
		}
	});
</script>
<div class="data_list">
		<div class="data_list_title">
			<c:if test="${not empty record.id }">
				修改缺勤记录
			</c:if>
			<c:if test="${ empty record.id }">	
				添加缺勤记录
			</c:if>
		</div>
		<form action="record.action?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
				<div align="center">
					<font id="error" color="red">${error}</font>
				</div>
				<input type="hidden" id="id" name="id" value="${record.id}"/>
				<table align="center">
					<tr>
						<td><font color="red">*</font>学号：</td>
						<td><input type="text" id="stuCode"  name="stuCode" value="${record.user.stuCode}"   style="margin-top:5px;height:30px;" /></td>
					</tr>
					<tr>
						<td><font color="red">*</font>日期：</td>
	                    <td><input id="date" name="date" value="<fmt:formatDate value="${record.date}"  pattern="yyyy-MM-dd"/>" style="margin-top:5px;height:30px;" placeholder="缺勤日期" type="text" class="controls input-append date form_date" readonly ></td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><input type="text" id="remark"  name="remark" value="${record.remark}"  style="margin-top:5px;height:30px;" /></td>
					</tr>
				</table>
				<div align="center">
					<input type="submit" class="btn btn-primary" value="保存"/>
					&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
				</div>
			</div>
		</form>
</div>