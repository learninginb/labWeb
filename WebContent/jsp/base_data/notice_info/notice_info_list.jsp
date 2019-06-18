<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询列表</title>
<script>
	//添加
	function toAdd() {
		window.location = '${path}/noticeController/toAdd.do';
	}
	
	//查看
	function toInfo() {
		var selected = getSelectedRowsArr('NoticeInfoList');
		if (selected.length > 0 && selected.length < 2) {
			window.location = '${path}/noticeController/findById.do?id='
					+ selected;
		} else {
			top.showArtDiaglog('提示', '请选择一条数据进行操作', null, function() {
				top.closeDialog();
			});
		}
	}

	//设置查询参数
	function postQueryParams(params) {
		var queryParams = $("#searchForm").serializeObject();
		queryParams.limit = params.limit;
		queryParams.offset = params.offset;
		return queryParams;
	}
	//查询列表
	function queryList() {
		$('#NoticeInfoList').bootstrapTable('refresh');
	}

	
	
	//根据id查看
	function viewById(id) {
		window.location = '${path}/noticeController/findById.do?id=' + id;
	}

	//操作工具栏
	function operatorFormatter(value, row, index) {
		var operator = "";
		
		<shiro:hasPermission name="Notice:info">
		operator += '<button class="btn btn-success btn-round btn-xs" onclick="viewById(\''
				+ row.id
				+ '\')"><i class="glyphicon glyphicon-list-alt"></i>详情</button>&nbsp;&nbsp;';
		</shiro:hasPermission>
		
		return operator;
	}
	function typeFormatter(value, row, index) {
		if (value == '1') {
			return '<span class="label label-info label-lg">仿真通知</span>'
		} else if (value == '2') {
			return '<span class="label label-info  arrowed">管理员通知</span>';
		} else {
			return "-";
		}
	}
	

	function timeFormatter(value, row, index) {
		if (value == "") {
			return "-";
		} else {
			var datetime = new Date(value);
			var year = datetime.getFullYear();
			var month = datetime.getMonth() + 1 < 10 ? "0"
					+ (datetime.getMonth() + 1) : datetime.getMonth() + 1;
			var date = datetime.getDate() < 10 ? "0" + datetime.getDate()
					: datetime.getDate();
			var hour = datetime.getHours() < 10 ? "0" + datetime.getHours()
					: datetime.getHours();
			var minute = datetime.getMinutes() < 10 ? "0"
					+ datetime.getMinutes() : datetime.getMinutes();
			var second = datetime.getSeconds() < 10 ? "0"
					+ datetime.getSeconds() : datetime.getSeconds();
			return year + "-" + month + "-" + date + " " + hour + ":" + minute
					+ ":" + second;
		}
	}
</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li><a href="#">信息管理</a></li>
			<li><a href="#">通知管理</a></li>
			<li><a href="#">通知</a></li>
		</ul>
	</div>

	<div class="rightinfo">
		<div>
			<form id="searchForm" name="searchForm" method="post">
				<label>名称：</label><input type="text" name="name" class="txtSearch">&nbsp;
				<input type="button" class="btn btn-info btn-round" value="查询"
					onclick="queryList()">&nbsp;&nbsp; <input type="button"
					class="btn btn-warning btn-round" value="重置"
					onclick="$('#searchForm')[0].reset();">
			</form>
		</div>
		<div id="toolbar" class="btn-group">
			<shiro:hasPermission name="Notice:add">
				<button class="btn btn-info btn-round" onclick="toAdd();">
					<i class="glyphicon glyphicon-plus"></i>添加
				</button>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="Notice:info">
				<button class="btn btn-success btn-round" onclick="toInfo()">
					<i class="glyphicon glyphicon-list-alt"></i>详情
				</button>
			</shiro:hasPermission>
		</div>

		<table id="NoticeInfoList" data-toggle="table"
			data-url="${path}/noticeController/list.do"
			data-pagination="true" data-side-pagination="server"
			data-cache="false" data-query-params="postQueryParams"
			data-page-list="[10, 15, 20, 30, 50,100]" data-method="post"
			data-show-refresh="true" data-show-toggle="true"
			data-show-columns="true" data-toolbar="#toolbar"
			data-click-to-select="true" data-single-select="false"
			data-striped="true"
			data-content-type="application/x-www-form-urlencoded">
			<thead>
				<tr>
					<th data-field="" data-checkbox="true"></th>
					<th data-field="content">内容</th>
					<th data-field="type" data-formatter="typeFormatter">类型</th>
					<th data-field="create_time" data-formatter="timeFormatter">更新时间</th>
					<th data-field="operator" data-formatter="operatorFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>