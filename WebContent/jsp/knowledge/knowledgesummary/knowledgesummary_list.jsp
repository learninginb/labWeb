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
		window.location='${path}/knowledgeSummaryController/toAdd.do';
	}
	//删除
	function toRemove(){
		var ids=getSelectedRowsIds('KnowledgeSummaryList');
		if(ids){
			top.showConfirmDiaglog('提示','删除数据不可恢复，确定要删除吗？',function(){  //关闭事件
				refleshData('KnowledgeSummaryList');
			},function(){  //确认事件
				 $.post('${path}/knowledgeSummaryController/deleteById.do?ids='+ids,function(data){
					var json=$.parseJSON(data);
				    if(json.success){
					   top.showArtDiaglog('提示','删除成功',function(){  //关闭事件
	 					},function(){   //确定事件
	 						top.closeDialog();
	 					});
				    }else{
					   top.showArtDiaglog('提示','删除失败',function(){  //关闭事件
	 					},function(){   //确定事件
	 						top.closeDialog();
	 					});
				    }
				 });
			});
		}else{
			top.showArtDiaglog('提示','请选择一条数据进行操作',null,function(){
				top.closeDialog();
			});
		}
	}
	
	//编辑
    function toEdit(){
    	var selected=getSelectedRowsArr('KnowledgeSummaryList');
    	if(selected.length>0&&selected.length<2){
    		window.location='${path}/knowledgeSummaryController/editById.do?id='+selected;
    	}else{
    		//提示信息
    		top.showArtDiaglog('提示','请选择一条数据进行操作',null,function(){
				top.closeDialog();
			});
    	}
	}
	
    //查看
    function toInfo(){
    	var selected=getSelectedRowsArr('KnowledgeSummaryList');
    	if(selected.length>0&&selected.length<2){
    		window.location='${path}/knowledgeSummaryController/findById.do?id='+selected;
    	}else{
    		top.showArtDiaglog('提示','请选择一条数据进行操作',null,function(){
				top.closeDialog();
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
    	$('#KnowledgeSummaryList').bootstrapTable('refresh');
    }
    
    function editById(id){
		window.location='${path}/knowledgeSummaryController/editById.do?id='+id;
	}

	//根据id删除
	function deleteById(id){
		top.showConfirmDiaglog('提示','删除数据不可恢复，确定要删除吗？',function(){  //关闭事件
				refleshData('KnowledgeSummaryList');
			},function(){  //确认事件
				 $.post('${path}/knowledgeSummaryController/deleteById.do?ids='+id,function(data){
				   var json=$.parseJSON(data);
				   if(json.success){
					   top.showArtDiaglog('提示','删除成功',function(){  //关闭事件
	 					},function(){   //确定事件
	 						top.closeDialog();
	 					});
				   }else{
					   top.showArtDiaglog('提示','删除失败',function(){  //关闭事件
	 					},function(){   //确定事件
	 						top.closeDialog();
	 					});
				   }
			});
		});
	}

	//根据id查看
	function viewById(id){
			window.location='${path}/knowledgeSummaryController/findById.do?id='+id;
	}
	
    //操作工具栏
    function operatorFormatter(value, row, index) {
		var operator="";
	    	<shiro:hasPermission name="KnowledgeSummary:edit">
	    		operator+='<button class="btn btn-warning btn-round btn-xs" onclick="editById(\''+row.id+'\');"><i class="glyphicon glyphicon-pencil"></i> 修改</button>&nbsp;&nbsp;';
		    </shiro:hasPermission>
		    <shiro:hasPermission name="KnowledgeSummary:info">
    			operator+='<button class="btn btn-success btn-round btn-xs" onclick="viewById(\''+row.id+'\')"><i class="glyphicon glyphicon-list-alt"></i>详情</button>&nbsp;&nbsp;';
	    	</shiro:hasPermission>
	    	<shiro:hasPermission name="KnowledgeSummary:remove">
				operator+='<button class="btn btn-danger btn-round btn-xs" onclick="deleteById(\''+row.id+'\')"><i class="glyphicon glyphicon-trash"></i>删除</button>';
    		</shiro:hasPermission>
		return operator;
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
    			<input type="text" name="columnName" class="txtSearch">&nbsp;
    			<input type="button" class="btn btn-info btn-round" value="查询" onclick="queryList()">&nbsp;&nbsp;
    			<input type="button" class="btn btn-warning btn-round" value="重置" onclick="$('#searchForm')[0].reset();"> 
    		</form>
    	</div>
	    <div id="toolbar" class="btn-group">
			<shiro:hasPermission name="KnowledgeSummary:add">
		    	<button class="btn btn-info btn-round" onclick="toAdd();">
					<i class="glyphicon glyphicon-plus"></i>添加
				</button>
		    </shiro:hasPermission>
	    	<shiro:hasPermission name="KnowledgeSummary:edit">
	    		<button class="btn btn-warning btn-round" onclick="toEdit();">
					<i class="glyphicon glyphicon-pencil"></i> 修改
				</button>
	    	</shiro:hasPermission>
			<shiro:hasPermission name="KnowledgeSummary:info">
				<button class="btn btn-success btn-round" onclick="toInfo()">
					<i class="glyphicon glyphicon-list-alt"></i>详情
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="KnowledgeSummary:remove">
				<button class="btn btn-danger btn-round" onclick="toRemove()">
					<i class="glyphicon glyphicon-trash"></i>删除
				</button>
			</shiro:hasPermission>
		</div>
    
    	<table id="KnowledgeSummaryList" data-toggle="table"
			data-url="${path}/knowledgeSummaryController/list.do" data-pagination="true"
			data-side-pagination="server" data-cache="false" data-query-params="postQueryParams"
			data-page-list="[10, 15, 20, 30, 50,100]" data-method="post"
			data-show-refresh="true" data-show-toggle="true"
			data-show-columns="true" data-toolbar="#toolbar"
			data-click-to-select="true" data-single-select="false"
			data-striped="true" data-content-type="application/x-www-form-urlencoded">
			<thead>
				<tr>
					<th data-field="" data-checkbox="true"></th>
					<th data-field="knowledgeId">信息id</th>
					<th data-field="readTimes">阅读次数</th>
					<th data-field="collectionTimes">收藏次数</th>
					<th data-field="followTimes">关注次数</th>
					<th data-field="commentTimes">评论次数</th>
					<th data-field="operator" data-formatter="operatorFormatter">操作</th>
				</tr>
			</thead>
		</table>
    </div>
</body>
</html>