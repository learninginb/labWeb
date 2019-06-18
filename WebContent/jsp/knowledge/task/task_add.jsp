<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加任务</title>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<script type="text/javascript" src="${path }/js/dateTime.js"></script>
<script type="text/javascript">
$(function(){
	requestData();
	   $('.form_date').datetimepicker({
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,   //今日日期按钮
			autoclose: 1,   //自动关闭
			todayHighlight: 1,   //高亮今日日期
			startView: 2,       //从日期视图开始
			minView: 2,
			forceParse: 0,
	 		startDate:new Date()
			
	    });
	   $('#startdiv').unbind("change");
	   $('#startdiv').change(function(){
	     $('#endDiv').datetimepicker('setStartDate', $("#start").val());
	   });
	   $('#endDiv').unbind("change");
	   $('#endDiv').change(function(){
	     $('#startdiv').datetimepicker('setEndDate', $("#end").val());
	   });
		
	//监听任务的类型变化
	$('#type').on("change",function(){
		console.log("类型变换了！");
		if($('#type').val()!="计划性任务"){
			$('#start-container').hide();
			$('#duration').hide();
		}
		else{
			$('#start-container').show();
			$('#duration').show();
		}
	});
	
	//监听开始时间和结束时间的变化
	$('#start').on('change',function(){
		if($('#end').val()!=''&&$('start').val()!=''){
			var endDate = new Date($('#end').val());
			var startDate = new Date($('#start').val());
			var duration = (endDate-startDate)/1000/60/60/24;
			$('#duration>input').val(duration+1);
		}
		
	});
	$('#end').on('change',function(){
		if($('start').val()!=''&&$('#end').val()!=''){
			var endDate = new Date($('#end').val());
			var startDate = new Date($('#start').val());
			var duration = (endDate-startDate)/1000/60/60/24;
			$('#duration>input').val(duration+1);
		}
		
	});
	//监听任务时长的变化
	$('#duration>input').on('change',function(){
		if($('#duration>input').val()!=''){
			
			var duration = new Date(($('#duration>input').val()-1)*24*60*60*1000);
			
			if($('#start').val()==''&&$('#end').val()!=''){
				var endTime = new Date($('#end').val());
				var start = dateFormat(endTime-duration);
				$('#start').val(start);
			}
			if($('#start').val()!=''){
				var startTime = new Date($('#start').val());
				console.log((startTime.getTime()+duration.getTime()));
				var end = dateFormat((startTime.getTime()+duration.getTime()));
				$('#end').val(end);
			}
		}
	});
	
});

function clearForm(){
	     $('#start').val('');
	     $('#end').val('');
	     //用于解决清空后，前后日期还会关联的问题
	     $('.input-group-addon:has(span.glyphicon-remove)').click();
	 }

//参数校验
function formValidate() {
	var name = $("#theme").val();
	var start = $('#start').val();
	var end = $('#end').val();
	if (name == "") {
		showArtDiaglog('提示', '请输入主题', null, function() {
			closeDialog();
		});
		return false;
	}
	if (start == "") {
		showArtDiaglog('提示', '请输入开始时间', null, function() {
			closeDialog();
		});
		return false;
	}
	if (end == ""&&$('#type').val()=="计划性任务") {
		showArtDiaglog('提示', '请输入截至时间', null, function() {
			closeDialog();
		});
		return false;
	}
	
	return true;
}

//请求数据
function requestData(){
	getAjax("${path}/TaskController/requestAddData.do",{},(data)=>{
		$('#distributor').val(data['distributor']);
		$(".selectpicker").html("");
		var list = data["list"];
		var selectOBJ1 = document.getElementById("picId");
		var selectOBJ2 = document.getElementById("executorId");
		for(var i=0;i<list.length;i++){
			selectOBJ1.add(new Option(list[i].userName,list[i].id));
			selectOBJ2.add(new Option(list[i].userName,list[i].id));
		}
		$('.selectpicker').selectpicker('refresh');
	    $('.selectpicker').selectpicker('render');
	  
	});
}

