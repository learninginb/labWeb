<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻-列表</title>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
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
	$('#NewsList').bootstrapTable('refresh');
}

function statusFormatter(value, row, index) {
	if (value == '1') {
		return '<span class="label label-default label-lg">未提交</span>'
	} else if (value == '0') {
		return '<span class="label label-danger label-lg">禁用</span>';
	} else if (value=='2') {
		return '<span class="label label-info label-lg">审核中</span>';
	}else if(value=='3'){
		return '<span class="label label-success label-lg">已发布</span>';
	}else if(value=='4'){
		return '<span class="label label-warning label-lg">审核未通过</span>'
	}
}
//查看详情
function newsInfo(id){
	window.location="${path}/jsp/knowledge/news/news_info.jsp?id="+id;
}

function deleteById(id){
		top.showConfirmDiaglog('提示','删除将不可恢复，是否确定删除？',function(){
			refleshData("NewsList");
		},function(){
			postAjax("${path}/NewsController/news.do",{ids:id,_method:'DELETE'},function(data){
				top.showArtDiaglog('提示','删除成功',function(){	},
						function(){
							top.closeDialog();
							window.location="${path}/NewsController/toMyNews.do";
						});
			}.bind(this));
		});
}

function editById(id){
	window.location = "${path}/jsp/knowledge/news/news_editor.jsp?id="+id;
}

//删除新闻
function toRemove(){
	var ids = getSelectedRowsIds("NewsList");
	console.log("-----"+ids);
	if(ids){
		top.showConfirmDiaglog('提示', '删除数据不可恢复，是否确定删除？', function() { //关闭事件
		}, function() { //确认事件
			postAjax('${path}/NewsController/news.do',{ids:ids,_method:'DELETE'},(data)=>{
				top.showArtDiaglog('提示', '删除成功', function() { //关闭事件
				}, function() { //确定事件
					top.closeDialog();
					window.location="${path}/NewsController/toMyNews.do";
				});
			})
		});
	}else {
		top.showArtDiaglog('提示', '请选择一条数据进行操作', null, function() {
			top.closeDialog();
		});
	}
}

function importanceFormatter(value,row,index){
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

function submitById(id) {
	console.log(id)
	postAjax('${path}/NewsController/submit.do',{ids:id},(data)=>{
		top.showArtDiaglog('提示', '提交成功', function() { //关闭事件
		}, function() { //确定事件
			top.closeDialog();
			window.location="${path}/NewsController/toMyNews.do";
		});
	});
}

function toSubmit() {
	var ids = getSelectedRowsIds("NewsList");
	if(ids){
		postAjax('${path}/NewsController/submit.do',{ids:ids},(data)=>{
			top.showArtDiaglog('提示', '提交成功', function() { //关闭事件
			}, function() { //确定事件
				top.closeDialog();
				window.location="${path}/NewsController/toMyNews.do";
			});
		})
	}
}

function operatorFormatter(value, row, index) {
	var operator = "";
	<shiro:hasPermission name="News:edit">
	operator += '<button class="btn btn-warning btn-round btn-xs" onclick="editById(\''
			+ row.id
			+ '\');"><i class="glyphicon glyphicon-pencil"></i> 修改</button>&nbsp;&nbsp;';
	</shiro:hasPermission>
	operator += '<button class="btn btn-success btn-round btn-xs" onclick="newsInfo(\''
			+ row.id
			+ '\')"><i class="glyphicon glyphicon-list-alt"></i>详情</button>&nbsp;&nbsp;';
	<shiro:hasPermission name="News:delete">
	operator += '<button class="btn btn-danger btn-round btn-xs" onclick="deleteById(\''
			+ row.id
			+ '\')"><i class="glyphicon glyphicon-trash"></i>删除</button>&nbsp;&nbsp;';
	</shiro:hasPermission>
	if(row.statusId==1){
		operator += '<button class="btn btn-primary btn-round btn-xs" onclick="submitById(\''
				+ row.id
				+ '\')"><i class="glyphicon glyphicon-plus"></i>提交</button>&nbsp;&nbsp;';
	}else if (row.statusId==4){
		operator += '<button class="btn btn-primary btn-round btn-xs" onclick="submitById(\''
				+ row.id
				+ '\')"><i class="glyphicon glyphicon-plus"></i>重提交</button>&nbsp;&nbsp;';
	}

	return operator;
}


</script>
</head>
<body>
	<div>
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li><a href="#">首页</a></li>
				<li><a href="#">信息管理</a></li>
				<li><a href="#">新闻管理</a></li>
				<li><a href="#">新闻列表</a></li>
			</ul>
		</div>
		<div class="rightinfo">
			<div>
			 <input type="hidden" name="resource_path" id="resource_path" value="${resource_path }">
				<form id="searchForm" name="searchForm" method="post">
						<label>标题：</label><input type="text" name="newsName" class="txtSearch">&nbsp;
						<input type="button" class="btn btn-info btn-round" value="查询"
							onclick="queryList()">&nbsp;&nbsp; <input type="button"
							class="btn btn-warning btn-round" value="重置"
							onclick="$('#searchForm')[0].reset();">
				</form>
			</div>
			<div id="toolbar" class="btn-group">
				<shiro:hasPermission name="News">
					<button class="btn btn-primary btn-round" onclick="toSubmit()">
						<i class="glyphicon glyphicon-plus"></i>提交
					</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="News:delete">
					<button class="btn btn-danger btn-round" onclick="toRemove()">
						<i class="glyphicon glyphicon-trash"></i>删除
					</button>
				</shiro:hasPermission>
			</div>
			<table id="NewsList" data-toggle="table"
			data-url="${path}/NewsController/myList.do" data-pagination="true"
			data-side-pagination="server" data-cache="false"
			data-query-params="postQueryParams"
			data-page-list="[10, 15, 20, 30, 50,100]" data-method="post"
			data-show-refresh="true" data-show-toggle="true"
			data-show-columns="true" data-toolbar="#toolbar"
			data-click-to-select="true" data-single-select="false"
			data-striped="true"
			data-content-type="application/x-www-form-urlencoded">
			<thead>
				<tr>
					<th data-field="" data-checkbox="true"></th>
					<th data-field="newsName">标题</th>
					<th data-field="author">作者</th>
					<th data-field="location">所属实验室</th>
					<th data-field="importance" data-formatter=importanceFormatter>重要程度</th>
					<th data-field="keyWords">关键词</th>
					<th data-field="createTime" data-formatter="dateFormatter">发布时间</th>
					<th data-field="statusId" data-formatter="statusFormatter">状态</th>
					<th data-field="operator" data-formatter="operatorFormatter">操作</th>
				</tr>
			</thead>
		</table>
		</div>
	</div>
</body>
</html>