<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请审核</title>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<style type="text/css">
	.forminfo li input[type="text"],.forminfo li input[type="file"]{
		width:350px;
	}
	.forminfo li #description{
		width: 550px; height:200px;
	}
	
</style>
<script type="text/javascript">

$(function(){
	getAjax("${path}/TaskController/task.do",{id:${param.taskId}},(data)=>{
		$('#theme').val(data['task'].theme);
		$('#reviewer').val(data['task'].distributor);
		$('#executor').val(data['task'].executor);
	})
})
//参数校验
function formValidate(){
	var description = $('#description').val();
	description = description.replace(/^\s+|\s+$/g,'');
	var filePath = $('#filePath').val();
	if(description==""||description==null||description.length==0){
		showArtDiaglog('提示', '请输入申请简述', null, function() {
			closeDialog();
		});
		return false;
	}else if(filePath==null||filePath==""){
		showArtDiaglog('提示', '请输上传审核材料', null, function() {
			closeDialog();
		});
		return false;
	}
	return true;
	
}

//提交
function toSubmit(){
	if(formValidate()){
		$('#add_form').ajaxSubmit({
			url:"${path}/TaskController/reviews.do?taskId="+${param.taskId},
			type:"post",
			dataType:"json",
			data:$('#add_form').serialize(),
			cache:false,
			success:function(data){
				console.log(data);
				if(data.code==200){
					showArtDiaglog('提示', '提交成功', null, function() {
						closeDialog();
						goBackList();
					});
					
				}
				else{
					showArtDiaglog('提示', '提交失败', null, function() {
						closeDialog();
						goBackList();
					});
					
				}
			}
		});
	}
}

//返回列表
function goBackList(){
	window.location="${path}/TaskController/toUnfinishTask.do";
}
</script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">任务管理</a></li>
    <li><a href="#">申请审核</a></li>
    </ul>
    </div>
	<div class="formbody">
		<div class="formtitle">
			<span>申请审核</span>
		</div>
		<form id="add_form" method="post" enctype ="multipart/form-data">
			<ul class="forminfo">
				<li ><label>任务主题：</label>
					<input  id="theme" name="theme" type="text" class="form-control input-primary" value="任务主题" readonly />
				</li>
				<li><label>审核人：</label>
					<input  id="reviewer" name="reviewer" type="text" class="form-control input-primary" value="审核人" readonly/>
				</li>
				<li><label>申请人：</label>
					<input  id="executor" name="executor" type="text" class="form-control input-primary" value="申请人" readonly/>
				</li>
				<li><label>申请简述：</label>
					<textarea id="description" name="description" class="form-control input-primary">
					</textarea>
				</li>
				<li><label>申请材料上传：</label>
					 <input name="filePath" id="filePath" type="file" class="form-control input-primary" />
				</li>
				<li><label>&nbsp;</label>
					<input class="btn btn-primary" type="button" value="提交" onclick="toSubmit()"/>&emsp;
					<input type="button" class="btn btn-warning" value="返回列表" onclick="goBackList()" />
				</li>
			</ul>
		</form>
	</div>
</body>
</html>