<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow:hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实验室管理平台</title>
<link href="<%=request.getContextPath()%>/css/layout/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${path }/jslib/table/docs/assets/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/jslib/table/docs/assets/js/jquery.min.js"></script>
<script src="${path}/jslib/artDialog/jquery.artDialog.js?skin=blue"></script>   
<script src="${path}/jslib/artDialog/plugins/iframeTools.js"></script>  
<script src="${path}/js/simulation/artDialogExt.js"></script>
<script src="${path }/jslib/table/docs/assets/bootstrap/js/bootstrap.min.js"></script> 
<!-- 时间转换js -->
<script type="text/javascript" src="${path }/js/dateTime.js"></script>
<!-- ajax.js -->
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<!-- websocket -->
<script type="text/javascript" src="${path }/js/layout/websocket/websocket_main_message.js"></script>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico">

<style type="text/css">
	.modal-content{
		background-image:url(../images/layout/righttop.gif);
	}
	.modal-header{
		padding:5px;
	}
	.modal-body{
		margin-top:0px;
		margin-bottom:0px;
		margin-right:5px;
		margin-left:5px;
		background:white;		
	}
	.modal-footer{
		padding:5px;
	}
	
	#taskInfo li{
		margin-top:5px;	
	}
</style>
<script type="text/javascript">
$(function(){	
	Chat.initialize();
	 layoutrezise();
     AutoHeight();
     getMessage();
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected");
		$(this).addClass("selected");
	});	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active");
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
	
	var firstSys= $('#top .nav li').eq(0).find('a').trigger('click');
	
	
});	

//展示任务详情弹窗
function showModal(obj){
	 //获取子任务
	getAjax("${path}/TaskController/getSonTask.do",{parentId:obj.id},(data)=>{
		$('#sonlist').html('');
		var sonlist = data['sonlist'];
		for(var i=0;i<sonlist.length;i++){
			$('#sonlist').append("<li id='"+sonlist[i].id+"' class='list-group-item' onclick='taskInfo(this)'>"+sonlist[i].theme+"</li>")
		}
	}); 
	$('.modal-header a').attr('id',obj.parentId);
	$('#theme').val(obj.theme);
	$("#description").val(obj.description);
	$('#myModalLabel').html(obj.type+":详情");
	$('#pic').val(obj.pic);
	$('#distributor').val(obj.distributor);
	$('#executor').val(obj.executor);
	$('#beginTime').val(dateFormat(obj.beginTime));
	$('#finishTime').val(dateFormat(obj.finishTime));
	$('#status').val(obj.status);
	$('#myModal').modal('show');
	$('#file').html(obj.fileUrl);
	$("#myAlert").hide();
	
}

//查看审核详情
function showReview(obj){
	console.log(obj);
	$('#review_theme').val(obj.taskTheme);
	$('#review_reviewer').val(obj.distributor);
	$('#review_description').val(obj.description);
	$('#review_applyer').val(obj.executor);
	$('#review_createTime').val(datetimeFormat(obj.createTime));
	$('#review_file').html(obj.fileUrl);
	
	$('#reviewModal').modal('show');
}

//下载文件
function downloadfile(obj){
	window.location = '${path}/TaskController/downLoadById.do?fileUrl='
		+ $(obj).html();
}

//任务详情
function taskInfo(obj){
	if(obj.id==0){
		$("#myAlert").show();
		return ;
	}
	getAjax("${path}/TaskController/task.do",{id:obj.id},(data)=>{
		console.log(data['task']);
		showModal(data['task']);
	},(data)=>{
		$("#myAlert").show();
	});
}



function layoutrezise() {
    var headerH = $("div#top").height();
    var footerH = $("div#footer").height();
    var bodyerH = $(window).height() - headerH - footerH;
    $("div#middle,div#middle-left,div#middle-right,iframe#rightFrame").height(bodyerH);
    $("div#middle-right").width($(window).width() - $("div#middle-left").width());
}
function AutoHeight() {
    $(window).resize(function () {
        layoutrezise();
    })
} 
function changeList(menu){
	var text= $('#top .nav li').find('.selected').attr("id");
	var all=$('.leftmenu dd').hide();
	var currentModule=$('[class^='+menu+'_]').show();
	currentModule.eq(0).find('.title').trigger('click');
}
//下列为公共方法
function showArtDiaglog(title,msg,closeFun,okFunction){
	art.dialog({
		id:'artDialogTop',
		 lock:true,
		title:title,
	    content: msg,
	    ok: okFunction,
	    close:closeFun
	});
}

