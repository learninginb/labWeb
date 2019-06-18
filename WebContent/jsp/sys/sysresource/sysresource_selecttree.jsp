<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/global.jsp"%>	
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>授权</title>
<link rel="stylesheet" href="${path}/jslib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${path}/jslib/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${path}/jslib/zTree_v3/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${path}/jslib/jquery.json-2.4.min.js"></script> 
<script type="text/javascript" src="${path}/js/MaskUtil.js"></script>
<style type="text/css">
.tree_div {
  width:360px;
  height: 360px;
  overflow: auto;
}
.button_div{
  text-align: center;
  vertical-align:middle;
  width: 360px;
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
					url :  "${path}/sysResourceController/loadSelectTree.do?id=${roleId}&timestamp="+ new Date().getTime()
				},
				view : {
					dblClickExpand : true,
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
			zTree = $.fn.zTree.init($("#treeDemo"), setting);
		});

		function submitSelect(){
			
			var nodes = zTree.getCheckedNodes(true);
			var parms = [];
			
			$.each(nodes,function(i,node){
				var resid = node.id;
				var roleId = '${roleId}';
				var po = {"roleId":roleId,"resourceId":resid};
				parms.push(po);
			});
			MaskUtil.mask();
			$.ajax({
				  url:"${path}/sysRoleController/toAuthorization.do",
				  type:"post",
				  data:$.toJSON(parms),
				  dataType:"json",
				  contentType:"application/json",
				  cache:false,
				  success:function(json){
					  if(json.success){
						  MaskUtil.unmask();
					  }else{
						  alert('授权失败');
					  }
					  artDialog.open.origin.closeDialog();
				   },error:function(data){
					   
				   }
				});
			window.parent.closeDialog();
		}
	</script>
</head>
<body style="width: 360px; height: 400px;overflow:hidden">
	<div id="treeDemo" class="ztree tree_div"></div>
	<div class="button_div">
	 <input type="button" class="btn btn-primary" onclick="submitSelect();" value="确定"/>
	</div>
</body>
</html>