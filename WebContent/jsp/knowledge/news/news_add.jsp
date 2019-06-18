<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻-添加</title>
<script charset="utf-8" src="${path }/jslib/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="${path }/jslib/kindeditor/lang/zh-CN.js"></script>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<script>
        KindEditor.ready(function(K) {
                window.editor = K.create('#content',{
                	filterMode : true,
                	filePostName: "uploadFile",
                	uploadJson: '${path}/jslib/kindeditor/jsp/upload_json.jsp',
                    resizeType: 1,
                    allowPreviewEmoticons: true,
                    allowImageUpload: true,
                    afterBlur: function(){this.sync();}
                });
        });
        
        function formValidate(){
        	var newsName =$("#newsName").val();
        	var image = $("#image").val();
        	var importance = $("#importance");
        	var keyWords = $("#keyWords").val();
        	var content = $("#content").val();
    		console.log(content);
        	content = content.replace(/^\s+|\s+$/g,'');
        	if(newsName == ""){
        		showArtDiaglog('提示', '请输入新闻标题', null, function() {
    				closeDialog();
    			});
    			return false;
        	}
        	if(content==""){
        		showArtDiaglog('提示', '新闻内容不能为空', null, function() {
    				closeDialog();
    			});
    			return false;
        	}
        	return true;
        }
        
        function toSubmit(){
        	if(formValidate()){
        		$("#add_form").ajaxSubmit({
        			url:"${path}/NewsController/news.do",
        			data : $("#add_form").serialize(),
        			cache : false,
        			dataType : 'json',
        			type : 'post',
        			success:function(data){
        				if(data.code==200){
        					top.showArtDiaglog('提示',"发布成功", null, function() { //确定事件
        						top.closeDialog();
        						$(".menuson li.active", parent.document).removeClass("active");
        						$("a[href='/shiyanshi/NewsController/toMyNews.do']",parent.document).parent().addClass("active");
        						window.location= "${path}/NewsController/toMyNews.do";
        					});
        				}
        				else{
        					top.showArtDiaglog('提示', res.msg, null, function() { //确定事件
        						top.closeDialog();
        					});
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
				<li><a href="#">信息管理</a></li>
				<li><a href="#">新闻管理</a></li>
				<li><a href="#">新闻添加</a></li>
			</ul>
		</div>
		<div class="formbody">
			<div class="formtitle">
				<span>新闻添加</span>
			</div>
			<form id="add_form" method="post" enctype ="multipart/form-data">
				<ul class="forminfo">
					<li><label>文章标题：</label>
						<input name="newsName" id="newsName" type="text" class="form-control input-primary" style="width: 350px" />
					</li>
					<li><label>主图：</label>
						<input name="imageFile" id="imageFile" type="file" class="form-control input-primary" style="width: 350px" />
					</li>
					<li class="form-inline"><label>重要程度：</label>
						<select id="importance" name="importance" class="selectpicker show-tick " style="width: 350px">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
						</select>
					</li>
					<li class="form-inline"><label>关键词</label>
						<select id="keyWords" name="keyWords" class="selectpicker show-tick " multiple data-live-search="true" data-actions-box="true" style="width: 350px">
							<option value="实验">实验</option>
							<option value="科技">科技</option>
							<option value="自动化">自动化</option>
							<option value="软件">软件</option>
							<option value="硬件">硬件</option>
							<option value="化学">化学</option>
							<option value="设备模型">设备模型</option>
							<option value="操作">操作</option>
							<option value="释义">释义</option>
							<option value="仿真">仿真</option>
							<option value="函数">函数</option>
					    </select>
					</li>
					<li><label>文章内容：</label>
						<textarea id="content" name="content" style="width:700px;height:300px;">
						</textarea>
					</li>
					<li><label>&nbsp;</label>
						<input name="" type="button" class="btn btn-primary" value="确认保存" onclick="toSubmit()" />
					</li>
				</ul>
			</form>
			
		</div>
	</div>
</body>
</html>