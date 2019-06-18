<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详情页面</title>
<script type="text/javascript">
	$(function() {
		//设置select的值
		var val = $("#knowledgeTypeStateHidden").val();
		$("#knowledgeTypeState").find("option[value='"+val+"']").attr("selected",true);
	});
	
	//返回列表
	function goBackList(){
		window.location="${path}/knowledgeTypeController/toList.do";
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
					<c:if test="${not empty vo.parentName}"><li><label>上级分类：</label><input name="parentName" id="parentName" value="${vo.parentName}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li></c:if>
					<li><label>信息分类：</label><input name="knowledgeType" id="knowledgeType" value="${vo.knowledgeType}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>信息分类描述：</label><input name="knowledgeTypeDesc" id="knowledgeTypeDesc" value="${vo.knowledgeTypeDesc}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>信息分类状态：</label>
						<select name="knowledgeTypeState" id="knowledgeTypeState" class="form-control input-middle" style="width:350px" disabled="disabled">
    						<option value="1">有效</option>
    						<option value="0">无效</option>
						</select>
						<input type="hidden" id="knowledgeTypeStateHidden" name="knowledgeTypeState" value="${vo.knowledgeTypeState}" />
					</li>
					<li><label>创建时间：</label><input name="createTime" id="createTime" value="<fmt:formatDate value="${vo.createTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>更新时间：</label><input name="modifyTime" id="modifyTime" value="<fmt:formatDate value="${vo.modifyTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
	    			<li><input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>