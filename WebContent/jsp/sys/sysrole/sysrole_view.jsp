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
		window.location="${path}/sysRoleController/toList.do";
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
				<ul class="forminfo">
					<li><label>角色名称：</label><input name="roleName" id="roleName" value="${vo.roleName}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>角色类型</label>
						<select name="roleType" id="roleType" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" >
							<option value="1" <c:if test="${vo.roleType==1 }">selected="selected"</c:if>>超级管理员</option>
							<option value="2" <c:if test="${vo.roleType==2 }">selected="selected"</c:if>>管理员</option>
							<option value="3" <c:if test="${vo.roleType==3 }">selected="selected"</c:if>>发布方</option>
							<option value="4" <c:if test="${vo.roleType==4 }">selected="selected"</c:if>>普通会员</option>
						</select>
					</li>
					<li><label>状态</label>
						<input type="radio" disabled="disabled" style="cursor: not-allowed;"   value="1" name="roleStatus" id="roleStatus" <c:if test="${vo.roleStatus==1 }">checked="checked"</c:if> >
						启用    
						<input type="radio" disabled="disabled" style="cursor: not-allowed;"  value="2" name="roleStatus" id="roleStatus" name="roleStatus" <c:if test="${vo.roleStatus==2 }">checked="checked"</c:if> >
						禁用	
					</li>
					<li><label>创建时间：</label><input name="createTime" id="createTime" value="<fmt:formatDate value="${vo.createTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>修改时间：</label><input name="modifyTime" id="modifyTime" value="<fmt:formatDate value="${vo.modifyTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>角色描述：</label>
						<textarea  class="form-control" name="roleDesc" id="roleDesc"  disabled="disabled" style="cursor: not-allowed;width:350px">${vo.roleDesc }</textarea>
					</li>
	    			<li><input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>