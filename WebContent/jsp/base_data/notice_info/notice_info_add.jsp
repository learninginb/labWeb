<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物性数据类型-添加</title>
<script type="text/javascript">
	$(function() {

	});
	function toSubmit() {
		//表单验证
		if (!formValidate()) {
			return;
		}

		//表单提交
		$("#add_form").ajaxSubmit({
			url : "${path}/noticeController/add.do",
			data : $("#add_form").serialize(),
			cache : false,
			dataType : 'JSON',
			type : 'post',
			success : function(data) {
				if (data.success) {
					top.showArtDiaglog('提示', '添加成功', function() { //关闭事件
						goBackList();
					}, function() { //确定事件
						top.closeDialog();
					});
				} else {
					top.showArtDiaglog('提示', data.msg, null, function() {
						top.closeDialog();
					});
				}
			}
		});
	}

	function formValidate() {
		var content = $("#content").val();
		if (content == "") {
			showArtDiaglog('提示', '内容不能为空', null, function() {
				closeDialog();
			});
			return false;
		}

		return true;
	}

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
				<span>发送信息添加</span>
			</div>
			<form id="add_form" method="post">
				<ul class="forminfo">
					<input type="hidden" name="type" value="2">
					<input type="hidden" name="receive_user_ids" value="0">
					<li><label>内容:</label>
					<textarea rows="5" name="content" id="content" 
							class="form-control input-primary" style="width: 350px;resize:none" ></textarea></li>
					<li><label>&nbsp;</label><input name="" type="button"
						class="btn btn-primary" value="发送" onclick="toSubmit()" />&nbsp;&nbsp;&nbsp;&nbsp;<input
						name="" type="button" class="btn btn-warning" value="返回列表"
						onclick="goBackList();" /></li>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>