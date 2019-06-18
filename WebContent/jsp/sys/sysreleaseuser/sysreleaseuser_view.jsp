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
		window.location="${path}/sysReleaseUserController/toList.do";
	}
	
	
	function toMeituUpload(){
		var path=$.trim($('#imgFilePath').val());
		var dialog = art.dialog.open("${path}/sysReleaseUserController/openMeituUpload.do?path="+path,{
	  		  id:"selectUploadImages",
	  		  title:"图像编辑",
	  		  width :'800px',
	  		  height:'450px',
	  		  lock:true,
	  		  init: function (){
		  		$(this.iframe).attr("scrolling","no");//去掉滚动条
		  	  },
	  		 	close:function(){
	  		  }
	  	});
	}
	

	
	function closeDialog(){
    	art.dialog.list["selectUploadImages"].close();
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
	    			<li><label>发布方类型：</label>
						<select name="orgType" id=orgType class="form-control input-primary" style="width:350px"  disabled="disabled" style="cursor: not-allowed;">
							<option value="1" <c:if test="${vo.orgType==1 }">selected="selected"</c:if> >社会组织</option>
							<option value="2" <c:if test="${vo.orgType==2 }">selected="selected"</c:if> >志愿服务组织</option>
							<option value="3" <c:if test="${vo.orgType==3 }">selected="selected"</c:if> >团组织</option>
							<option value="4" <c:if test="${vo.orgType==4 }">selected="selected"</c:if> >其他组织</option>
						</select>
					</li>
					<li><label>发布方名称：</label><input name="sysUser.userName" id="userName" type="text" class="form-control input-primary" style="width:350px" value="${vo.sysUser.userName }"  disabled="disabled" style="cursor: not-allowed;" /> </li>
					<li><label>登录账号：</font></label><input name="sysUser.account" id="account" type="text" class="form-control input-primary" style="width:350px" disabled="disabled" style="cursor: not-allowed;"  value="${vo.sysUser.account }" /></li>
					<li><label>电子邮箱：</label><input name="sysUser.email" id="email" value="${vo.sysUser.email }" type="text" class="form-control input-primary" style="width:350px" disabled="disabled" style="cursor: not-allowed;"/></li>
					<li><label>发布方QQ号码：</label><input name="qq" value="${vo.qq }" id="qq" type="text" class="form-control input-primary" style="width:350px"  disabled="disabled" style="cursor: not-allowed;"/></li>
					<li><label>图像地址：</label><input name="imgFilePath" value="${vo.imgFilePath }" id="imgFilePath" type="text" class="form-control input-primary" style="width:350px" disabled="disabled" style="cursor: not-allowed;"/> <input name="" type="button" class="btn btn-primary" value="查看头像" onclick="toMeituUpload()"/> </li>
					<li><label>联系人：</label><input name="contactsUser" id="contactsUser" value="${vo.contactsUser }" type="text" class="form-control input-primary" style="width:350px"  disabled="disabled" style="cursor: not-allowed;"/></li>
					<li><label>联系手机号码：</label><input name="contactsPhone" value="${vo.contactsPhone }" id="contactsPhone" type="text" class="form-control input-primary" style="width:350px" disabled="disabled" style="cursor: not-allowed;" /></li>
					<li><label>所在地址：</label><input name="address" id="address" type="text" class="form-control input-primary" style="width:350px" value="${vo.address}" disabled="disabled" style="cursor: not-allowed;" /></li>
					<li><label>常用举办地址：</label><input name="commonAddress" id="commonAddress" type="text" class="form-control input-primary" style="width:350px" value="${vo.commonAddress }"  disabled="disabled" style="cursor: not-allowed;" /></li>
					<li><label>主办方简介：</label>
						<textarea class="form-control" style="width:350px;" name="introduce" id="introduce" disabled="disabled" style="cursor: not-allowed;">${vo. introduce}</textarea>
					</li>
					
					<li><label>添加时间：</label><input name="regTime" id="regTime" value="<fmt:formatDate value="${vo.sysUser.regTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>添加ip：</label><input name="regIp" id="regIp" value="${vo.sysUser.regIp}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>最后登录时间：</label><input name="lastLoginTime" id="lastLoginTime" value="<fmt:formatDate value="${vo.sysUser.lastLoginTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>最后错误登录时间：</label><input name="lastLoginErrTime" id="lastLoginErrTime" value="<fmt:formatDate value="${vo.sysUser.lastLoginErrTime}" />" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px" /></li>
					<li><label>最后登录ip：</label><input name="lastLoginIp" id="lastLoginIp" value="${vo.sysUser.lastLoginIp}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>身份证号：</label><input name="idNumber" id="idNumber" value="${vo.sysUser.idNumber}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
					<li><label>登录累计错误次数：</label><input name="loginErrTimes" id="loginErrTimes" value="${vo.sysUser.loginErrTimes}" type="text" class="form-control input-primary" disabled="disabled" style="cursor: not-allowed;width:350px"  /></li>
	    			<li><label>状态</label>
						<input type="radio" id="status" name="sysUser.status" value="1" <c:if test="${vo.sysUser.status==1 }">checked="checked"</c:if> disabled="disabled" style="cursor: not-allowed;">启用
						<input type="radio" id="status" name="sysUser.status" value="2" <c:if test="${vo.sysUser.status==2 }">checked="checked"</c:if> disabled="disabled" style="cursor: not-allowed;">锁定
					</li>
					<li><input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>