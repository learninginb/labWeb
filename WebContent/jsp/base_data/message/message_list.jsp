<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息列表</title>
<script type="text/javascript" src="${path }/js/dateTime.js"></script>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<script type="text/javascript">
	function statusFormatter(value, row, index){
		if(value==0){
			return "<button class='btn btn-danger btn-round btn-xs' ><i class='glyphicon glyphicon-remove-sign'></i>未阅</button>"
		}else if(value==1){
			return "<button class='btn btn-success btn-round btn-xs' ><i class='glyphicon glyphicon-ok-sign'></i>已阅</button>"
		}
	}
	
	function contentFormatter(value,row,index){
		var url =row.messageUrl;
		return '<a href="#" onclick="toView(\''+url+'\',\''+row.id+'\')"><b>'+value+'</b></a>';
	}
	
	function timeFormatter(value, row, index){
		return datetimeFormat(value);
	}
	
	//设置查询参数
	function postQueryParams(params) {
		var queryParams = {};
		queryParams.limit = params.limit;
		queryParams.offset = params.offset;
		return queryParams;
	}
	//查询列表
	function queryList() {
		$('#MessageList').bootstrapTable('refresh');
	}
	
	//跳转查看详情
	function toView(url,id){
		console.log(id);
		postAjax("${path}/MessageController/message/"+id+".do",{},(data)=>{
			console.log("success");
		})
		var urllist = url.split("/")
		window.parent.modalClick(urllist[urllist.length-2],url);
		window.location = url;
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
			<li><a href="#">消息</a></li>
		</ul>
	</div>
	<div class="rightinfo">
		<table id="MessageList" data-toggle="table"
			data-url="${path }/MessageController/list.do"
			data-pagination="true" data-side-pagination="server"
			data-cache="false" data-query-params="postQueryParams"
			data-page-list="[10, 15, 20, 30, 50,100]" data-method="post"
			data-show-refresh="true" data-show-toggle="true"
			data-show-columns="true" data-toolbar="#toolbar" data-unique-id="id"
			data-click-to-select="true" data-single-select="true"
			data-striped="true"
			data-content-type="application/x-www-form-urlencoded">
			<thead>
				<tr>
					<th data-field="content" data-formatter="contentFormatter">内容</th>
					<th data-field="type" >类型</th>
					<th data-field="createTime" data-formatter="timeFormatter">更新时间</th>
					<th data-field="isRead" data-formatter="statusFormatter">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>