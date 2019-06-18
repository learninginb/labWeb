<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${path }/js/foxtail/RegUtil.js"></script>
<title>新增页面</title>
<script type="text/javascript">
	$(function() {

	});
	function toSubmit(){
		//表单验证
		var userName=$.trim($("#userName").val());
		var account=$.trim($("#account").val());
		var email=$.trim($("#email").val());
		if(!userName){
			top.showArtDiaglog('提示','发布方名称不能为空',null,function(){
				top.closeDialog();
			});
			return 
		}
		if(!account){
			top.showArtDiaglog('提示','发布方账户不能为空',null,function(){
				top.closeDialog();
			});
			return ;
		}
		var flag=checkIsExist(account,"1");
		if(flag){
			top.showArtDiaglog('提示','账户已经存在，请重新输入一个',null,function(){
				top.closeDialog();
			});
			return ;
		}
		if(email){
			flag=regEmail(email);
			if(!flag){
				top.showArtDiaglog('提示','电子邮箱不合法，请重新输入一个',null,function(){
					top.closeDialog();
				});
				return ;
			}else if(checkIsExist(email,"2")){
				top.showArtDiaglog('提示','电子邮箱已经注册，请重新输入一个',null,function(){
					top.closeDialog();
				});
				return ;
			}
		}
		
		
		//表单提交
		$("#add_form").ajaxSubmit({
			url:"${path}/sysReleaseUserController/add.do",
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
	
	//返回列表
	function goBackList(){
		window.location="${path}/sysReleaseUserController/toList.do";
	}
	
	function closeDialog(){
    	art.dialog.list["selectUploadImages"].close();
    }
	
	//获得拼音
	function getChinesePinYin(){
		var name=$.trim($('#userName').val());
		var account=$.trim($('#account').val());
		if(!account){
			$.ajax({
				url:'${path}/sysUserController/getUserAccount.do',
				cache:false,
				async:true,
				data:{name:name},
				dataType:'json',
				type:'post',
				success:function(data){
					if(data&&data.success){
						$('#account').val(data.obj)
					}
				}
			});
		}
	}
	
	function checkIsExist(name,type){
		var flag=false;
		$.ajax({
			url:'${path}/sysUserController/getIsExist.do',
			cache:false,
			async:false,
			data:{name:name,type:type},
			dataType:'json',
			type:'post',
			success:function(data){
				if(data&&data.success){
					flag=true;
				}
			}
		});
		return flag;
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
   			
   			<div>
   			
   			
   			<form id="add_form" method="post">
				<ul class="forminfo">
					<li><label>组织类型：</label>
						<select name="orgType" id=orgType class="form-control input-primary" style="width:350px">
							<option value="1" selected="selected">社会组织</option>
							<option value="2">志愿服务组织</option>
							<option value="3" >团组织</option>
							<option value="4" >其他组织</option>
						</select>
					</li>
					<li><label>发布方名称：<font color="red">*</font></label><input name="sysUser.userName" id="userName" type="text" class="form-control input-primary" style="width:350px" onblur="getChinesePinYin();" /><input type="hidden" value="3" name="sysUser.userType"></li>
					<li><label>登录账号：<font color="red">*</font></label><input name="sysUser.account" id="account" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>电子邮箱：</label><input name="sysUser.email" id="email" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>密码：<font color="red">*</font></label><input name="sysUser.password" id="password" type="password" class="form-control input-primary" style="width:350px" /></li>
					<li><label>发布方QQ号码：</label><input name="qq" id="qq" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>图像地址：</label><input name="imgFilePath" id="imgFilePath" type="text" class="form-control input-primary" style="width:350px" readonly="readonly"/> <input name="" type="button" class="btn btn-primary" value="编辑图像" onclick="toMeituUpload()"/> </li>
					<li><label>联系人：</label><input name="contactsUser" id="contactsUser" type="text" class="form-control input-primary" style="width:350px"  /></li>
					<li><label>联系手机号码：</label><input name="contactsPhone" id="contactsPhone" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>所在地址：</label><input name="address" id="address" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>常用举办地址：</label><input name="commonAddress" id="commonAddress" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>主办方简介：</label>
						<textarea class="form-control" style="width:350px;" name="introduce" id="introduce"></textarea>
					</li>
	    			<li><label>状态</label>
						<input type="radio" id="status" name="sysUser.status" value="1" checked="checked">启用
						<input type="radio" id="status" name="sysUser.status" value="2">锁定
					</li>
	    			<li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" value="确认保存" onclick="toSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>