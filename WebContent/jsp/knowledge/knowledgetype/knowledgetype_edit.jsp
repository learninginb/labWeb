<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${path}/jslib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${path}/jslib/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<style type="text/css">
#resource-left{
 width: 15%;
 float: left;
 padding-right: 10px;
 overflow :auto;
}
#resource-right{
padding-left:10px;
 width:85%;
 height: 100%;
 float: left;
}
</style>
<title>编辑页面</title>
<script type="text/javascript">
	var zTree;
	var selectNode;
	var load = false;
	$(function() {
		//设置select的值
		var val = $("#knowledgeTypeStateHidden").val();
		$("#knowledgeTypeState").find("option[value='"+val+"']").attr("selected",true);
		
		//设置select的值,上级分类
		var val = $("#parentId").val();
		$.ajax({
			url:"${path}/knowledgeTypeController/findJsonById.do",
			data:{id:val},
			dataType : 'JSON',
			type : 'post',
			success:function(data) {
				$("#knowledgeTypeSelect").text(data.obj.knowledgeType);
			}
		});
		
		//下拉树
		zTree = $.fn.zTree.init($("#treeDemo"), setting);
	});
	function toSubmit(){
		//表单验证
		if(!formValidate()){
			return;
		}
		//表单提交
		$("#submit_form").ajaxSubmit({
			url:"${path}/knowledgeTypeController/editSubmit.do",
			data : $("#submit_form").serialize(),
			cache : false,
			dataType : 'JSON',
			type:'post',
			success:function(data){
				if(data.success){
			       showArtDiaglog('提示','编辑成功',function(){//关闭事件
						goBackList();
					},function(){//确定事件
						closeDialog();
					});
				}else{
					showArtDiaglog('提示','编辑失败：'+data.msg,null,function(){
						closeDialog();
					});
				}
			}
		});
	}
	
	var setting = {
			async : {
				enable : true, //是否通过异步方式加载数据
				dataType : "text",
				type : "post",
				url : "${path}/knowledgeTypeController/loadTree.do?timestamp="
						+ new Date().getTime(),
				autoParam : [ "id" ]
			//异步加载时必须传递的父节点的id值
			},
			view : {
				dblClickExpand : true,
				selectedMulti : false
			},
			data : {
				key : {
					name : "text"
				},
				simpleData : {
					enable : true,
					idKey : "id", // id编号命名 默认   
					pIdKey : "pId", // 父id编号命名 默认    
					rootPId : 0
				// 用于修正根节点父节点数据，即 pIdKey 指定的属性值   
				}
			},
			callback : {
				onClick : onClick,
				onAsyncSuccess : zTreeOnAsyncSuccess
			}
		};

		function onClick(e, treeId, treeNode) {
			selectNode = treeNode;
			nodeId = getSelectTreeId();
			nodeName = getSelectTreeName();
			$("#parentId").val(nodeId);
			$("#knowledgeTypeSelect").text(nodeName);
		}

		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			if (!load) {
				var nodes = zTree.getNodesByParam("pId", 0, null);
				$.each(nodes, function(i, n) {
					zTree.expandNode(n, true, false, true);
				});
				load = true;
			} else {
				if (!treeNode) {
					var nodes = zTree.getNodesByParam("pId", 0, null);
					$.each(nodes, function(i, n) {
						zTree.expandNode(n, true, false, true);
					});
				} else {
					var id = treeNode.id;
					if (id == 'root') {
						var children = treeNode.children;
						$.each(children, function(i, child) {
							zTree.expandNode(child, true, false, true);
						});
					}
				}
			}

		}

		function getSelectTreeId() {
			var treeNodeId = "";
			var treeObj = zTree;
			var nodes = treeObj.getSelectedNodes();
			if (nodes.length == 1) {
				treeNodeId = nodes[0].id;
			}
			return treeNodeId;
		}
		function getSelectTreeName() {
			var treeNodeName = "";
			var treeObj = zTree;
			var nodes = treeObj.getSelectedNodes();
			if (nodes.length == 1) {
				treeNodeName = nodes[0].text;
			}
			return treeNodeName;
		}
		
		function formValidate(){
			var parentId = $("#parentId").val();
			var knowledgeType = $("#knowledgeType").val();
			var knowledgeTypeDesc = $("#knowledgeTypeDesc").val();
			if(parentId==""){
				showArtDiaglog('提示','上级目录不能为空',null,function(){
					closeDialog();
				});
				return false;
			}
			if(knowledgeType==""){
				showArtDiaglog('提示','信息分类不能为空',null,function(){
					closeDialog();
				});
				return false;
			}
			if(knowledgeTypeDesc==""){
				showArtDiaglog('提示','分类描述不能为空',null,function(){
					closeDialog();
				});
				return false;
			}
			return true;
		}
		
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
   			<form id="submit_form" method="post">
   				<input type="hidden" name="id" value="${vo.id}"/>
				<ul class="forminfo">
					<c:if test="${not empty vo.parentName }"><li><label>上级分类：</label>
							<div class="btn-group" >
						    	<input type="hidden" name="parentId" id="parentId" value="${vo.parentId}"/>
								<button type="button" name="knowledgeTypeSelect" id="knowledgeTypeSelect" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="width:350px;text-align:left" >
								</button>
								<ul id="selectTree">
									<li id="treeDemo" class="ztree"></li>
								</ul>
							</div>
					</li></c:if>
					<li><label>信息分类：</label><input name="knowledgeType" id="knowledgeType" value="${vo.knowledgeType}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>信息分类描述：</label><input name="knowledgeTypeDesc" id="knowledgeTypeDesc" value="${vo.knowledgeTypeDesc}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>信息分类状态：</label>
						<select name="knowledgeTypeState" id="knowledgeTypeState" class="form-control input-middle" style="width:350px">
    						<option value="1">有效</option>
    						<option value="0">无效</option>
						</select>
						<input type="hidden" id="knowledgeTypeStateHidden" name="knowledgeTypeStateHidden" value="${vo.knowledgeTypeState}" />
					</li>
					<li><label>创建时间：</label><input name="createTime" id="createTime" value="<fmt:formatDate value="${vo.createTime}" />" type="text" class="wdateExt input-primary Wdate" style="width:350px" onFocus="WdatePicker({isShowClear:false})" /></li>
					<li><label>更新时间：</label><input name="modifyTime" id="modifyTime" value="<fmt:formatDate value="${vo.modifyTime}" />" type="text" class="wdateExt input-primary Wdate" style="width:350px" onFocus="WdatePicker({isShowClear:false})" /></li>
	    			<li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" value="确认保存" onclick="toSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>