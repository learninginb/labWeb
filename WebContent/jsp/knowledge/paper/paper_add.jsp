<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物性数据类型-添加</title>
<script type="text/javascript">

	
	function toSubmit() {
		//表单验证
		if (!formValidate()) {
			return;
		}
		
		$("#key_word").val($("#key_wordSelector").val().toString());
		//console.log($("#key_word").val());
		//表单提交
		debugger;
		$("#add_form").ajaxSubmit({
			url : "${path}/PaperController/add.do",
			data : $("#add_form").serialize(),
			cache : false,
			dataType : 'JSON',
			type : 'post',
			success : function(data) {
				var result;
				if(data.success==undefined){
					result = jQuery.parseJSON(data);
				}else{
					result=data;
				}
				if (result.success) {
					top.showArtDiaglog('提示', '添加成功', function() { //关闭事件
						goBackList();
					}, function() { //确定事件
						top.closeDialog();
					});
				} else {
					top.showArtDiaglog('提示', result.msg, null, function() {
						top.closeDialog();
					});
				}
			}
		});
	}

	function formValidate() {
		var name = $("#name").val();
		var type=$("#type").val();
		var file_path=$("#file_path").val();
		if (name == "") {
			showArtDiaglog('提示', '请输入名称', null, function() {
				closeDialog();
			});
			return false;
		}
		if (type == "") {
			showArtDiaglog('提示', '请选择类型', null, function() {
				closeDialog();
			});
			return false;
		}
		if (file_path == "") {
			showArtDiaglog('提示', '请添加文件', null, function() {
				closeDialog();
			});
			return false;
		}
		
		console.log(file_path);
		return true;
	}
	//返回列表
	function goBackList() {
		window.location = "${path}/PaperController/toList.do";
	}
</script>

</head>
<body>
	<div>
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li><a href="#">首页</a></li>
				<li><a href="#">系统管理</a></li>
				<li><a href="#">组态资源管理</a></li>
				<li><a href="#">组态资源</a></li>
			</ul>
		</div>
		<div class="formbody">
			<div class="formtitle">
				<span>文献上传</span>
			</div>
			<form id="add_form" method="post" enctype ="multipart/form-data">
				<ul class="forminfo">
				 
					<li><label>文件：</label>
					    <input name="file_path" id="file_path" type="file" class="form-control input-primary" style="width:350px"/></li>
					<li><label>名称：</label><input name="name" id="name" type="text"
						class="form-control input-primary" style="width: 350px" /></li>
					<li><label>重要程度：</label> <select class="form-control"
						style="width: 350px;" id="important" name="important">
							
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							
					</select></li>
					<li><label>关键词</label><select id="key_wordSelector" name="key_wordSelector" class="selectpicker show-tick" noneSelectedText="关键词"  multiple>
						<c:forEach var="list" items="${keyWordList }">
							<option value="${list.name }">${list.name }</option>
						</c:forEach>
					</select>
					</li>
					<input type="hidden" value="" id="key_word" name="key_word">
					
					<li><label>摘要：</label> 
					<textarea name="introduction" id="introduction" type="text"
						class="form-control input-primary" style="width: 550px; height:200px" >
						</textarea>
					</li>
					<li><label>状态：</label> <input type="radio" checked="checked"
						value="1" name="status" id="status"> 启用 <input
						type="radio" value="0" id="status" name="status"> 禁用</li>
					<li><label>&nbsp;</label><input name="" type="button"
						class="btn btn-primary" value="确认保存" onclick="toSubmit()" />&nbsp;&nbsp;&nbsp;&nbsp;<input
						name="" type="button" class="btn btn-warning" value="返回列表"
						onclick="goBackList();" /></li>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>