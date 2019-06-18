<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息</title>
<script>
	//修改密码
	function toEditPassword() {
		window.location = '${path}/personalInfoController/toEditPassword.do';
	}

	//修改信息
	function toEdit() {
		window.location = '${path}/personalInfoController/toEdit.do';
	}
	//随便写的一个测试类
	function toTest() {
		window.location = '${path}/personalInfoController/toTest.do';
	}
</script>
</head>
<body>
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
		<div class="formtitle">
			<span>个人信息</span>
			<div id="toolbar" class="btn-group">
				
			</div>
		</div>
		
			<input type="hidden" name="id" value="${vo.id}" />
			 <ul class="forminfo">
					<li><label>名称：</label>
					<input name="userName"
						id="userName" value="${vo.userName}" type="text"
						class="form-control input-primary" disabled="disabled"
						style="cursor: not-allowed; width: 350px" /></li>
					
					<li><label>账号：</label>
					<input name="account"
						id="account" value="${vo.account}" type="text"
						class="form-control input-primary" disabled="disabled"
						style="cursor: not-allowed; width: 350px" /></li>
					<li><label>账号类型：</label>
					<input name="roleName"
						id="roleName" value="${roleNames}" type="text"
						class="form-control input-primary" disabled="disabled"
						style="cursor: not-allowed; width: 350px" /></li>
					<li><label>邮箱：</label>
					<input name="email"
						id="email" value="${vo.email}" type="text"
						class="form-control input-primary" disabled="disabled"
						style="cursor: not-allowed; width: 350px" /></li>	
					<li><label>电话号码：</label>
					<input name="mobile_phone"
						id="mobile_phone" value="${vo.mobilePhone}" type="text"
						class="form-control input-primary" disabled="disabled"
						style="cursor: not-allowed; width: 350px" /></li>
					<li><label>&nbsp;</label>
					<shiro:hasPermission name="PersonalInfo:edit">
					<button class="btn btn-warning btn-round" onclick="toEdit();">
						 信息修改
					</button>
				</shiro:hasPermission>
				<!-- //TODO -->
				<shiro:hasPermission name="PersonalInfo:info">
					<button class="btn btn-success btn-round"
						onclick="toEditPassword();">
						密码修改
					</button>
				</shiro:hasPermission>
				<!--<shiro:hasPermission name="PersonalInfo:test">
					<button class="btn btn-success btn-round" onclick="toTest();">
						 测试按钮
					</button>
				</shiro:hasPermission>--></li>


			</ul>
	</div>



	</div>
</body>
</html>