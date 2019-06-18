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
<script type="text/javascript" charset="utf-8" src="../jslib/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../jslib/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
	var zTree;
	var selectNode;
	var load = false;
	var ue;
	$(function() {
		//设置select的值
		var val = $("#knowledgeTypeHidden").val();
		/* $("#knowledgeType").find("option[value='"+val+"']").attr("selected",true); */
		$.ajax({
			url:"${path}/knowledgeTypeController/findJsonById.do",
			data:{id:val},
			dataType : 'JSON',
			type : 'post',
			success:function(data) {
				$("#knowledgeType").text(data.obj.knowledgeType);
			}
		});
		
		ue = createUeditor("ueditor", 300, 700);//ue编辑器
		//ue_img = createUeditor("ue_img", 100, 700);//ue图片上传
		ue_file = createUeditor("ue_file", 100, 700);//ue文件上传
		/* ue_img.ready(function() {
			uploadimg(ue_img, "knowledgeIconUrl");
		}); */
		ue_file.ready(function() {
			uploadfile(ue_file, "knowledgeAttachUrl");
		});

		//下拉树
		zTree = $.fn.zTree.init($("#treeDemo"), setting);
		
		//设置状态字段
		val = $("#knowledgeState").val();
		if(val==0){
			$("#knowledgeStateText").val("待审核");
		}else if(val==1){
			$("#knowledgeStateText").val("已发布");
		}else if(val==2){
			$("#knowledgeStateText").val("已点评");
		}else if(val==3){
			$("#knowledgeStateText").val("关注过");
		}else if(val==4){
			$("#knowledgeStateText").val("收藏");
		}else if(val==5){
			$("#knowledgeStateText").val("被驳回");
		}else if(val==6){
			$("#knowledgeStateText").val("过期");
		}else if(val==7){
			$("#knowledgeStateText").val("回收站");
		}
	});
	function toSubmit() {
		//表单验证
		if(!formValidate()){
			return;
		}
		//表单提交
		$("#submit_form").ajaxSubmit({
			url : "${path}/knowledgeController/editSubmit.do",
			data : $("#submit_form").serialize(),
			cache : false,
			dataType : 'JSON',
			type : 'post',
			success : function(data) {
				if (data.success) {
					showArtDiaglog('提示', '编辑成功', function() { //关闭事件
						goBackList();
					}, function() { //确定事件
						closeDialog();
					});
				} else {
					showArtDiaglog('提示', '编辑失败:'+data.msg, null, function() {
						closeDialog();
					});
				}
			}
		});
	}

	//返回列表
	function goBackList() {
		window.location = "${path}/knowledgeController/toList.do";
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
		$("#selectHidden").val(nodeId);
		$("#knowledgeType").text(nodeName);
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
		var knowledgeType = $("#knowledgeTypeHidden").val();
		var knowledgeTitle = $("#knowledgeTitle").val();
		var knowledgeContent = ue.getContent();
		var knowledgeVersion = $("#knowledgeVersion").val();
		if(knowledgeType==""){
			top.showArtDiaglog('提示','请选择信息分类',function(){//关闭事件
			},function(){//确定事件
				top.closeDialog();
			});
			return false;
		}
		if(knowledgeTitle==""){
			top.showArtDiaglog('提示','请填写标题',function(){//关闭事件
			},function(){//确定事件
				top.closeDialog();
			});
			return false;
		}
		if(knowledgeContent==""){
			top.showArtDiaglog('提示','请填写信息内容',function(){//关闭事件
			},function(){//确定事件
				top.closeDialog();
			});
			return false;
		}
		if(knowledgeVersion==""){
			top.showArtDiaglog('提示','请填写版本号',function(){//关闭事件
			},function(){//确定事件
				top.closeDialog();
			});
			return false;
		}
		//对版本号进行校验
		var reg = /^(\d+\.\d{1,1}|\d+)$/;
		if(!knowledgeVersion.match(reg)){
			top.showArtDiaglog('提示','版本号必须为整数或者带一位小数如1或1.0',function(){//关闭事件
			},function(){//确定事件
				top.closeDialog();
			});
			return false;
		}
		return true;
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
					<li><label>信息分类：</label>
						<%-- <select name="knowledgeType" id="knowledgeType" class="form-control input-middle" style="width:350px">
    						<c:forEach var="list" items="${knowledgeTypeList}">
    							<option value="${list.id}">${list.knowledgeType}</option>
    						</c:forEach>
						</select> --%>
  						<%-- <input type="hidden" id="knowledgeTypeHidden" name="knowledgeTypeHidden" value="${vo.knowledgeType}" />--%>
							<div class="btn-group" >
						    	<input type="hidden" name="knowledgeType" id="knowledgeTypeHidden" value="${vo.knowledgeType}"/>
								<button type="button" name="knowledgeTypeSelect" id="knowledgeType" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="width:350px;text-align:left">
								</button>
								<ul id="selectTree">
									<li id="treeDemo" class="ztree"></li>
								</ul>
							</div>
						</li>
					<li><label>信息标题：</label><input name="knowledgeTitle" id="knowledgeTitle" value="${vo.knowledgeTitle}" type="text" class="form-control input-primary" style="width:350px" /></li>
					<li><label>正文：</label>
    					<textarea id="ueditor" name="knowledgeComment">
							${vo.knowledgeComment}
						</textarea>
					</li>
					<%-- <li><label>图片：</label>
						<input name="knowledgeIconUrl" id="knowledgeIconUrl" type="text" value="${vo.knowledgeIconUrl }" class="form-control input-inline input-medium" style="float:left;width:350px" readonly="true" />
						<input type="button" value="上传图片" class="btn btn-primary" onclick="upImage(ue_img)"/>
						<script type="text/plain" id="ue_img"></script>
					</li> --%>
					<li><label>附件地址：</label>
						<input name="knowledgeAttachUrl" id="knowledgeAttachUrl" type="text" value="${vo.knowledgeAttachUrl }" class="form-control input-inline input-medium" style="width:350px;float:left;" readonly="true" />
						<input type="button" value="上传文件" class="btn btn-primary" onclick="upFiles(ue_file)"/></li>
						<script type="text/plain" id="ue_file"></script>
					</li>
					<li><label>状态：</label>
						<input name="knowledgeStateText" id="knowledgeStateText" type="text" class="form-control input-inline input-medium" style="width:350px;float:left;" readonly="true" />
						<input name="knowledgeState" id="knowledgeState" type="hidden" value="${vo.knowledgeState }" class="form-control input-inline input-medium" style="width:350px;float:left;" readonly="true" />
					</li>
					<li><label>版本号：</label>
						<input name="knowledgeVersion" id="knowledgeVersion" type="text" value="${vo.knowledgeVersion }" class="form-control input-inline input-medium" style="width:350px;float:left;" />
					</li>
	    			<li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" value="确认保存" onclick="toSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>