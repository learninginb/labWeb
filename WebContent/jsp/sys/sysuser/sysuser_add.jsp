<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增页面</title>
<script type="text/javascript">
	$(function() {

	});
	function toSubmit(){
		//表单验证
	
		//表单提交
		$("#add_form").ajaxSubmit({
			url:"${path}/sysUserController/add.do",
			data : $("#add_form").serialize(),
			cache : false,
			dataType : 'JSON',
			type:'post',
			success:function(data){
				if(data.success){
					top.showArtDiaglog('提示','添加成功',function(){  //关闭事件
						goBackList();
					},function(){   //确定事件
						top.closeDialog();
					});
				}else{
					top.showArtDiaglog('提示','添加失败',null,function(){
						top.closeDialog();
					});
				}
			}
		});
	}
	
	//返回列表
	function goBackList(){
		window.location="${path}/sysUserController/toList.do";
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
				<ul class="forminfo">
					<li><label>姓名：</label><input name="userName" id="userName" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>所属实验室：</label>
						<select name="location" id="location" class="form-control input-primary" style="width:350px">
							<option value="超级管理员">超级管理员</option>
							<option value="608实验室" selected="selected">608实验室</option>
							<option value="111实验室" >111实验室</option>
							<option value="233实验室">233实验室</option>
						</select>
					</li>
					<li><label>账号：</label><input name="account" id="account" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>电子邮箱：</label><input name="email" id="email" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>手机号码：</label><input name="mobilePhone" id="mobilePhone" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>密码：</label><input name="password" id="password" type="password" class="form-control input-primary" style="width:350px" /></li>
					<li><label>身份证号：</label><input name="idNumber" id="idNumber" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>用户类型：</label>
						<select name="userType" id="userType" class="form-control input-primary" style="width:350px">
							<option value="1">超级管理员</option>
							<option value="2">管理员</option>
							<option value="3" selected="selected">发布方</option>
							<option value="4">普通会员</option>
						</select>
					</li>
					<li><label>状态</label>
						<input type="radio" id="status" name="status" value="1" checked="checked">启用
						<input type="radio" id="status" name="status" value="2">锁定
					</li>
	    			<li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" value="确认保存" onclick="toSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>