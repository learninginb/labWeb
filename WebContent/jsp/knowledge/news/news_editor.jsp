<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻-修改</title>
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
                    afterCreate : function() { this.sync(); },
                    afterBlur: function(){this.sync();}
                });
        });
        
        $(function(){
        	requestData();
        })
        //请求数据
        function requestData(){
        	getAjax("${path}/NewsController/news/"+${param.id}+".do",{},(data)=>{
        		console.log(data['newsVo']);
        		var newsVo = data['newsVo'];
        		$('#newsName').val(newsVo.newsName);
        		$('#importance').val(newsVo.importance);
        		$('#importance').selectpicker('refresh');
        		var keywordsList = newsVo.keyWords.split(',');
        		console.log(keywordsList);
        		for(var i=0;i<keywordsList.length;i++){
        			$("#keyWords").find("option[value='"+keywordsList[i]+"']").attr("selected",true);
        		}
        		$('#keyWords').selectpicker('refresh');
        		KindEditor.html("#content", newsVo.content);
        	})
        }
        
        //提交修改
        function toSubmit(){
        	$("#add_form").ajaxSubmit({
    			url:"${path}/NewsController/news/"+${param.id}+".do",
    			data : $("#add_form").serialize(),
    			cache : false,
    			dataType : 'json',
    			type : 'post',
    			success:function(data){
    				if(data.code==200){
    					top.showArtDiaglog('提示',"修改成功", null, function() { //确定事件
    						top.closeDialog();
    						window.location= "${path}/NewsController/toMyNews.do";
    					});
    				}
    				else{
    					top.showArtDiaglog('提示', data.msg, null, function() { //确定事件
    						top.closeDialog();
    					});
    				}
    			}
    		});
        }
        
        function goBackList(){
        	window.location="${path}/NewsController/toMyNews.do";
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
				<li><a href="#">新闻修改</a></li>
			</ul>
		</div>
		<div class="formbody">
			<div class="formtitle">
				<span>新闻修改</span>
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
					            　　<option value="苹果">苹果</option>
					            　　<option value="菠萝">菠萝</option>
					            　　<option value="香蕉">香蕉</option>
					            　　<option value="火龙果">火龙果</option>
					            　　<option value="梨子">梨子</option>
					            　　<option value="草莓">草莓</option>    
					            　　<option value="哈密瓜">哈密瓜</option>
					            　　<option value="椰子">椰子</option>
					            　　<option value="猕猴桃">猕猴桃</option>
					            　　<option value="桃子">桃子</option>
					    </select>
					</li>
					<li><label>文章内容：</label>
						<textarea id="content" name="content" style="width:700px;height:300px;">
						</textarea>
					</li>
					<li><label>&nbsp;</label>
						<input name="" type="button" class="btn btn-primary" value="确认修改" onclick="toSubmit()" />&emsp;
						<input name="" type="button" class="btn btn-warning" value="返回列表"	onclick="goBackList();" />
					</li>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>