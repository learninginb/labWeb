<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文献评论添加</title>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<script type="text/javascript">
	var paperId = ${param.id};
	$(function(){
		var prename =decodeURI(decodeURI($("#name").val()));
		$("#name").val(prename);
		
	});
	
	//返回
	function goBackList(){
		window.location= "${path}/PaperController/findById.do?id="+paperId;
	}
	
	//提交评论	
	function toSubmit(){
		if(!formValidate()){
			return ;			
		}
		postAjax('${path}/PaperController/addComment.do',{paper_id:paperId,comment:$(introduction).val()},(data)=>{
			showArtDiaglog('提示','添加成功',null,function(){
				closeDialog();
			});
		});
	}
	
	function formValidate() {
		var comment = $("#introduction").val();
		comment = comment.replace(/^\s+|\s+$/g,'');
		if (comment == ""||comment==null||comment.length==0) {
			showArtDiaglog('提示', '请输入评论', null, function() {
				closeDialog();
			});
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<div>
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li><a href="#">首页</a></li>
				<li><a href="#">文献管理</a></li>
				<li><a href="#">文献评论管理</a></li>
				<li><a href="#">添加评论</a></li>
			</ul>
		</div>
		<div class="formbody">
			<div class="formtitle">
				<span>评论添加</span>
			</div>
			<form id="add_form" method="post">
				<ul class="forminfo">
					<li><label>文献名称：</label>
						<input name="name"
						id="name" value="${param.name}" type="text"
						class="form-control input-primary" disabled="disabled"
						style="cursor: not-allowed; width: 350px" />
					</li>
					<li><label>评论：</label>
						<textarea name="introduction" id="introduction" type="text"
						class="form-control input-primary" style="width: 550px; height:200px" >
						</textarea>
					</li>
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