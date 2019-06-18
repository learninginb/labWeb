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
<title>查询列表</title>
<script>
	var zTree;
	var selectNode;
	var load = false;
	var knowledgeState;
	$(function(){
		zTree = $.fn.zTree.init($("#treeDemo"), setting);
		knowledgeState = $("#knowledgeState").val();
	})
	//删除
	function toRemove(){
		var ids=getSelectedRowsIds('KnowledgeList');
		if(ids){
			top.showConfirmDiaglog('提示','删除数据不可恢复，确定要删除吗？',function(){  //关闭事件
				refleshData('KnowledgeList');
			},function(){  //确认事件
				 $.post('${path}/knowledgeController/deleteById.do?ids='+ids,function(data){
					var json=$.parseJSON(data);
				    if(json.success){
					   showArtDiaglog('提示','删除成功',function(){  //关闭事件
	 					},function(){   //确定事件
	 						closeDialog();
	 					});
				    }else{
					   showArtDiaglog('提示','删除失败',function(){  //关闭事件
	 					},function(){   //确定事件
	 						closeDialog();
	 					});
				    }
				 });
			});
		}else{
			showArtDiaglog('提示','请选择一条数据进行操作',null,function(){
				closeDialog();
			});
		}
	}
	
    //查看
    function toInfo(){
    	var selected=getSelectedRowsArr('KnowledgeList');
    	if(selected.length>0&&selected.length<2){
    		window.location='${path}/knowledgeController/findById.do?id='+selected;
    		
    	}else{
    		showArtDiaglog('提示','请选择一条数据进行操作',null,function(){
				closeDialog();
			});
    	}
	}
	
	//设置查询参数
	function postQueryParams(params) {
		var queryParams = $("#searchForm").serializeObject();
		queryParams.limit=params.limit;
		queryParams.offset=params.offset;
		return queryParams;
	}
	//查询列表
    function queryList(){
    	$('#KnowledgeList').bootstrapTable('refresh');
    }
    
    function editById(id){
		window.location='${path}/knowledgeController/editById.do?id='+id;
	}

	//根据id删除
	function deleteById(id){
		showConfirmDiaglog('提示','删除数据不可恢复，确定要删除吗？',function(){  //关闭事件
				refleshData('KnowledgeList');
			},function(){  //确认事件
				 $.post('${path}/knowledgeController/deleteById.do?ids='+id,function(data){
				   var json=$.parseJSON(data);
				   if(json.success){
					   showArtDiaglog('提示','删除成功',function(){  //关闭事件
	 					},function(){   //确定事件
	 						closeDialog();
	 					});
				   }else{
					   showArtDiaglog('提示','删除失败',function(){  //关闭事件
	 					},function(){   //确定事件
	 						closeDialog();
	 					});
				   }
			});
		});
	}

	//根据id查看
	function viewById(id){
			window.location='${path}/knowledgeController/findById.do?id='+id;
	}
	//根据id历史版本
	function toHistoryById(id){
		window.location='${path}/knowledgeHistoryController/toList.do?id='+id;
	}
	
	//查看历史版本
	 function toHistory(){
		 var selected=getSelectedRowsArr('KnowledgeList');
	     if(selected.length>0&&selected.length<2){
	    	 window.location='${path}/knowledgeHistoryController/toList.do?id='+selected;
	     }else{
	    	showArtDiaglog('提示','请选择一条数据进行操作',null,function(){
				closeDialog();
			});
	     }
	 }
	
    //操作工具栏
    function operatorFormatter(value, row, index) {
		return '<button class="btn btn-success btn-round btn-xs" onclick="viewById(\''+row.id+'\')">'+
					'<i class="glyphicon glyphicon-list-alt"></i>详情</button>&nbsp;&nbsp;'+
			   '<button class="btn btn-danger btn-round btn-xs" onclick="deleteById(\''+row.id+'\')">'+
			        '<i class="glyphicon glyphicon-trash"></i>删除</button>&nbsp;&nbsp;'+
			   '<button class="btn btn-info btn-round btn-xs" onclick="toHistoryById(\''+row.id+'\')">'+
			        '<i class="fa fa-search"></i>查看历史版本</button>'
	}
    
    var setting = {
			async : {
				enable : true, //是否通过异步方式加载数据
				dataType : "text",
				type : "post",
				url :  "${path}/knowledgeTypeController/loadTree.do?timestamp="+ new Date().getTime(),
				autoParam : [ "id" ] //异步加载时必须传递的父节点的id值
			},
			view : {
				dblClickExpand : true,
				selectedMulti:false
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
				onAsyncSuccess: zTreeOnAsyncSuccess
			}
		};

	function onClick(e, treeId, treeNode) {
		selectNode = treeNode;
		changeList();
	}
	
	function changeList(pid,parentName,dictType){
		if(!selectNode){
			top.showArtDiaglog('提示','删除失败',function(){  //关闭事件
				},function(){   //确定事件
					top.closeDialog();
			});
		}else{
			var pid=getSelectTreeId();
			$('#KnowledgeList').bootstrapTable('refresh',{url:"${path}/knowledgeController/list.do?knowledgeType="+pid+"&knowledgeState="+knowledgeState});
		} 
	}
	
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
		if(!load){
			var nodes = zTree.getNodesByParam("pId", 0, null);
			$.each( nodes, function(i, n){
				zTree.expandNode(n, true, false, true);
			});
			load = true;
		}else{
			if(!treeNode){
				var nodes = zTree.getNodesByParam("pId", 0, null);
				$.each( nodes, function(i, n){
					zTree.expandNode(n, true, false, true);
				});
			}else{
				var id = treeNode.id;
				if(id=='root'){
					var children = treeNode.children;
					$.each( children, function(i, child){
						zTree.expandNode(child, true, false, true);
					});
				}
			}
		}
	}
	
   function refreshNode() { 
   		var treeObj = zTree;
   		var nodes = treeObj.getSelectedNodes();
   		var node = null;
   		 if(nodes.length==1){
   			node=nodes[0];
   		} 
		if(null==node){//不选择节点则刷新整棵树
			treeObj.reAsyncChildNodes(null, "refresh");			
		}else{
			if(node.isParent){
				treeObj.reAsyncChildNodes(node, "refresh");
  	  		 }else{//如果没有子节点，则刷新父节点
  	  			treeObj.reAsyncChildNodes(node.parentNode, "refresh");
  	  	  	 }
  		}
	}

	function getSelectTreeId(){
		var treeNodeId="";
		var treeObj = zTree;
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length==1){
			treeNodeId=nodes[0].id;
		}
		return treeNodeId;
	}
	 
    //回显状态
    function formatState(value){
    	if(value==0){
    		return "待审核";
    	}else if(value==1){
    		return "已发布";
    	}else if(value==2){
    		return "已点评";
    	}else if(value==3){
    		return "关注过";
    	}else if(value==4){
    		return "收藏";
    	}else if(value==5){
    		return "被驳回";
    	}else if(value==6){
    		return "过期";
    	}else if(value==7){
    		return "回收站";
    	}
    }
