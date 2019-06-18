<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
  response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
  response.setHeader("Pragma", "no-cache");
  response.setDateHeader("Expires", -1);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实验室管理平台</title>
<link href="<%=request.getContextPath()%>/css/layout/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="<%=request.getContextPath()%>/jslib/table/docs/assets/js/jquery.min.js"></script>
<!-- <script src="<%=request.getContextPath()%>/js/layout/logincloud.js" type="text/javascript"></script> -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/jslib/table/docs/assets/bootstrap/css/bootstrap.min.css">
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico">
<script src="<%=request.getContextPath()%>/jslib/table/docs/assets/bootstrap/js/bootstrap.min.js"></script>
<script  type="text/javascript">
	$(function(){
		if (window != top) {
			top.location.href = location.href; 
		}
    	$('.loginbox').css({'position':'absolute','left':($(window).width()-850)/2});
		$(window).resize(function(){  
    		$('.loginbox').css({'position':'absolute','left':($(window).width()-850)/2});
    	});
		var message=$.trim($('#msgShow').text());
		if(message){
			$('#warnInfo').show();
		}
		
	}); 

	//刷新验证码
	function refreshCode(){
		$("#passkey_img").attr("src","<%=request.getContextPath()%>/loadPasskey.do?timestamp="+ new Date().getTime());
	} 
	
	function openWarnInfo(){
		$('#warnInfo').show();
	}
	
	function login(){
		var loginNam = $("#loginName").val();
		var password = $("#password").val();
		var passkey = $("#passKey").val();
		if(!loginNam){
			$("#msgShow").text('请输入登录名');
			openWarnInfo();
			return;
		}
		if(!password){
			$("#msgShow").text('请输入登录密码');
			openWarnInfo();
			return;
		}		
 		if(!passkey){
			$("#msgShow").text('请输入验证码');
 			openWarnInfo();
 			return;
 		}
 		
		var $btn = $("btnLogin").button('登录中...');
	    $btn.button('reset');
		$("#userLoginForm").submit();
	}
	
	function enterLogin(){
		 var event=arguments.callee.caller.arguments[0]||window.event;// 修正浏览器兼容 
	     if (event.keyCode == 13){  
	    	 login();
	     }  	   
	 }
</script> 
<style type="text/css">
span {
	display: block;
}
body{
	
	background-image: url(images/layout/jumpman.jpg); 
	background-repeat: no-repeat; 
	background-position: center top; 
	overflow: hidden;
}
</style>
</head>
<body  onkeydown="enterLogin();" onload="refreshCode()">
	<!--  <div id="mainBody">
		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div>
	</div> -->
	<div class="logintop">
		<span>欢迎登录实验室管理平台</span>
		<ul>
			<!-- <li><a href="#">回首页</a></li> -->
			
			<li><a href="#" data-toggle="tooltip" data-placement="bottom" title="帮助">帮助</a></li>
			 <li><a href="#" data-toggle="tooltip" data-placement="bottom" title="北京化工大学仿真中心成立与 1987年，于1997年2月被批准为原化工部系统仿真工程技术中心">关于</a></li>
		</ul>
	</div>
	<div class="loginbody">
		<span class="systemlogo"></span>
		<form id="userLoginForm" class="userLoginForm" action="<%=request.getContextPath()%>/login.do" method="post">
			<div class="loginbox">
			<ul>
				<li>
					<div id="warnInfo" class="alert alert-danger" role="alert" style="width: 330px;height: 46px;display: none;" ><label id="msgShow">${warn}</label></div>
				</li>
				<li>
					<div class="input-group" >
						<span class="input-group-addon">
							<i class="glyphicon glyphicon-user"></i>
						</span>
						<input type="text" id="loginName" name="loginName" class="form-control " style="width:240px;" placeHolder="请输入登录名" >
					</div>
				</li>
				<li>
					<div class="input-group" >
						<span class="input-group-addon">
							<i class="glyphicon glyphicon-lock"></i>
						</span>
						<input type="password" id="password" name="password" class="form-control " style="width:240px;" placeHolder="请输入登录密码" >
					</div>
				</li>
				<li>
					<div class="row">
					  <div class="col-xs-7"><input type="text"  value="1" id="passKey" name="passKey" class="form-control " style="width:110px; display:none" placeHolder="请输入验证码"   autocomplete="off" ></div>
					  <!-- <div class="col-xs-5"><img alt="验证码" id="passkey_img" onclick="refreshCode()" src="<%=request.getContextPath()%>/loadPasskey.do" ></div> -->
					</div>
				</li>
				<li>
					<div class="col-xs-3"><input id="btnLogin" type="button" style="width:250px;" class="btn btn-primary" value="登录"
					onclick="login();" /></div>
					<!-- <div class="col-xs-9"><input name="rememberMe" value="1" type="checkbox"  checked="checked" />记住密码</label><label><a href="#">忘记密码？</a></div> -->
				</li>
			</ul>
		</div>
		</form>
		
	</div>
	
</body>
</html>