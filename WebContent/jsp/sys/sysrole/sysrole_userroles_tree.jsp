<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/global.jsp"%>	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择角色树</title>
<link rel="stylesheet" href="${path}/jslib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${path}/jslib/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${path}/jslib/zTree_v3/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${path}/jslib/zTree_v3/js/jquery.ztree.exhide-3.5.min.js"></script>
<script type="text/javascript" src="${path}/jslib/jquery.json-2.4.min.js"></script> 

<style type="text/css">
.txt_search {
    width: 200px;
	padding-left: 10px;
	font-size: 14px;
	height: 25px;
	line-height: 25px;
	border: 1px solid #d2d2d2;
	margin-bottom: 10px;
	color:#555555;
}
.tree_div {
  height: 300px;
  width:300px;
  overflow: auto;
}
.button_div{
  text-align: center;
  vertical-align:middle;
   width:300px;
}
</style>
	<script type="text/javascript">
	    var zTree;
	    var selectNode;
		var setting = {
				check : {
					enable : true,
					chkStyle : "checkbox"
				},
				async : {
					enable : true, //是否通过异步方式加载数据
					dataType : "text",
					type : "post",
					url :  "${path}/sysRoleController/loadRoleTree.do?roleId=${roleId}&timestamp="+ new Date().getTime(),
					autoParam : [ "id" ] //异步加载时必须传递的父节点的id值
				},
				view : {
					dblClickExpand : true,
					showIcon : false,
					showLine : false,
					showTitle : false,
					selectedMulti : true
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
					
				}
			};
			
		$(function() {
			//树搜索框监听事件
			$('#addbook_search').attr("autocomplete","off"); 
			$('#addbook_search').bind('propertychange input ', function(e) {
				booksearch();
		    });
			zTree = $.fn.zTree.init($("#treeDemo"), setting);
		});
		
		function submitSelect(){
			var nodes = zTree.getCheckedNodes(true);
			var parms = [];
			var userId = "${userId}";
			$.each(nodes,function(i,node){
				var roleId = node.id;
				var po = {"userId":userId,"roleId":roleId};
				parms.push(po);			
			});
			var len = parms.length;
			if(len==0){//传递userId到后台删除该用户所有角色
				var po = {"userId":userId,"roleId":""};
				parms.push(po);	
			}
			$.ajax({
				  url:"${path}/sysUserController/toSetUserRoles.do",
				  type:"post",
				  data:$.toJSON(parms),
				  dataType:"json",
				  contentType:"application/json",
				  cache:false,
				  success:function(json){
					  if(json.success){
						  artDialog.open.origin.closeDialog();
					  }else{
						  alert('设置失败');
					  }
					  artDialog.open.origin.closeDialog();
				   },error:function(data){
					   alert('服务异常');
				   }
			});
		}
		function booksearch(){
			var cond = $('#addbook_search').val();
			if(cond == $('#addbook_search').attr("placeholder")) return;
			if(cond){
				var zNodes = zTree.getNodes();
				$.each(zNodes,function(i,node){
					var text = node.text;
					if(text.indexOf(cond)>-1){
						zTree.showNode(node); //显示
					}else{
						zTree.hideNode(node);  //隐藏
					}
				});
				zTree.expandAll(true);
			}else{
				var nodes = zTree.getNodesByParam("isHidden", true);
				zTree.showNodes(nodes);
				zTree.expandAll(false);
			}
			
		}

	</script>
  </head>
  
  <body style="width: 300px;height: 380px;overflow:hidden">
 	<div >
       <input id="addbook_search" type="text"  class="txt_search" placeholder="输入您要搜索的角色"
       onblur="if (value ==''){value='输入您要搜索的角色'}" onfocus="if (value =='输入您要搜索的角色'){value =''};" value="输入您要搜索的角色"/>
    </div>
	<div id="treeDemo" class="ztree tree_div"></div>
	<div class="button_div">
	 <input type="button" class="btn btn-primary" onclick="submitSelect();" value="确定"/>
	</div>
 </body>
</html>