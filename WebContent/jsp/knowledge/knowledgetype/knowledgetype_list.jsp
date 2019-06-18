<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询列表</title>
<script>
	//添加
	function toAdd(){
		window.location='${path}/knowledgeTypeController/toAdd.do';
	}
	//删除
	function toRemove(){
		var ids=getSelectedRowsIds('KnowledgeTypeList');
		var rows = getSelectedRows('KnowledgeTypeList');
		for(var i=0;i<rows.length;i++){
			if(rows[i].parentId==0){
				showArtDiaglog('提示','根目录不能删除！',null,function(){
					closeDialog();
				});
				return;
			}
		}
		if(ids){
			top.showConfirmDiaglog('提示','删除数据不可恢复，确定要删除吗？',function(){  //关闭事件
				refleshData('KnowledgeTypeList');
			},function(){  //确认事件
				 $.post('${path}/knowledgeTypeController/deleteById.do?ids='+ids,function(data){
					var json=$.parseJSON(data);
				    if(json.success){
					   showArtDiaglog('提示','删除成功',function(){  //关闭事件
	 					},function(){   //确定事件
	 						closeDialog();
	 					});
				    }else{
					   showArtDiaglog('提示','删除失败：'+json.msg,function(){  //关闭事件
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
	
	//编辑
    function toEdit(){
    	var selected=getSelectedRowsArr('KnowledgeTypeList');
    	if(selected.length>0&&selected.length<2){
    		window.location='${path}/knowledgeTypeController/editById.do?id='+selected;
    	}else{
    		//提示信息
    		showArtDiaglog('提示','请选择一条数据进行操作',null,function(){
				closeDialog();
			});
    	}
	}
	
    //查看
    function toInfo(){
    	var selected=getSelectedRowsArr('KnowledgeTypeList');
    	if(selected.length>0&&selected.length<2){
    		window.location='${path}/knowledgeTypeController/findById.do?id='+selected;
    		
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
    	$('#KnowledgeTypeList').bootstrapTable('refresh');
    }
    
    function editById(id){
		window.location='${path}/knowledgeTypeController/editById.do?id='+id;
	}

	//根据id删除
	function deleteById(id){
		top.showConfirmDiaglog('提示','删除数据不可恢复，确定要删除吗？',function(){  //关闭事件
				refleshData('KnowledgeTypeList');
			},function(){  //确认事件
				 $.post('${path}/knowledgeTypeController/deleteById.do?ids='+id,function(data){
				   var json=$.parseJSON(data);
				   if(json.success){
					   showArtDiaglog('提示','删除成功',function(){  //关闭事件
	 					},function(){   //确定事件
	 						closeDialog();
	 					});
				   }else{
					   showArtDiaglog('提示','删除失败：'+json.msg,function(){  //关闭事件
	 					},function(){   //确定事件
	 						closeDialog();
	 					});
				   }
			});
		});
	}

	//根据id查看
	function viewById(id){
			window.location='${path}/knowledgeTypeController/findById.do?id='+id;
	}
	
    //操作工具栏
    function operatorFormatter(value, row, index) {
    	var operator='';
	    	<shiro:hasPermission name="KnowledgeType:edit">
				operator += '<button class="btn btn-warning btn-round btn-xs" onclick="editById(\''+row.id+'\');"><i class="glyphicon glyphicon-pencil"></i> 修改</button>&nbsp;&nbsp;';
			</shiro:hasPermission>
			<shiro:hasPermission name="KnowledgeType:info">
				operator += '<button class="btn btn-success btn-round btn-xs" onclick="viewById(\''+row.id+'\')"><i class="glyphicon glyphicon-list-alt"></i>详情</button>&nbsp;&nbsp;'
			</shiro:hasPermission>
			<shiro:hasPermission name="KnowledgeType:remove">
				if(row.parentId== 0){
					operator += '<button class="btn btn-danger btn-round btn-xs" onclick="deleteById(\''+row.id+'\')" disabled="disabled"><i class="glyphicon glyphicon-trash"></i>删除</button>&nbsp;&nbsp;'
				}else{
					operator += '<button class="btn btn-danger btn-round btn-xs" onclick="deleteById(\''+row.id+'\')"><i class="glyphicon glyphicon-trash"></i>删除</button>&nbsp;&nbsp;'
				}
			</shiro:hasPermission>
		return operator;
	}
   //状态转化
    function formatState(value){
    	if(value==1){
    		return "有效";
    	}else{
    		return "无效";
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
		<div>
    		<form id="searchForm" name="searchForm"  method="post">
    			信息分类：<input type="text" name="knowledgeType" class="txtSearch">&nbsp;
    			<input type="button" class="btn btn-info btn-round" value="查询" onclick="queryList()">&nbsp;&nbsp;
    			<input type="button" class="btn btn-warning btn-round" value="重置" onclick="$('#searchForm')[0].reset();"> 
    		</form>
    	</div>
	    <div id="toolbar" class="btn-group">
			<button class="btn btn-info btn-round" onclick="toAdd();">
				<i class="glyphicon glyphicon-plus"></i>添加
			</button>
			<button class="btn btn-warning btn-round" onclick="toEdit();">
				<i class="glyphicon glyphicon-pencil"></i> 修改
			</button>
			<button class="btn btn-success btn-round" onclick="toInfo()">
				<i class="glyphicon glyphicon-list-alt"></i>详情
			</button>
			<button class="btn btn-danger btn-round" onclick="toRemove()">
				<i class="glyphicon glyphicon-trash"></i>删除
			</button>
		</div>
    
    	<table id="KnowledgeTypeList" data-toggle="table"
			data-url="${path}/knowledgeTypeController/list.do" data-pagination="true"
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
					<th data-field="knowledgeType">信息分类</th>
					<th data-field="knowledgeTypeDesc">信息分类描述</th>
					<th data-field="knowledgeTypeState" data-formatter="formatState">信息分类状态</th>
					<th data-field="createTime" data-formatter="dateFormatter" >创建时间</th>
					<th data-field="modifyTime" data-formatter="dateFormatter" >更新时间</th>
					<th data-field="operator" data-formatter="operatorFormatter">操作</th>
				</tr>
			</thead>
		</table>
    </div>
</body>
</html>