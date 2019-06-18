<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息测试</title>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li><a href="#">基础信息管理</a></li>
			<li><a href="#">个人信息管理</a></li>
			<li><a href="#">用来测试的页面</a></li>
		</ul>
	</div>
	<div class="formbody">
		<div class="formtitle">
			<span ></span>
			<div id="toolbar" class="btn-group">
				<input name="userName"
						id="userName" value="${vo.userName}" type="text"
						class="form-control input-primary" disabled="disabled"
						style="cursor: not-allowed; width: 350px" />
			</div>
		</div>
	</div>
</body>
</html>