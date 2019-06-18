<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑页面</title>
<script type="text/javascript">
	$(function() {
		
	});
	function toSubmit(){
		//表单验证
	
		//表单提交
		$("#submit_form").ajaxSubmit({
			url:"${path}/knowledgeHistoryController/editSubmit.do",
			data : $("#submit_form").serialize(),
			cache : false,
			dataType : 'JSON',
			type:'post',
			success:function(data){
				if(data.success){
			       top.showArtDiaglog('提示','编辑成功',function(){  //关闭事件
						goBackList();
					},function(){   //确定事件
						top.closeDialog();
					});
				}else{
					top.showArtDiaglog('提示','编辑失败',null,function(){
						top.closeDialog();
					});
				}
			}
		});
	}
	
	//返回列表
	function goBackList(){
		window.location="${path}/knowledgeHistoryController/toList.do";
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
   			<form id="submit_form" method="post">
   				<input type="hidden" name="id" value="${vo.id}"/>
				<ul class="forminfo">
					<li><label>信息id：</label><input name="knowledgeId" id="knowledgeId" value="${vo.knowledgeId}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>版本号：</label><input name="knowledgeVersion" id="knowledgeVersion" value="${vo.knowledgeVersion}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>信息分类id：</label><input name="knowledgeType" id="knowledgeType" value="${vo.knowledgeType}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>信息标题：</label><input name="knowledgeTitle" id="knowledgeTitle" value="${vo.knowledgeTitle}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>正文：</label><input name="knowledgeComment" id="knowledgeComment" value="${vo.knowledgeComment}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>图片地址：</label><input name="knowledgeIconUrl" id="knowledgeIconUrl" value="${vo.knowledgeIconUrl}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>附件地址：</label><input name="knowledgeAttachUrl" id="knowledgeAttachUrl" value="${vo.knowledgeAttachUrl}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>状态（0-待审核1-已发布2-已点评3-关注过4-收藏5-被驳回6-过期7-回收站）：</label><input name="knowledgeState" id="knowledgeState" value="${vo.knowledgeState}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>发布人id：</label><input name="createUser" id="createUser" value="${vo.createUser}" type="text" class="form-control input-primary" style="width:350px" /></li>
	    			<li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" value="确认保存" onclick="toSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>