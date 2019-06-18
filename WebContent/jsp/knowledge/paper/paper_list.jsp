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
		window.location = '${path}/PaperController/toAdd.do';
	}
	//删除
	function toRemove() {
		var ids = getSelectedRowsIds('PaperList');
		if (ids) {
			top.showConfirmDiaglog('提示', '删除数据不可恢复，确定要删除吗？', function() { //关闭事件
			}, function() { //确认事件
				$.post('${path}/PaperController/deleteById.do?ids=' + ids,
						function(data) {
							var json = $.parseJSON(data);
							if (json.success) {
								queryList();
								top.showArtDiaglog('提示', '删除成功', function() { //关闭事件
								}, function() { //确定事件
									top.closeDialog();
								});
							} else {
								top.showArtDiaglog('提示', json.msg, function() { //关闭事件
								}, function() { //确定事件
									top.closeDialog();
								});
							}
						});
			});
		} else {
			top.showArtDiaglog('提示', '请选择一条数据进行操作', null, function() {
				top.closeDialog();
			});
		}
	}

	//编辑
	function toEdit() {
		var selected = getSelectedRowsArr('PaperList');
		if (selected.length > 0 && selected.length < 2) {
			window.location = '${path}/PaperController/editById.do?id='
					+ selected;
		} else {
			//提示信息
			top.showArtDiaglog('提示', '请选择一条数据进行操作', null, function() {
				top.closeDialog();
			});
		}
	}

	function toExport() {
		location.href = '${path}/PaperController/export.do';
	}

	//查看
	function toInfo() {
		var selected = getSelectedRowsArr('PaperList');
		if (selected.length > 0 && selected.length < 2) {
			window.location = '${path}/PaperController/findById.do?id='
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
		$('#PaperList').bootstrapTable('refresh');
	}

	function editById(id) {
		window.location = '${path}/PaperController/editById.do?id=' + id;
	}

	//根据id删除
	function deleteById(id) {
		top.showConfirmDiaglog('提示', '删除数据不可恢复，确定要删除吗？', function() { //关闭事件
			refleshData('PaperList');
		}, function() { //确认事件
			$.post('${path}/PaperController/deleteById.do?ids=' + id,
					function(data) {
						var json = $.parseJSON(data);
						if (json.success) {
							top.showArtDiaglog('提示', '删除成功', function() { //关闭事件
							}, function() { //确定事件
								top.closeDialog();
							});
						} else {
							top.showArtDiaglog('提示', json.msg, function() { //关闭事件
							}, function() { //确定事件
								top.closeDialog();
							});
						}
					});
		});
	}

	//根据id查看
	function viewById(id) {
		window.location = '${path}/PaperController/findById.do?id=' + id;
	}

	//操作工具栏
	function operatorFormatter(value, row, index) {
		var operator = "";
		<shiro:hasPermission name="Paper:edit">
		operator += '<button class="btn btn-warning btn-round btn-xs" onclick="editById(\''
				+ row.id
				+ '\');"><i class="glyphicon glyphicon-pencil"></i> 修改</button>&nbsp;&nbsp;';
		</shiro:hasPermission>
		<shiro:hasPermission name="Paper:info">
		operator += '<button class="btn btn-success btn-round btn-xs" onclick="viewById(\''
				+ row.id
				+ '\')"><i class="glyphicon glyphicon-list-alt"></i>详情</button>&nbsp;&nbsp;';
		</shiro:hasPermission>
		<shiro:hasPermission name="Paper:remove">
		operator += '<button class="btn btn-danger btn-round btn-xs" onclick="deleteById(\''
				+ row.id
				+ '\')"><i class="glyphicon glyphicon-trash"></i>删除</button>&nbsp;&nbsp;';
		</shiro:hasPermission>
		operator += '<button class="btn btn-success btn-round btn-xs" onclick="downLoadById(\''
				 + row.id
				 + '\')"><i class="glyphicon glyphicon-list-alt"></i>下载</button>';
		return operator;
	}
	
	function downLoadById(){
		var selected = getSelectedRowsArr('PaperList');
		if (selected.length > 0 && selected.length < 2) {
			window.location = '${path}/PaperController/downLoadById.do?id='
					+ selected;
		} else {
			//提示信息
			top.showArtDiaglog('提示', '请选择一条数据进行操作', null, function() {
				top.closeDialog();
			});
		}
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
	function statusFormatter(value, row, index) {
		if (value == '1') {
			return '<span class="label label-success label-lg">启用</span>'
		} else if (value == '0') {
			return '<span class="label label-danger arrowed">禁用</span>';
		} else {
			return "";
		}
	}
	 function imagePathFormatter(value,row,index){
	    	if(value!=""){
	    	return "<img width='60px' height='60px' border=0 src='${path }/"+$("#resource_path").val()+value+"'>";
	    	}
	    	return "<img width='60px' height='60px' border=0 src='${path }/images/no_image.png'>";
	    }

</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li><a href="#">系统管理</a></li>
			<li><a href="#">组态资源管理</a></li>
			<li><a href="#">组态资源</a></li>
		</ul>
	</div>

	<div class="rightinfo">
		<div>
		   <input type="hidden" name="resource_path" id="resource_path" value="${resource_path }">
			<form id="searchForm" name="searchForm" method="post">
				<label>名称：</label><input type="text" name="name" class="txtSearch">&nbsp;
				<input type="button" class="btn btn-info btn-round" value="查询"
					onclick="queryList()">&nbsp;&nbsp; <input type="button"
					class="btn btn-warning btn-round" value="重置"
					onclick="$('#searchForm')[0].reset();">
			</form>
		</div>
		<div id="toolbar" class="btn-group">
			<shiro:hasPermission name="Paper:add">
				<button class="btn btn-info btn-round" onclick="toAdd();">
					<i class="glyphicon glyphicon-plus"></i>添加
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="Paper:edit">
				<button class="btn btn-warning btn-round" onclick="toEdit();">
					<i class="glyphicon glyphicon-pencil"></i> 修改
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="Paper:info">
				<button class="btn btn-success btn-round" onclick="toInfo()">
					<i class="glyphicon glyphicon-list-alt"></i>详情
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="Paper:remove">
				<button class="btn btn-danger btn-round" onclick="toRemove()">
					<i class="glyphicon glyphicon-trash"></i>删除
				</button>
			</shiro:hasPermission>
		</div>
		<table id="PaperList" data-toggle="table"
			data-url="${path}/PaperController/list.do" data-pagination="true"
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
					<th data-field="name">名称</th>
					<th data-field="key_word">关键词</th>
					<th data-field="important" data-formatter="typeFormatter">重要程度</th>
					<th data-field="user_name">上传人</th>
					<th data-field="status" data-formatter="statusFormatter">状态</th>
					<th data-field="operator" data-formatter="operatorFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="display: none">
	  <select  id="typeList">
	    <c:forEach var="list" items="${list }">
	      <option value="${list.index }">${list.name }</option>
	    </c:forEach>
	  </select>
	</div>
</body>
</html>