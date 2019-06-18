<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="<%=request.getContextPath()%>/jslib/table/docs/assets/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/css/layout/style.css" rel="stylesheet" type="text/css" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上边部分</title>

<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected");
		$(this).addClass("selected");
	});	
})	
</script>
</head>
<body >

    <div class="topleft">
    <a href="main.html" target="_parent"><img src="images/layout/logo.png" title="系统首页" /></a>
    </div>
        
    <ul class="nav">
    <li><a href="default.html" target="rightFrame" class="selected"><img src="images/layout/icon01.png" title="工作台" /><h2>工作台</h2></a></li>
    <li><a href="imgtable.html" target="rightFrame"><img src="images/layout/icon02.png" title="模型管理" /><h2>模型管理</h2></a></li>
    <li><a href="imglist.html"  target="rightFrame"><img src="images/layout/icon03.png" title="模块设计" /><h2>模块设计</h2></a></li>
    <li><a href="tools.html"  target="rightFrame"><img src="images/layout/icon04.png" title="常用工具" /><h2>常用工具</h2></a></li>
    <li><a href="computer.html" target="rightFrame"><img src="images/layout/icon05.png" title="文件管理" /><h2>文件管理</h2></a></li>
    <li><a href="tab.html"  target="rightFrame"><img src="images/layout/icon06.png" title="系统设置" /><h2>系统设置</h2></a></li>
    </ul>
            
    <div class="topright">    
    <ul>
    <li><span><img src="images/layout/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    <li><a href="login.html" target="_parent">退出</a></li>
    </ul>
     
    <div class="user">
    <span>admin</span>
    <i>消息</i>
    <b>5</b>
    </div>    
    
    </div>

</body>
</html>