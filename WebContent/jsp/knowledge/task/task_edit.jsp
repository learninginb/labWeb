<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改任务</title>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<script type="text/javascript" src="${path }/js/dateTime.js"></script>
<style type="text/css">
	.forminfo li input[type="text"],.forminfo li input[type="file"]{
		width:350px;
	}
	.forminfo li #description{
		width: 550px; height:200px;
	}
	.forminfo li select{
		width:350px;
	}
</style>
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
				$('#duration>input').val(duration);
			}
			
		});
		$('#end').on('change',function(){
			if($('start').val()!=''&&$('#end').val()!=''){
				var endDate = new Date($('#end').val());
				var startDate = new Date($('#start').val());
				var duration = (endDate-startDate)/1000/60/60/24;
				$('#duration>input').val(duration);
			}
			
		});
		
		//监听任务时长的变化
		$('#duration>input').on('change',function(){
			if($('#duration>input').val()!=''){
				
				var duration = new Date($('#duration>input').val()*24*60*60*1000);
				
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
		
	})
	
	function clearForm(){
	     $('#start').val('');
	     $('#end').val('');
	     //用于解决清空后，前后日期还会关联的问题
	     $('.input-group-addon:has(span.glyphicon-remove)').click();
	 }
	
	//请求数据
	function requestData(){
		getAjax("${path}/TaskController/task.do",{id:${param.id}},(data)=>{
			var task = data['task'];
			console.log(task);
			$('#theme').val(task.theme);
			$('#type').val(task.type);
			$('#description').val(task.description);
			$('#pic').val(task.pic);
			$('#distributor').val(task.distributor);
			$('#executor').val(task.executor);
			$('#start').val(dateFormat(task.beginTime));
			$('#end').val(dateFormat(task.finishTime));
			if(task.type=="计划性任务")
			$('#duration>input').val((task.finishTime-task.beginTime)/1000/60/60/24);
			else{
				$('#duration').hide();
				$('#date').hide();
			}
		});
	}
	//提交数据
	function toSumbit(){
			$("#add_form").ajaxSubmit({
				url : "${path}/TaskController/task/"+${param.id}+".do",
				data : $("#add_form").serialize(),
				cache : false,
				dataType : 'json',
				type : 'post',
				success : function(res) {
					console.log(res);
					if(res.code==200){
						top.showArtDiaglog('提示', '修改成功', function() { //关闭事件
							window.location= "${path}/TaskController/toManageTask.do";
						}, function() { //确定事件
							window.location="${path}/TaskController/toManageTask.do";
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
	
	function goBackList(){
		window.location="${path}/TaskController/toManageTask.do";
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
				<li><a href="#">修改任务</a></li>
			</ul>
		</div>
		<div class="formbody">
			<div class="formtitle">
				<span>修改任务</span>
			</div>
			<form id="add_form" method="post" enctype ="multipart/form-data">
				<ul class="forminfo" >
					<li><label>任务主题：</label><input  id="theme" name="theme" type="text" class="form-control input-primary" readonly/></li>
					<li><label>重要程度：</label>
						<select class="form-control" id="importance" name="importance">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
						</select>
					</li>
					<li><label>类型：</label>
						<input id="type" name="type" type="text" class="form-control input-primary" readonly/>
					</li>
					<li><label>负责人：</label>
						<input id="pic" name="pic" type="text" class="form-control input-primary" readonly/>
					</li>
					<li><label>分配人：</label>
						<input id="distributor" name="distributor" type="text" class="form-control input-primary" readonly/>
					</li>
					<li><label>执行人：</label>
						<input id="executor" name="executor" type="text" class="form-control input-primary" readonly/>
					</li>
					<li id="date">
           				<div class="form-group">
               		 		<label for="dtp_input1" class="col-md-1 control-label">开始时间：</label>
                			<div id="startdiv" class="input-group date form_date col-md-3"  data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    		<input id="start" name="start" class="form-control" size="16" type="text" placeholder="请选择开始时间" value="" readonly>
                    		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    		<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                			</div>
                		<input type="hidden" id="dtp_input1" value="" /><br/>
            			</div>
           				<div class="form-group" id="start-container">
                			<label for="dtp_input2" class="col-md-1 control-label">结束时间：</label>
                			<div id="endDiv" class="input-group date form_date col-md-3"  data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    		<input id="end" name="end" class="form-control" size="16" type="text" placeholder="请选择结束时间" value="" readonly>
                    		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    		<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                		</div>
                		<input type="hidden" id="dtp_input2" value="" /><br/>
            			</div>
					</li>
					<li id="duration"><label>任务时长：</label>
						<input  value="" type="text" class="form-control input-primary" >
					</li>
					<li><label>文件：</label>
					    <input name="file_path" id="file_path" type="file" class="form-control input-primary" />
					</li>
					<li><label>任务描述：</label>
						<textarea name="description" id="description" 
						class="form-control input-primary" style="width: 550px; height:200px" >
						</textarea>
					</li>
					<li><label>&nbsp;</label>
						<input name="" type="button"class="btn btn-primary" value="确认修改" onclick="toSumbit()" />
						&ensp;
						<input type="button" class="btn btn-warning" value="返回列表" onclick="goBackList()" />
					</li>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>