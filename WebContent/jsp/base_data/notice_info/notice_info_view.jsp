<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详情页面</title>
<script type="text/javascript">
	$(function() {

	});

	//返回列表
	function goBackList() {
		window.location = "${path}/noticeController/toList.do";
	}
</script>
</head>
<body>
	<div>
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li><a href="#">首页</a></li>
				<li><a href="#">信息管理</a></li>
				<li><a href="#">通知管理</a></li>
				<li><a href="#">通知</a></li>
			</ul>
		</div>
		<div class="formbody">
			<div class="formtitle">
				<span>通知详情</span>
			</div>
			<form id="add_form" method="post">
				<input type="hidden" name="id" value="${vo.id}" />
				<ul class="forminfo">
					
					<li><label>内容:</label> <textarea rows="5" name="instruction"
							id="instruction" class="form-control input-primary"
							style="width: 350px; resize: none;" readonly="readonly">${vo.content}</textarea></li>
					<li><label>类型：</label>
					<c:if test="${vo.type=='1' }">
					<input 
						value="仿真通知" type="text" class="form-control input-primary"
						disabled="disabled" style="cursor: not-allowed; width: 350px" />
					</c:if>
					<c:if test="${vo.type=='2' }">
					<input 
						value="管理员通知" type="text" class="form-control input-primary"
						disabled="disabled" style="cursor: not-allowed; width: 350px" />
					</c:if>
					</li>
					<li><label>时间：</label> <input name="name" id="name"
						value="<fmt:formatDate value='${vo.create_time}' pattern='yyyy/MM/dd  HH:mm:ss' />" type="text" class="form-control input-primary"
						disabled="disabled" style="cursor: not-allowed; width: 350px" /></li>

					<li><label>&nbsp;</label><input name="" type="button"
						class="btn btn-warning" value="返回列表" onclick="goBackList();" /></li>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>