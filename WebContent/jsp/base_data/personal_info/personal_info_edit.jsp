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
			url:"${path}/personalInfoController/editSubmit.do",
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
					top.showArtDiaglog('提示','编辑失败',null,function(){
						top.closeDialog();
					});
				}
			}
		});
	}
	
	function formValidate(){
		var name = $("#userName").val();
		if (name == "") {
			showArtDiaglog('提示', '名称不能为空', null, function() {
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
			<li><a href="#">个人信息</a></li>
		    </ul>
	    </div>
		<div class="formbody">
   			<div class="formtitle"><span>个人信息修改</span></div>
   			<form id="submit_form" method="post">
   				<input type="hidden" name="id" value="${vo.id}"/>
				<ul class="forminfo">
					<li><label>名称：</label>
					<input name="userName"
						id="userName" value="${vo.userName}" type="text"
						class="form-control input-primary" 
						style=" width: 350px" /></li>
					
					<li><label>账号：</label>
					<input name="accounts"
						id="accounts" value="${vo.account}" type="text"
						class="form-control input-primary" disabled="disabled"
						style=" width: 350px" /></li>
					<li><label>账号类型：</label>
					<input name="roleName"
						id="roleName" value="${roleNames}" type="text"
						class="form-control input-primary" disabled="disabled"
						style=" width: 350px" /></li>
					<li><label>邮箱：</label>
					<input name="email"
						id="email" value="${vo.email}" type="text"
						class="form-control input-primary" 
						style="width: 350px" /></li>	
					<li><label>电话号码：</label>
					<input name="mobilePhone"
						id="mobilePhone" value="${vo.mobilePhone}" type="text"
						class="form-control input-primary" 
						style=" width: 350px" /></li>
					<li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" value="确认保存" onclick="toSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>