</script>
</head>
<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">数据表</a></li>
    <li><a href="#">基本内容</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    <div id="resource-left">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		
		<div id="resource-right">
			<div>
	    		<form id="searchForm" name="searchForm"  method="post">
	    			信息标题：<input type="text" name="knowledgeTitle" class="txtSearch">&nbsp;
	    			<input type="button" class="btn btn-info btn-round" value="查询" onclick="queryList()">&nbsp;&nbsp;
	    			<input type="button" class="btn btn-warning btn-round" value="重置" onclick="$('#searchForm')[0].reset();"> 
	    		</form>
	    	</div>
		    <div id="toolbar" class="btn-group">
				<button class="btn btn-success btn-round" onclick="toInfo()">
					<i class="glyphicon glyphicon-list-alt"></i>详情
				</button>
				<button class="btn btn-danger btn-round" onclick="toRemove()">
					<i class="glyphicon glyphicon-trash"></i>删除
				</button>
				<button class="btn btn-info btn-round" onclick="toHistory()">
					<i class="fa fa-search"></i>查看历史版本
			    </button>
			    <input type="hidden" name="knowledgeState" id="knowledgeState" value="${knowledgeState}"/>
			</div>
	    
	    	<table id="KnowledgeList" data-toggle="table"
				data-url="${path}/knowledgeController/list.do?knowledgeState=${knowledgeState}" data-pagination="true"
				data-side-pagination="server" data-cache="false" data-query-params="postQueryParams"
				data-page-list="[10, 15, 20, 30, 50,100]" data-method="post"
				data-show-refresh="true" data-show-toggle="true"
				data-show-columns="true" data-toolbar="#toolbar"
				data-click-to-select="true" data-single-select="false"
				data-striped="true" data-content-type="application/x-www-form-urlencoded"
				>
				<thead>
					<tr>
						<th data-field="" data-checkbox="true"></th>
						<th data-field="knowledgeTypeName">信息分类</th>
						<th data-field="knowledgeTitle">信息标题</th>
						<th data-field="createUserName">发布人</th>
						<!-- <th data-field="knowledgeComment">正文</th>
						<th data-field="knowledgeIconUrl">图片地址</th>
						<th data-field="knowledgeAttachUrl">附件地址</th> -->
						<th data-field="knowledgeState" data-formatter="formatState">状态</th>
						<th data-field="createTime" data-formatter="dateFormatter" >创建时间</th>
						<th data-field="modifyTime" data-formatter="dateFormatter" >更新时间</th>
						<th data-field="operator" data-formatter="operatorFormatter">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="clear: both;"></div>
    </div>
</body>
</html>