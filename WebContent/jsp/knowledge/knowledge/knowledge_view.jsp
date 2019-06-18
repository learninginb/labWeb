<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详情页面</title>
<script type="text/javascript" charset="utf-8" src="../jslib/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../jslib/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
$(function() {
	//设置select的值
	var val = $("#knowledgeTypeHidden").val();
	$("#knowledgeType").find("option[value='"+val+"']").attr("selected",true);
	
	ue = createUeditor("ueditor",300,700);//ue编辑器
	
	//设置状态字段
	val = $("#knowledgeState").val();
	if(val==0){
		$("#knowledgeState").val("待审核");
	}else if(val==1){
		$("#knowledgeState").val("已发布");
	}else if(val==2){
		$("#knowledgeState").val("已点评");
	}else if(val==3){
		$("#knowledgeState").val("关注过");
	}else if(val==4){
		$("#knowledgeState").val("收藏");
	}else if(val==5){
		$("#knowledgeState").val("被驳回");
	}else if(val==6){
		$("#knowledgeState").val("过期");
	}else if(val==7){
		$("#knowledgeState").val("回收站");
	}
});
	
	//返回列表
	function goBackList(){
		window.location="${path}/knowledgeController/toList.do";
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
   				<input type="hidden" name="id" value="${vo.id}"/>
				<ul class="forminfo">
					<li><label>信息分类：</label>
						<select name="knowledgeType" id="knowledgeType" class="form-control input-middle" style="width:350px" disabled="disabled">
    						<c:forEach var="list" items="${knowledgeTypeList}">
    							<option value="${list.id}">${list.knowledgeType}</option>
    						</c:forEach>
						</select>
						<input type="hidden" id="knowledgeTypeHidden" name="knowledgeTypeHidden" value="${vo.knowledgeType}" />
					</li>
					<li><label>信息标题：</label><input name="knowledgeTitle" id="knowledgeTitle" value="${vo.knowledgeTitle}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>发布人：</label><input name="createUser" id="createUser" value="${vo.createUser}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>正文：</label>
    				<li>
    					<textarea id="ueditor" name="knowledgeComment">
							${vo.knowledgeComment}
						</textarea>
					</li>
					<%-- <li><label>图片地址：</label><input name="knowledgeIconUrl" id="knowledgeIconUrl" value="${vo.knowledgeIconUrl}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li> --%>
					<li><label>附件地址：</label><input name="knowledgeAttachUrl" id="knowledgeAttachUrl" value="${vo.knowledgeAttachUrl}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>状态：</label><input name="knowledgeState" id="knowledgeState" value="${vo.knowledgeState}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>创建时间：</label><input name="createTime" id="createTime" value="<fmt:formatDate value="${vo.createTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>更新时间：</label><input name="modifyTime" id="modifyTime" value="<fmt:formatDate value="${vo.modifyTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
	    			<li><input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>