//关闭窗口
function closeDialog(){
	art.dialog.list["artDialogTop"].close();
}

//弹出确认窗口
function showConfirmDiaglog(title,msg,closeFun,okFunction){
	art.dialog({
		id:'artDialogTop',
		 lock:true,
		title:title,
	    content: msg,
	    cancelVal: '取消',
	    cancel: true,
	    ok: okFunction,
	    close:closeFun
	});
}
//查看消息
function toMessage(){
	changeList('sys_102');
	$('.sys_102_309>.title').trigger("click");
	$(".sys_102_309>ul>li>a[href='/shiyanshi/MessageController/toList.do']").parent().trigger("click");
	document.getElementById("rightFrame").src="/shiyanshi/MessageController/toList.do";
	/* var $ul = $('.sys_102_309>.title').next('ul');
	$('dd').find('ul').slideUp();
	if($ul.is(':visible')){ */
}

function modalClick(title,sontitle){
	
	 switch(title){
	case "TaskController":
		$('.sys_102_410>.title').trigger("click");$(".sys_102_410>ul>li>a[href='"+sontitle+"']").parent().trigger("click");break;
	case "PaperController":
		$('.sys_102_404>.title').trigger("click");$(".sys_102_404>ul>li>a[href='"+sontitle+"']").parent().trigger("click");break;
	} 
	 
}
//更新未读消息数
function getMessage(){
	getAjax("${path}/MessageController/messageCount.do",{},(data)=>{
		console.log(data);
		$('.user>a>b').html(data['count']);
	})
}
</script>
</head>
	<body>
	<!-- 任务详情modal组件 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   	 	<div class="modal-dialog">
        	<div class="modal-content">
            	<div class="modal-header">
            		<a href="#" onclick="taskInfo(this)" id=""><i class="glyphicon glyphicon-backward"></i></a>
                	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                	<h4 class="modal-title" id="myModalLabel" style="text-align:center;">计划性任务:详情</h4>
            	</div>
            	 <div class="modal-body" style="border:1px solid grey">
            	 	<ul id="taskInfo">
            	 		<li>
            	 			<div id="myAlert" class="alert alert-warning" style="display:none;">
    							<a href="#" class="close" onclick="$('#myAlert').hide()">&times;</a>
   						 		<strong>警告！</strong>无父级任务了。
							</div>
            	 		</li>
            	 		<li class="form-inline"><label>任务名称：</label><input class="form-control" id="theme" name="theme" style="width:496px;background-color: white;"
            	 		 value="任务主题" type="button" readonly="readonly"/>
            	 		</li>
            	 		<li class="form-inline" ><label>任务描述：</label>
            	 			<textarea id="description" rows="3" class="form-control" style="width:490px;background-color: white;" readonly="readonly">一。飞机设计的法律就是打开链接而我却撇颇为i去觉得芬兰首都基辅哦未批切片【奇瑞却日u为哦入围偶肉i五欸群殴撇哦
            	 			二、而温柔i俄乌坡i认识空间的犯了什么呢
            	 			三、JFK的理解微软颇为就
            	 			四、付款了对方Joe我i人</textarea>
            	 		</li>
            	 		<li class="form-inline"><label>责任人：&emsp;</label><input class="form-control" id="pic" name="pic" style="width:200px;background-color: white;"
	            	 		 value="责任人" type="button" readonly="readonly"/>
	            	 		 <label>分配人：&emsp;</label>
	            	 		 <input class="form-control" id="distributor" name="distributor" style="width:200px;background-color: white;"
	            	 		 value="分配人" type="button" readonly="readonly"/>
	            	 		 </li>
            	 		<li class="form-inline"><label>开始时间：</label><input class="form-control" id="beginTime" name="beginTime" style="width:200px;background-color: white;"
	            	 		 value="开始时间" type="button" readonly="readonly"/>
	            	 		 <label>结束时间：</label>
	            	 		 <input class="form-control" id="finishTime" name="finishTime" style="width:200px;background-color: white;"
	            	 		 value="结束时间" type="button" readonly="readonly"/>
            	 		</li>
            	 		<li class="form-inline"><label>执行者：&emsp;</label><input class="form-control" id="executor" name="executor" style="width:200px;background-color: white;"
	            	 		 value="执行者" type="button" readonly="readonly"/>
	            	 		 <label>任务状态：</label>
	            	 		 <input class="form-control" id="status" name="status" style="width:200px;background-color: white;border: none;box-shadow: none;font-family:FZShuTi;font-weight:bold;color:#00F;"
	            	 		 value="已完成" type="button" readonly="readonly"/>
            	 		</li>
            	 		<li class="form-inline" style="height:34px; display: flex;align-items: center;"><label>任务附件：</label>
            	 			<!-- <input class="form-control" id="file" name="file" style="width:490px;background-color: white;"
            	 		 value="任务附件.pdf" type="button" readonly="readonly"/> -->
            	 		  <a href='#' id="file" name="file" style="font-weight:bold;color:#00F;" onclick="downloadfile(this)"></a>
            	 		</li>
            	 		<li class="form-inline" style="height:160px;width:100%;"><label style="float:left;">子任务：&emsp;</label>
            	 			<div class="row pre-scrollable" style="height:150px;width:490px;background-color: white;">
            	 				<ul id="sonlist" class="list-group">	
            	 				</ul>
            	 			</div>
            	 		</li>
            	 	</ul>
            	 </div>
            	<div class="modal-footer">
                	<button type="button" class="btn btn-default" data-dismiss="modal" style="display:block;margin:0 auto"><i class="glyphicon glyphicon-off"></i>关闭</button>
            	</div>
        	</div>
    	</div>
	</div>
	
	<!-- 审核详情 -->
	<div class="modal fade" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   	 	<div class="modal-dialog">
        	<div class="modal-content">
            	<div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                	<h4 class="modal-title" id="myModalLabel" style="text-align:center;">审核表</h4>
            	</div>
            	 <div class="modal-body" style="border:1px solid grey">
            	 	<ul id="taskInfo">
            	 		<li class="form-inline"><label>任务名称：</label><input class="form-control" id="review_theme" name="review_theme" style="width:496px;background-color: white;"
            	 		 value="任务主题" type="button" readonly="readonly"/>
            	 		</li>
            	 		<li class="form-inline" ><label>审核描述：</label>
            	 			<textarea id="review_description" rows="4" class="form-control" style="width:490px;background-color: white;" readonly="readonly">一。飞机设计的法律就是打开链接而我却撇颇为i去觉得芬兰首都基辅哦未批切片【奇瑞却日u为哦入围偶肉i五欸群殴撇哦
            	 			二、而温柔i俄乌坡i认识空间的犯了什么呢
            	 			三、JFK的理解微软颇为就
            	 			四、付款了对方Joe我i人</textarea>
            	 		</li>
            	 		<li class="form-inline"><label>审核人：&emsp;</label><input class="form-control" id="review_reviewer" name="review_reviewer" style="width:200px;background-color: white;"
	            	 		 value="责任人" type="button" readonly="readonly"/>
	            	 		 <label>申请人：&emsp;</label>
	            	 		 <input class="form-control" id="review_applyer" name="review_applyer" style="width:200px;background-color: white;"
	            	 		 value="分配人" type="button" readonly="readonly"/>
	            	 		 </li>
            	 		<li class="form-inline"><label>申请时间：</label><input class="form-control" id=review_createTime name="review_createTime" style="width:200px;background-color: white;"
	            	 		 value="申请时间" type="button" readonly="readonly"/>
	            	 		  <label>任务状态：</label>
	            	 		 <input class="form-control" id="review_status" name="review_status" style="width:200px;background-color: white;border: none;box-shadow: none;font-family:FZShuTi;font-weight:bold;color:#00F;"
	            	 		 value="审核中" type="button" readonly="readonly"/>
            	 		</li>
            	 		<li class="form-inline" style="height:34px; display: flex;align-items: center;"><label>任务附件：</label>
            	 			
            	 		  <a href='#' id="review_file" name="review_file" style="font-weight:bold;color:#00F;" onclick="downloadfile(this)"></a>
            	 		</li>
            	 	</ul>
            	 </div>
            	<div class="modal-footer">
                	<button type="button" class="btn btn-default" data-dismiss="modal" style="display:block;margin:0 auto"><i class="glyphicon glyphicon-off"></i>关闭</button>
            	</div>
        	</div>
    	</div>
	</div>
	
	<div id="top" style="background:url(../images/layout/topbg.gif) repeat-x;height: 88px;width: 100%">
		<div class="topleft" >
	    	<a href="pass.do" target="_parent"><img src="../images/layout/logo.png" title="实验室管理平台" /></a>
	    </div>
    <ul class="nav">

    	
    	<c:forEach var="res" items="${resList }" varStatus="status">
    		<c:if test="${res.level==1 }">
    			<li><a href="javascript:void(0)" id="sys_${res.id }" onclick="changeList('sys_${res.id }')" ><img src="../images/layout/icon0${status.index+1 }.png" title="模块设计" style="margin:0px;"/><h2 style="margin:0px;">${res.resourceName }</h2></a></li>
    			
    		</c:if>
    	
    	</c:forEach>
    
    </ul>
    <div class="topright">    
	    <ul>
		    <li><span><img src="../images/layout/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li>
		    <li><a href="#">关于</a></li>
		    <li><a href="#" onclick="javascript:window.location='${path}/logout.do'" target="_parent">退出</a></li>
	    </ul>
		<div class="user">
			<span>${user.userName }</span>
			<i>消息</i>
			<a href="#" onclick="toMessage()"><b>5</b></a>
		</div>    
    </div>
	<!-- topEnd -->
	</div>
	<div id="middle" style="width: 100%;height: 100%;">
		<div id="middle-left" style="width: 187px;height: 100%;float: left;background:#f0f9fd;">
			<div class="lefttop"><span></span>菜单</div>
    		<dl class="leftmenu">
    			<c:forEach  var="res" items="${resList }" varStatus="status">
    				<c:if test="${res.level==2 }">
    					<dd style="display: none;" class="sys_${res.parentId }_${res.id}">
					    <div class="title">
					    	<span><img src="../images/layout/leftico01.png" /></span>${res.resourceName }
	   					</div>
	    				<ul class="menuson" style="display: none">
	    					<c:forEach var="r" items="${resList }">
	    						<c:if test="${r.level==3 && res.id==r.parentId }">
	    							<li><cite></cite><a href="${path}/${r.resourcePath}" target="rightFrame">${r.resourceName }</a><i></i></li>
	    						</c:if>
	    					</c:forEach>
	       				</ul>    
    				</dd>
    				</c:if>
    			</c:forEach>
    			<dd style="display: none;" class="sys_12_id">
				   <div class="title">
				   <span><img src="../images/layout/leftico02.png" /></span>其他设置
				   </div>
						<ul class="menuson" style="display: none">
					        <li><cite></cite><a href="#">编辑内容</a><i></i></li>
					        <li><cite></cite><a href="#">发布信息</a><i></i></li>
					        <li><cite></cite><a href="#">档案列表显示</a><i></i></li>
						</ul>     
				 </dd> 
   		 		 <dd style="display: none;" class="sys_12_id"><div class="title"><span><img src="../images/layout/leftico03.png" /></span>编辑器</div>
					<ul class="menuson" style="display: none">
					    <li><cite></cite><a href="#">自定义</a><i></i></li>
					    <li><cite></cite><a href="#">常用资料</a><i></i></li>
					    <li><cite></cite><a href="#">信息列表</a><i></i></li>
					    <li><cite></cite><a href="#">其他</a><i></i></li>
					</ul>    
    			 </dd>  
				    <dd style="display: none;" class="sys_13_id"><div class="title"><span><img src="../images/layout/leftico04.png" /></span>日期管理</div>
					   <ul class="menuson" style="display: none">
					       <li><cite></cite><a href="#">自定义</a><i></i></li>
					       <li><cite></cite><a href="#">常用资料</a><i></i></li>
					       <li><cite></cite><a href="#">信息列表</a><i></i></li>
					       <li><cite></cite><a href="#">其他</a><i></i></li>
					   </ul>
				    </dd>   
    				<dd style="display: none;" class="sys_14_id">
					    <div class="title">
					    	<span><img src="../images/layout/leftico02.png" /></span>其他设置
					    </div>
					    <ul class="menuson" style="display: none">
					        <li><cite></cite><a href="#">编辑内容</a><i></i></li>
					        <li><cite></cite><a href="#">发布信息</a><i></i></li>
					        <li><cite></cite><a href="#">档案列表显示</a><i></i></li>
					    </ul>     
					</dd>
				</dl>
		</div>
		<div id="middle-right" style="float: left;" >
			 <iframe id="rightFrame" name="rightFrame" style="width:100%; border:0;" src="<%=request.getContextPath()%>/workIndex.do"></iframe>
		</div>
		 <div id="clear" style="clear: both;"></div> 
	</div>
	<div id="footer" style="background-color:#eee">
	     <center><a href="#" >&copy; 2017 Xu Qi. All Rights Reserved.</a></center>
	</div>  

</body>
</html>