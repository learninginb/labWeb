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
		window.location='${path}/sysRoleController/toAdd.do';
	}
	//删除
	function toRemove(){
		var ids=getSelectedRowsIds('SysRoleList');
		if(ids){
			top.showConfirmDiaglog('提示','删除数据不可恢复，确定要删除吗？',function(){  //关闭事件
				refleshData('SysRoleList');
			},function(){  //确认事件
				 $.post('${path}/sysRoleController/deleteById.do?ids='+ids,function(data){
					var json=$.parseJSON(data);
				    if(json.success){
				    	top.showArtDiaglog('提示','删除成功，角色被用户绑定的，将不删除',function(){  //关闭事件
				    		refleshData('SysRoleList');
	 					},function(){   //确定事件
	 						top.closeDialog();
	 					});
				    }else{
				    	top.showArtDiaglog('提示','删除失败，',function(){  //关闭事件
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
    	var selected=getSelectedRowsArr('SysRoleList');
    	if(selected.length>0&&selected.length<2){
    		window.location='${path}/sysRoleController/editById.do?id='+selected;
    	}else{
    		//提示信息
    		top.showArtDiaglog('提示','请选择一条数据进行操作',null,function(){
    			top.closeDialog();
			});
    	}
	}
	
    //查看
    function toInfo(){
    	var selected=getSelectedRowsArr('SysRoleList');
    	if(selected.length>0&&selected.length<2){
    		window.location='${path}/sysRoleController/findById.do?id='+selected;
    		
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
    	$('#SysRoleList').bootstrapTable('refresh');
    }
    
    function editById(id){
		window.location='${path}/sysRoleController/editById.do?id='+id;
	}

	//根据id删除
	function deleteById(id){
		top.showConfirmDiaglog('提示','删除数据不可恢复，确定要删除吗？',function(){  //关闭事件
				refleshData('SysRoleList');
			},function(){  //确认事件
				 $.post('${path}/sysRoleController/deleteById.do?ids='+id,function(data){
				   var json=$.parseJSON(data);
				   if(json.success){
					   top.showArtDiaglog('提示','删除成功，角色被用户绑定的，将不删除',function(){  //关闭事件
						   refleshData('SysRoleList');
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
			window.location='${path}/sysRoleController/findById.do?id='+id;
	}
	
    //操作工具栏
    function operatorFormatter(value, row, index) {
    	var operator="";
	    	<shiro:hasPermission name="SysRole:edit">
	    		operator+='<button class="btn btn-warning btn-round btn-xs" onclick="editById(\''+row.id+'\');"><i class="glyphicon glyphicon-pencil"></i> 修改</button>&nbsp;&nbsp;';
		    </shiro:hasPermission>
		    <shiro:hasPermission name="SysRole:info">
    			operator+='<button class="btn btn-success btn-round btn-xs" onclick="viewById(\''+row.id+'\')"><i class="glyphicon glyphicon-list-alt"></i>详情</button>&nbsp;&nbsp;';
	    	</shiro:hasPermission>
	    	<shiro:hasPermission name="SysRole:remove">
				operator+='<button class="btn btn-danger btn-round btn-xs" onclick="deleteById(\''+row.id+'\')"><i class="glyphicon glyphicon-trash"></i>删除</button>';
    		</shiro:hasPermission>
		return operator;
	}
    
    //角色状态格式化
    function statusFormatter(value,row,index){
    	if(value=='1'){
    		return '<span class="label label-success label-lg">启用</span>'
    	}else if(value=='2'){
    		return '<span class="label label-danger arrowed">禁用</span>';
    	}else{
    		return "";
    	}
    }
    
    //角色类型格式化
    function roleTypeFormatter(value ,row,index){
    	if(value=='1'){
    		return '超级管理员';
    	}else if(value=='2'){
    		return '管理员';
    	}else if(value=='3'){
    		return '发布方';
    	}else if(value=='4'){
    		return '普通会员';
    	}
    }

    //授权
    function toAuth(){
    	var selected=getSelectedRowsArr('SysRoleList');
    	if(selected.length>0&&selected.length<2){
    		var dialog = art.dialog.open("${path}/sysResourceController/toSelectTree.do?roleId="+selected,{
    	  		  id:"selectResourceDialog",
    	  		  title:"选择资源",
    	  		  width :'360px',
    	  		  height:'400px',
    	  		  lock:true,
    	  		  init: function (){
    		  		$(this.iframe).attr("scrolling","no");//去掉滚动条
    		  	  },
    	  		  close:function(){
    	  		  }
    	  	});
    	}else{
    		//提示信息
    		top.showArtDiaglog('提示','请选择一条数据进行操作',null,function(){
    			top.closeDialog();
			});
    	}
    } 
    
    function closeDialog(){
    	art.dialog.list["selectResourceDialog"].close();

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
    			<label>角色名称：</label><input type="text" name="roleName" class="txtSearch">&nbsp;
<!--     			<label>角色类型：</label> -->
<!-- 						<select name="roleType" id="roleType" class="form-control input-primary" style="width:150px"> -->
<!-- 							<option value="1">超级管理员</option> -->
<!-- 							<option value="2">管理员</option> -->
<!-- 							<option value="3" selected="selected">发布方</option> -->
<!-- 							<option value="4">普通会员</option> -->
<!-- 						</select> -->
    			<input type="button" class="btn btn-info btn-round" value="查询" onclick="queryList()">&nbsp;&nbsp;
    			<input type="button" class="btn btn-warning btn-round" value="重置" onclick="$('#searchForm')[0].reset();"> 
    		</form>
    	</div>
	    <div id="toolbar" class="btn-group">
	    	<shiro:hasPermission name="SysRole:add">
		    	<button class="btn btn-info btn-round" onclick="toAdd();">
					<i class="glyphicon glyphicon-plus"></i>添加
				</button>
		    </shiro:hasPermission>
	    	<shiro:hasPermission name="SysRole:edit">
	    		<button class="btn btn-warning btn-round" onclick="toEdit();">
					<i class="glyphicon glyphicon-pencil"></i> 修改
				</button>
	    	</shiro:hasPermission>
			<shiro:hasPermission name="SysRole:info">
				<button class="btn btn-success btn-round" onclick="toInfo()">
					<i class="glyphicon glyphicon-list-alt"></i>详情
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="SysRole:remove">
				<button class="btn btn-danger btn-round" onclick="toRemove()">
					<i class="glyphicon glyphicon-trash"></i>删除
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="SysRole:auth">
				<button class="btn btn-info btn-round" onclick="toAuth();">
					<i class="fa fa-cog fa-spin  fa-fw"></i> 授权
				</button>
			</shiro:hasPermission>
		</div>
    	<table id="SysRoleList" data-toggle="table"
			data-url="${path}/sysRoleController/list.do" data-pagination="true"
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
					<th data-field="roleName">角色名称</th>
					<th data-field="roleType" data-formatter="roleTypeFormatter">角色类型</th>
					<th data-field="roleStatus" data-formatter="statusFormatter" >状态</th>
					<th data-field="createTime" data-formatter="dateFormatter" >创建时间</th>
					<th data-field="modifyTime" data-formatter="dateFormatter" >修改时间</th>
					<th data-field="roleDesc">角色描述</th>
					<th data-field="operator" data-formatter="operatorFormatter">操作</th>
				</tr>
			</thead>
		</table>
    </div>
</body>
</html>