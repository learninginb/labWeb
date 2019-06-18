<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详情页面</title>
<script type="text/javascript">
	$(function() {

	});
	
	//返回列表
	function goBackList(){
		window.location="${path}/sysResourceController/toList.do";
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
	    			<li><label>上级资源名称：</label>
						<input class="form-control" style="width:350px;" value="${vo.parentName }" disabled="disabled" style="cursor: not-allowed;" />
					</li>
					<li><label>资源名称：</label><input name="resourceName" id="resourceName" type="text" class="form-control input-primary" style="width:350px" value="${vo.resourceName }" disabled="disabled" style="cursor: not-allowed;"/></li>
					<li><label>资源路径：</label>
						<input name="resourcePath" <c:if test="${vo.level==3}">required="true"</c:if> id="resourcePath" type="text" class="form-control input-primary" style="width:350px" value="${vo.resourcePath }" disabled="disabled" style="cursor: not-allowed;" />
					</li>
					<li><label>资源图标：</label><input name="resourceIcon" id="resourceIcon" type="text" class="form-control input-primary" style="width:350px" value="${vo.resourceIcon}" disabled="disabled" style="cursor: not-allowed;" /></li>
					<li><label>层级：</label>
						<input name="level" id="level" type="text" class="form-control input-primary" style="width:350px;cursor: not-allow" disabled="disabled"  value="${vo.level }" disabled="disabled" style="cursor: not-allowed;" />
					</li>
					<li><label>权限字符串：</label><input name="permissionStr" id="permissionStr" type="text" class="form-control input-primary" style="width:350px" value="${vo.permissionStr}" disabled="disabled" style="cursor: not-allowed;" /></li>
					<li><label>排序：</label><input name="orderNum" id="orderNum" value="${vo.orderNum}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="10" type="text" class="form-control input-primary" style="width:350px" disabled="disabled" style="cursor: not-allowed;" /></li>
					<li><label>是否启用：</label>
						<input type="radio" id="isEnable" <c:if test="${vo.isEnable==1 }">checked="checked"</c:if> name="isEnable" value="1" disabled="disabled" style="cursor: not-allowed;">启用
						<input type="radio" id="isEnable" <c:if test="${vo.isEnable==2 }">checked="checked"</c:if> name="isEnable" value="2" disabled="disabled" style="cursor: not-allowed;">禁用
					</li>
					<li><label>创建时间：</label><input name="createTime" id="createTime" value="<fmt:formatDate value="${vo.createTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>修改时间：</label><input name="modifyTime" id="modifyTime" value="<fmt:formatDate value="${vo.modifyTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>资源描述：</label>
						<textarea class="form-control" style="width:350px;" name="resourceDesc" id="resourceDesc" disabled="disabled" style="cursor: not-allowed;" >${vo.resourceDesc}</textarea>
					</li>	    	
	    			<li><input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>