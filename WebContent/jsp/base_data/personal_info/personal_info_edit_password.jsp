<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑页面</title>
<script type="text/javascript">
	$(function() {
		
	});
	function toSubmit(){
		//表单验证
		if(!formValidate()){
			return;
		}
		//表单提交
		$("#submit_form").ajaxSubmit({
			url:"${path}/personalInfoController/editPasswordSubmit.do",
			data : $("#submit_form").serialize(),
			cache : false,
			dataType : 'JSON',
			type:'post',
			success:function(data){
				if(data.success){
			       top.showArtDiaglog('提示','编辑成功',function(){  //关闭事件
						goBackList();
					},function(){   //确定事件
						top.closeDialog();
					});
				}else{
					top.showArtDiaglog('提示',msg,null,function(){
						top.closeDialog();
					});
				}
			}
		});
	}
	
	function formValidate(){
		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		var surePassword = $("#surePassword").val();
		if (oldPassword == "") {
			showArtDiaglog('提示', '原密码不能为空', null, function() {
				closeDialog();
			});
			return false;
		}
		if (newPassword == "") {
			showArtDiaglog('提示', '新密码不能为空', null, function() {
				closeDialog();
			});
			return false;
		}
		if (surePassword == "") {
			showArtDiaglog('提示', '确认密码不能为空', null, function() {
				closeDialog();
			});
			return false;
		}
		if (surePassword!=newPassword) {
			showArtDiaglog('提示', '新密码与确认密码不一致', null, function() {
				closeDialog();
			});
			return false;
		}
		if (surePassword.length<6||surePassword.length>12) {
			showArtDiaglog('提示', '密码长度6到12个字符', null, function() {
				closeDialog();
			});
			return false;
		}
		return true;
	}
	
	//返回列表
	function goBackList(){
		window.location="${path}/personalInfoController/toList.do";
	}
	
</script>
</head>
<body>
	<div>
		<div class="place">
		    <span>位置：</span>
		    <ul class="placeul">
			 <li><a href="#">首页</a></li>
			<li><a href="#">基础信息管理</a></li>
			<li><a href="#">个人信息管理</a></li>
			<li><a href="#">密码修改</a></li>
		    </ul>
	    </div>
		<div class="formbody">
   			<div class="formtitle"><span>密码修改</span></div>
   			<form id="submit_form" method="post">
   				<input type="hidden" name="id" value="${vo.id}"/>
				<ul class="forminfo">
					<li><label>原密码：</label>
					<input name="oldPassword"
						id="oldPassword" value="" type="password"
						class="form-control input-primary" 
						style=" width: 350px" /></li>
					
					<li><label>新密码：</label>
					<input name="newPassword"
						id="newPassword" value="" type="password"
						class="form-control input-primary" 
						style=" width: 350px" /></li>
					<li><label>确认新密码：</label>
					<input name="surePassword"
						id="surePassword" value="" type="password"
						class="form-control input-primary" 
						style=" width: 350px" /></li>
					<li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" value="确认保存" onclick="toSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>