<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询列表</title>
<script>
	//设置类型
	function toSet(type){
		var ids=getSelectedRowsIds('SysReleaseUserList');
		if(ids){
			var url='${path}/sysReleaseUserController/setWhite.do?ids='+ids;
			if(type=='2'){
				url='${path}/sysReleaseUserController/setLimited.do?ids='+ids;
			}
			 $.post(url,function(data){
					var json=$.parseJSON(data);
				    if(json.success){
					   top.showArtDiaglog('提示','设置成功',function(){  //关闭事件
						   refleshData('SysReleaseUserList');
	 					},function(){   //确定事件
	 						top.closeDialog();
	 					});
				    }else{
					   top.showArtDiaglog('提示','设置成功',function(){  //关闭事件
						   refleshData('SysReleaseUserList');
	 					},function(){   //确定事件
	 						top.closeDialog();
	 					});
				    }
			});
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
    	$('#SysReleaseUserList').bootstrapTable('refresh');
    }
    
  
    
    function typeFormatter(value,row,index){
    	if(value==1){
    		return '普通发布方'
    	}else if(value==2){
    		return '高级发布方';
    	}else if(value=='3'){
    		return '无权限发布方';
    	}
    }
    
    function userNameFormatter(value,row,index){
    	if(row&&row.sysUser){
    		return row.sysUser.userName;
    	}
    }
    
    function emailFormatter(value,row,index){
    	if(row&&row.sysUser){
    		return row.sysUser.email;
    	}
    }
    
    
    function accountFormatter(value,row,index){
    	if(row&&row.sysUser){
    		return row.sysUser.account;
    	}
    }
    
    
    function statusFormatter(value,row,index){
    	if(row&&row.sysUser){
    		var val=row.sysUser.status;
    		if(val=='1'){
        		return '<span class="label label-success label-lg">启用</span>';
        	}else if(val=='2'){
        		return '<span class="label label-danger arrowed">锁定</span>';
        	}else{
        		return "";
        	}
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
    			<input type="text" name="columnName" class="txtSearch">&nbsp;
    			<input type="hidden" name="releaseType" value="1">
    			<input type="button" class="btn btn-info btn-round" value="查询" onclick="queryList()">&nbsp;&nbsp;
    			<input type="button" class="btn btn-warning btn-round" value="重置" onclick="$('#searchForm')[0].reset();"> 
    		</form>
    	</div>
	    <div id="toolbar" class="btn-group">
			<shiro:hasPermission name="SysReleaseUser2:setWhite">
	    		<button class="btn btn-warning btn-round" onclick="toSet();">
					<i class="glyphicon glyphicon-pencil"></i>设置为高级发布方
				</button>
	    	</shiro:hasPermission>
			<shiro:hasPermission name="SysReleaseUser2:setLimited">
				<button class="btn btn-warning btn-round" onclick="toSet('2')">
					<i class="glyphicon glyphicon-pencil"></i>设置为无权限发布方
				</button>
			</shiro:hasPermission>
		</div>
    
    	<table id="SysReleaseUserList" data-toggle="table"
			data-url="${path}/sysReleaseUserController/list.do" data-pagination="true"
			data-side-pagination="server" data-cache="false" data-query-params="postQueryParams"
			data-page-list="[10, 15, 20, 30, 50,100]" data-method="post"
			data-show-refresh="true" data-show-toggle="true"
			data-show-columns="true" data-toolbar="#toolbar"
			data-click-to-select="true" data-single-select="false"
			data-striped="true" data-content-type="application/x-www-form-urlencoded">
			<thead>
				<tr>
					<th data-field="" data-checkbox="true"></th>
				    <th data-field="sysUser.userName" data-formatter="userNameFormatter">发布方名称</th>
				    <th data-field="sysUser.account" data-formatter="accountFormatter">登录账号账号</th>
				    <th data-field="sysUser.email" data-formatter="emailFormatter">电子邮件</th>
					<th data-field="releaseType" data-formatter="typeFormatter">发布方类型</th>
					<th data-field="qq">QQ号码</th>
					<th data-field="contactsUser">联系人</th>
					<th data-field="contactsPhone">联系手机号码</th>
				</tr>
			</thead>
		</table>
    </div>
</body>
</html>