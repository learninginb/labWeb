<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻-详情</title>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<script type="text/javascript">
const HTMLDecode = text =>{
	let tmp = document.createElement('div');
	tmp.innerHTML = text;
	const output = tmp.innerText || tmp.textContent;
	tmp = null;
	return output;
}
$(function(){
	var id = ${param.id};
	getAjax("${path}/NewsController/news/"+id+".do",{},(data)=>{
		console.log(data);
		$("#newsInfo").html(data['newsVo'].content);
		praseHtml(data['newsVo'].content);
	});
})
function goBackList(){
	console.log($("#newsInfo").text());
	window.location = "${path}/NewsController/toMyNews.do";
	
}
function praseHtml(description){
	description = description.replace(/(\n)/g, "");  
	description = description.replace(/(\t)/g, "");  
	description = description.replace(/(\r)/g, "");  
	description = description.replace(/<\/?[^>]*>/g, "");  
	description = description.replace(/\s*/g, "");
	description = description.replace(/&nbsp;/ig,"");
	console.log(description);
}	


</script>
</head>
<body>
	<div>
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li><a href="#">首页</a></li>
				<li><a href="#">信息管理</a></li>
				<li><a href="#">新闻管理</a></li>
				<li><a href="#">新闻详情</a></li>
			</ul>
		</div>
		<div id="newsInfo">
		</div>
	</div>
	<div style="text-align:center;"><input name="" type="button" class="btn btn-warning" value="返回列表"	onclick="goBackList();" /></div>
</body>
</html>