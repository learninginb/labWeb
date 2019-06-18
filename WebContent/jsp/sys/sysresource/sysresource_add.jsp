<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增页面</title>
<script type="text/javascript">
	$(function() {

	});
	function toSubmit(){
		//表单验证
		var message="";
		var resourceName=$.trim($("#resourceName").val());
		var permissionStr=$.trim($('#permissionStr').val());
		if(!resourceName){
			message="-资源名称不能为空";
		}
		if(!permissionStr){
			message+="<br/>-权限字符串不能为空";
		}
		if(message){
			top.showArtDiaglog('提示',message,null,function(){
				top.closeDialog();
			});
			return ;
		}
		//表单提交
		$("#add_form").ajaxSubmit({
			url:"${path}/sysResourceController/add.do",
			data : $("#add_form").serialize(),
			cache : false,
			dataType : 'JSON',
			type:'post',
			success:function(data){
				if(data.success){
					top.showArtDiaglog('提示','添加成功',function(){  //关闭事件
						goBackList();
					},function(){   //确定事件
						top.closeDialog();
					});
				}else{
					top.showArtDiaglog('提示','添加失败',null,function(){
						top.closeDialog();
					});
				}
			}
		});
	}
	
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
				<ul class="forminfo">
					<li><label>上级资源名称：</label>
						<input class="form-control" style="width:350px;" value="${vo.resourceName }" disabled="disabled" style="cursor: not-allowed;" />
						<input name="parentId" id="parentId" type="hidden" value="${vo.id }" />
					</li>
					<li><label>资源名称：<font color="red" size="3">*</font></label><input name="resourceName" id="resourceName" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>资源路径：<c:if test="${vo.level==3}"><font color="red" size="3">*</font></c:if></label><input name="resourcePath" <c:if test="${vo.level==3}">required="true"</c:if> id="resourcePath" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>资源图标：</label><input name="resourceIcon" id="resourceIcon" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>层级：</label>
						<input name="level" id="level" type="text" class="form-control input-primary" style="width:350px;cursor: not-allow" disabled="disabled"  value="${vo.level }" />
						<input name="level"  type="hidden" value="${vo.level }"  />
					</li>
					<li><label>权限字符串：<font color="red" size="3">*</font></label><input name="permissionStr" id="permissionStr" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>排序：</label><input name="orderNum" id="orderNum" value="${vo.orderNum}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="10" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>是否启用：</label>
						<input type="radio" id="isEnable" name="isEnable" value="1" checked="checked">启用
						<input type="radio" id="isEnable" name="isEnable" value="2">禁用
					</li>
					
					<c:if test="${vo.level == 3}">
					<li><label>是否生成菜单：</label>
						 <input checked="checked" type="checkbox" name="createButton" value="true" />
						          同时生成增、删、改、查按钮资源权限配置
					</li>
				    </c:if>
					<li><label>资源描述：</label>
						<textarea class="form-control" style="width:350px;" name="resourceDesc" id="resourceDesc"></textarea>
					</li>
	    			<li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" value="确认保存" onclick="toSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>