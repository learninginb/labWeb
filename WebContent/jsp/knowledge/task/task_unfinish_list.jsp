<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>未完成列表</title>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<script type="text/javascript" src="${path }/js/dateTime.js"></script>
<script type="text/javascript">

//设置查询参数
function postQueryParams(params) {
	var queryParams = $("#searchForm").serializeObject();
	queryParams.limit = params.limit;
	queryParams.offset = params.offset;
	return queryParams;
}
//查询列表
function queryList() {
	$('#UnfinishTaskList').bootstrapTable('refresh');
}

function typeFormatter(value, row, index) {
	if (value == '1') {
		return '★'
	} else if (value == '2') {
		return '★★';
	} else if(value == '3'){
		return "★★★";
	}else{
		return "-"
	}
	
}
//时间格式
function changeDateForm(value,row,index){
	return dateFormat(value);
}
//提交审核
function sumbitById(id){
	window.location="${path}/jsp/knowledge/task/task_add_review.jsp?taskId="+id;
}

function viewById(id){
	var task = $('#UnfinishTaskList').bootstrapTable('getRowByUniqueId', id);
	window.parent.showModal(task);
}

//查看审核详情
function viewReviewById(id){
	getAjax("${path}/TaskController/reviews.do",{taskId:id},(data)=>{
		window.parent.showReview(data['taskReview']);
	})
	
}
//新建子任务
function toAddSon(){
	var rows = $('#UnfinishTaskList').bootstrapTable('getSelections');
	if (rows.length > 0 && rows.length<2) {
		if (rows[0].statusId==3){
			var now = new Date();
			if(rows[0].finishTime>=now){
				window.location = "${path}/jsp/knowledge/task/task_addSon.jsp?id="+rows[0].id;
			}else{
				//提示信息
				top.showArtDiaglog('提示', '该任务已经结束', null, function() {
					top.closeDialog();
				});
			}
		}
		else{
			//提示信息
			top.showArtDiaglog('提示', '必须是未完成的任务才可新建子任务', null, function() {
				top.closeDialog();
			});
		}
	} else {
		//提示信息
		top.showArtDiaglog('提示', '请选择一条数据进行操作', null, function() {
			top.closeDialog();
		});
	}
}
//操作工具栏
function operatorFormatter(value, row, index) {
	var operator='';
    	
		<shiro:hasPermission name="Task">
			operator += '<button class="btn btn-success btn-round btn-xs" onclick="viewById(\''+row.id+'\')"><i class="glyphicon glyphicon-list-alt"></i>详情</button>&nbsp;&nbsp;'
		</shiro:hasPermission>
		if (row.statusId==3){
			<shiro:hasPermission name="Task:edit">
				operator += '<button class="btn grey-cascade btn-xs" onclick="sumbitById(\''+row.id+'\');"><i class="glyphicon glyphicon-floppy-saved"></i>提交</button>&nbsp;&nbsp;';
			</shiro:hasPermission>
		}
		else if(row.statusId==4){
			<shiro:hasPermission name="Task">
				operator += '<button class="btn btn-info btn-round btn-xs" onclick="viewReviewById(\''+row.id+'\')"><i class="glyphicon glyphicon-list-alt"></i>审核详情</button>&nbsp;&nbsp;'
			</shiro:hasPermission>
		}else if(row.statusId==5){
			<shiro:hasPermission name="Task">
			operator += '<button class="btn btn-primary btn-round btn-xs" onclick="sumbitById(\''+row.id+'\')"><i class="glyphicon glyphicon-list-alt"></i>重新提交</button>&nbsp;&nbsp;'
		</shiro:hasPermission>
		}
	return operator;
}
</script>
</head>
<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">任务管理</a></li>
    <li><a href="#">未完成的任务</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
		<div>
    		<form id="searchForm" name="searchForm"  method="post">
    			任务主题：<input type="text" name="theme" class="txtSearch">&nbsp;
    			<input type="button" class="btn btn-info btn-round" value="查询" onclick="queryList()">&nbsp;&nbsp;
    			<input type="button" class="btn btn-warning btn-round" value="重置" onclick="$('#searchForm')[0].reset();"> 
    		</form>
    	</div>
	    <div id="toolbar" class="btn-group">
	    	<shiro:hasPermission name="Task:add">
				<button class="btn btn-info btn-round" onclick="toAddSon();">
					<i class="glyphicon glyphicon-plus"></i>添加子任务
				</button>
			</shiro:hasPermission>
	    </div>
    	<table id="UnfinishTaskList" data-toggle="table"
			data-url="${path}/TaskController/unfinishList.do" data-pagination="true"
			data-side-pagination="server" data-cache="false" data-query-params="postQueryParams"
			data-page-list="[10, 15, 20, 30, 50,100]" data-method="post"
			data-show-refresh="true" data-show-toggle="true"
			data-show-columns="true" data-toolbar="#toolbar"
			data-click-to-select="true" data-single-select="true" data-unique-id="id"
			data-striped="true" data-content-type="application/x-www-form-urlencoded"
			>
			<thead>
				<tr>
					<th data-field="" data-checkbox="true"></th>
					<th data-field="theme">任务主题</th>
					<th data-field="type">任务类型</th>
					<th data-field="importance" data-formatter="typeFormatter">重要程度</th>
					<th data-field="pic">负责人</th>
					<th data-field="distributor">分配者</th>
					<th data-field="beginTime" data-formatter="changeDateForm" >开始时间</th>
					<th data-field="finishTime" data-formatter="changeDateForm" >截至时间</th>
					<th data-field="status" >任务状态</th>
					<th data-field="operator" data-formatter="operatorFormatter">操作</th>
				</tr>
			</thead>
		</table>
    </div>
</body>
</html>