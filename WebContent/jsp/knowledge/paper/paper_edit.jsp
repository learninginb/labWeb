<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑页面</title>
<style type="text/css">
#preview{width:120px;height:120px;border:1px solid #eb4f38;overflow:hidden;}
#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
</style>
<script type="text/javascript">
	$(function() {
		
	});
	
	function toSubmit(){
		//表单验证
		if(!formValidate()){
			return;
		}
		//表单提交
		$("#submit_form").ajaxSubmit({
			url:"${path}/PaperController/editSubmit.do",
			data : $("#submit_form").serialize(),
			cache : false,
			dataType : 'JSON',
			type:'post',
			success:function(data){
				var result;
				if(data.success==undefined){
					result = jQuery.parseJSON(data);
				}else{
					result=data;
				}
				if (result.success) {
					top.showArtDiaglog('提示', '修改成功', function() { //关闭事件
						goBackList();
					}, function() { //确定事件
						top.closeDialog();
					});
				} else {
					top.showArtDiaglog('提示', result.msg, null, function() {
						top.closeDialog();
					});
				}
			}
		});
	}
	
	function formValidate(){
		var name = $("#name").val();
		var type=$("#type").val();
		if (name == "") {
			showArtDiaglog('提示', '请输入名称', null, function() {
				closeDialog();
			});
			return false;
		}
		if (type == "") {
			showArtDiaglog('提示', '请选择类型', null, function() {
				closeDialog();
			});
			return false;
		}
		return true;
	}
	
	//返回列表
	function goBackList(){
		window.location="${path}/PaperController/toList.do";
	}
</script>
<script type="text/javascript">
      //图片上传预览    IE是用了滤镜。
        function previewImage(file)
        {
          var MAXWIDTH  = 120; 
          var MAXHEIGHT = 120;
          var div = document.getElementById('preview');
          if (file.files && file.files[0])
          {
              div.innerHTML ='<img id=imghead>';
              var img = document.getElementById('imghead');
              img.onload = function(){
                var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                img.width  =  rect.width;
                img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
                img.style.marginTop = rect.top+'px';
              }
              var reader = new FileReader();
              reader.onload = function(evt){img.src = evt.target.result;}
              reader.readAsDataURL(file.files[0]);
          }
          else //兼容IE
          {
            var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
            file.select();
            var src = document.selection.createRange().text;
            div.innerHTML = '<img id=imghead>';
            var img = document.getElementById('imghead');
            img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
            var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
            status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
            div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
          }
        }
        function clacImgZoomParam( maxWidth, maxHeight, width, height ){
            var param = {top:0, left:0, width:width, height:height};
            if( width>maxWidth || height>maxHeight )
            {
                rateWidth = width / maxWidth;
                rateHeight = height / maxHeight;
                 
                if( rateWidth > rateHeight )
                {
                    param.width =  maxWidth;
                    param.height = Math.round(height / rateWidth);
                }else
                {
                    param.width = Math.round(width / rateHeight);
                    param.height = maxHeight;
                }
            }
            param.left = Math.round((maxWidth - param.width) / 2);
            param.top = Math.round((maxHeight - param.height) / 2);
            return param;
        }
</script>
</head>
<body>
	<div>
		<div class="place">
		    <span>位置：</span>
		    <ul class="placeul">
			   <li><a href="#">首页</a></li>
				<li><a href="#">系统管理</a></li>
				<li><a href="#">组态资源管理</a></li>
				<li><a href="#">组态资源</a></li>
		    </ul>
	    </div>
		<div class="formbody">
   			<div class="formtitle"><span>文献修改</span></div>
   			<form id="submit_form" method="post"  enctype ="multipart/form-data">
   				<input type="hidden" name="id" value="${vo.id}"/>
				<ul class="forminfo">
				   
					<li><label>名称：</label><input name="name" id="name" type="text"
						class="form-control input-primary" style="width: 350px" value="${vo.name }"/></li>
					<li><label>重要程度：</label> <select class="form-control"
						style="width: 350px;" id="important" name="important" value="${vo.important} ">
							
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							
					</select></li>
					<li><label>关键词（用逗号分隔）：</label> 
					<input name="key_word" id="key_word" type="text"
						class="form-control input-primary" style="width: 550px" value="${vo.key_word}"/>
					</li>
					<li><label>摘要：</label> 
					<textarea name="introduction" id="introduction" type="text"
						class="form-control input-primary" style="width: 550px; height:200px" value="${vo.introduction}" >
						</textarea>
					</li>
					<li><label>状态：</label>
						<input type="radio" value="1" name="status" id="status" <c:if test="${vo.status==1 }">checked="checked"</c:if> >
						启用    
						<input type="radio" value="0" name="status" id="status"  <c:if test="${vo.status==0 }">checked="checked"</c:if> >
						禁用
					</li>
					<li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" value="确认保存" onclick="toSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn btn-warning" value="返回列表" onclick="goBackList();"/></li>
	    		</ul>
    		</form>
	    </div>
	</div>
</body>
</html>