//提交新建
function toSumbit(){
	if(formValidate()){
		$("#add_form").ajaxSubmit({
			url : "${path}/TaskController/addTask.do",
			data : $("#add_form").serialize(),
			cache : false,
			dataType : 'json',
			type : 'post',
			success : function(res) {
				console.log(res);
				if(res.code==200){
					top.showArtDiaglog('提示', '新建成功', function() { //关闭事件
						window.location= "${path}/TaskController/toManageTask.do";
						$(".menuson li.active", parent.document).removeClass("active");
						$("a[href='/shiyanshi/TaskController/toManageTask.do']",parent.document).parent().addClass("active");
						
					}, function() { //确定事件
						window.location="${path}/TaskController/toManageTask.do";
						$(".menuson li.active", parent.document).removeClass("active");
						$("a[href='/shiyanshi/TaskController/toManageTask.do']",parent.document).parent().addClass("active");
					});
				}
				else if(res.code==400){
					console.log(res.msg);
				}
				else if(res.code==500){
					console.log(res.msg);
				}
			}
		});
	}
}



</script>
</head>
<body>
<div>
    <div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li><a href="#">首页</a></li>
				<li><a href="#">任务管理</a></li>
				<li><a href="#">新建任务</a></li>
			</ul>
	</div>
	<div class="formbody">
			<div class="formtitle">
				<span>新建任务</span>
			</div>
			<form id="add_form" method="post" enctype ="multipart/form-data">
				<ul class="forminfo" >
					<li><label>任务主题：</label><input  id="theme" name="theme" type="text" class="form-control input-primary" style="width: 350px" /></li>
					<li><label>重要程度：</label>
						<select class="form-control" style="width: 350px;" id="importance" name="importance">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
						</select></li>
					<li><label>类型：</label>
						<select id="type" name="type" class="form-control" style="width: 350px;">
							<option value="计划性任务">计划性任务</option>
							<option value="日周期任务">日周期任务</option>
							<option value="周周期任务">周周期任务</option>
							<option value="月周期任务">月周期任务</option>
						</select>
					</li>
					<li><label>负责人：</label>
						<select id="picId" name="picId" class="selectpicker "  data-live-search="true" >
							<option>负责人1</option>
							<option>负责人2</option>
							<option>负责人3</option>
							<option>负责人4</option>
						</select>
					</li>
					<li><label>分配人：</label>
						<input id="distributor" value="分配人" type="text" class="form-control input-primary" style="width: 220px;"  readonly="readonly">
					</li>
					<li><label>执行者：</label>
						<select id="executorId" name="executorId" class="selectpicker "  data-live-search="true" >
							<option>执行者1</option>
							<option>执行者2</option>
							<option>执行者3</option>
							<option>执行者4</option>
						</select>
						
					</li>
					<li>
           				<div class="form-group">
               		 		<label for="dtp_input1" class="col-md-1 control-label">开始时间</label>
                			<div id="startdiv" class="input-group date form_date col-md-3"  data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    		<input id="start" name="start" class="form-control" size="16" type="text" placeholder="请选择开始时间" value="" readonly>
                    		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    		<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                			</div>
                		<input type="hidden" id="dtp_input1" value="" /><br/>
            			</div>
           				<div class="form-group" id="start-container">
                			<label for="dtp_input2" class="col-md-1 control-label">结束时间</label>
                			<div id="endDiv" class="input-group date form_date col-md-3"  data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    		<input id="end" name="end" class="form-control" size="16" type="text" placeholder="请选择结束时间" value="" readonly>
                    		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    		<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                		</div>
                		<input type="hidden" id="dtp_input2" value="" /><br/>
            			</div>
					</li>
					<li id="duration"><label>任务时长：</label>
						<input  value="" type="text" class="form-control input-primary" style="width: 220px;">
					</li>
					<li><label>文件：</label>
					    <input name="file_path" id="file_path" type="file" class="form-control input-primary" style="width:350px"/>
					</li>
					<li><label>任务描述：</label>
						<textarea name="description" id="description" type="text"
						class="form-control input-primary" style="width: 550px; height:200px" >
						</textarea>
					</li>
					<li><label>&nbsp;</label><input name="" type="button"
						class="btn btn-primary" value="确认保存" onclick="toSumbit()" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
				</ul>
			</form>
	</div>
</div>
</body>
</html>