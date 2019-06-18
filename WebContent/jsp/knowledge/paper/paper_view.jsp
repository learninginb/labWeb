<%@ page language="java" contentType="text/html; charset=UTF-8"
	 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详情页面</title>
<script type="text/javascript">

	//返回列表
	function goBackList() {
		window.location = "${path}/PaperController/toList.do";
	}
	
	
	
	//提交摘要
	function commitComment(){
		console.log("commit my Comment");
	}
	
	function addComment(){
		
	var name = encodeURI(encodeURI($("#name").val()));
	window.location.href="${path }/jsp/knowledge/paper/paper_comment_add.jsp?id="+${vo.id}+"&name="+name;
	}
	 
	
	
</script>
<style type="text/css">
#preview{width:120px;height:120px;border:1px solid #eb4f38;overflow:hidden;}
#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
.praise{float:right;}
#commitButton{float:right;}
</style>

</head>
<body>
	<div>
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li><a href="#">首页</a></li>
			<li><a href="#">系统管理</a></li>
			<li><a href="#">组态资源管理</a></li>
			<li><a href="#">组态资源</a></li>
			</ul>
		</div>
		<div class="formbody">
			<div class="formtitle">
				<span>组态资源详情</span>
			</div>
			<form id="add_form" method="post">
				<input type="hidden" name="id" value="${vo.id}" />
				<ul class="forminfo">
				    <li><label>图片：</label>
					     <div id="preview">
					        <c:if test="${vo.image_path==''}">
					         <img id="imghead" width=100% height=auto border=0 src='${path }/images/no_image.png'>
					        </c:if>
					        <c:if test="${vo.image_path!='' }">
					         <img id="imghead" width=100% height=auto border=0 src='${path }/${resource_path}${vo.image_path}'>
					        </c:if>
						</div>
					</li>
					<li><label>名称：</label>
					<input name="name"
						id="name" value="${vo.name}" type="text"
						class="form-control input-primary" disabled="disabled"
						style="cursor: not-allowed; width: 350px" /></li>
					<li><label>类型：</label>
					<input name="type_name"
						id="type_name" value="${vo.key_word}" type="text"
						class="form-control input-primary" disabled="disabled"
						style="cursor: not-allowed; width: 350px" /></li>	
					<li><label>状态</label>
						<input type="radio" disabled="disabled" style="cursor: not-allowed;"   value="1" name="status" id="status" <c:if test="${vo.status==1 }">checked="checked"</c:if> >
						启用    
						<input type="radio" disabled="disabled" style="cursor: not-allowed;"  value="0" name="status" id="status"  <c:if test="${vo.status==0}">checked="checked"</c:if> >
						禁用	
					</li>
					<li style="width:450px;">
						<a href="${path }/jsp/knowledge/paper/paper_comment_list.jsp?id=${vo.id}" style="display: block;text-align: center;font-size:16px;" >》》查看文献评论《《</a>
					</li>
					<li><label>&nbsp;</label><input name="" type="button" class="btn btn-warning"
						value="返回列表" onclick="goBackList();" />&nbsp;&nbsp;&nbsp;&nbsp;<input id="commentAdd" name="" type="button" class="btn btn-warning"
						value="添加评论" onclick="addComment()" /></li>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>