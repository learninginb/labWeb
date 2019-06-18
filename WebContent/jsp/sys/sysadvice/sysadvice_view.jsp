<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	function goBackList(){
		window.location="${path}/sysAdviceController/toList.do";
	}
</script>
</head>
<body>
	<div>
		<div class="place">
		    <span>位置：</span>
		    <ul class="placeul">
			    <li><a href="#">首页</a></li>
			    <li><a href="#">表单</a></li>
		    </ul>
	    </div>
		<div class="formbody">
   			<div class="formtitle"><span>基本信息</span></div>
   			<form id="add_form" method="post">
   				<input type="hidden" name="id" value="${vo.id}"/>
				<ul class="forminfo">
					<li><label>创建人id：</label><input name="createUserId" id="createUserId" value="${vo.createUserId}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>创建人姓名：</label><input name="createUserName" id="createUserName" value="${vo.createUserName}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>创建时间：</label><input name="createTime" id="createTime" value="<fmt:formatDate value="${vo.createTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>建议：</label><input name="advice" id="advice" value="${vo.advice}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>回复人id：</label><input name="replyUserId" id="replyUserId" value="${vo.replyUserId}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>回复人姓名：</label><input name="replyUserName" id="replyUserName" value="${vo.replyUserName}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>回复时间：</label><input name="replyTime" id="replyTime" value="<fmt:formatDate value="${vo.replyTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>状态(1默认提交 2回复 3其他操作)：</label><input name="status" id="status" value="${vo.status}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>回复内容：</label><input name="replyContent" id="replyContent" value="${vo.replyContent}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
	    			<li><input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>