<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>信息阅读</title>
<link href="${path}/css/knowledge/html5.css" rel="stylesheet" type="text/css">
<link href="${path}/css/knowledge/front.css" rel="stylesheet" type="text/css">
<link href="${path}/css/knowledge/jquery.ui.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${path}/jslib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${path}/jslib/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<style>
table tr td{border:1px solid #000;}
</style>
<script type="text/javascript">
	$(function(){
		var id = $("#id").val();
		//信息内容显示
		$("#txtShow").html($("#txtHidden").val());
		//当前用户已经收藏且收藏有效则显示取消收藏，否则显示收藏按钮
		showCollection();
		//点击收藏
		$("#comfirmSpan").bind("click",function(){
			$.post('${path}/knowledgeCollectionController/collection.do?knowledgeId='+id,function(data){
				var json=$.parseJSON(data);
			    if(json.success){
				   showArtDiaglog('提示','收藏成功',function(){
					   $("#comfirmSpan").hide();
					   $("#cancelSpan").show();
 					},function(){
 						closeDialog();
 					});
			    }else{
				   showArtDiaglog('提示','收藏失败',function(){
 					},function(){
 						closeDialog();
 					});
			    }
			 });
		});
		//取消收藏
		$("#cancelSpan").bind("click",function(){
			$.post('${path}/knowledgeCollectionController/cancelCollection.do?knowledgeId='+id,function(data){
				var json=$.parseJSON(data);
			    if(json.success){
				   showArtDiaglog('提示','取消成功',function(){
					   $("#cancelSpan").hide();
					   $("#comfirmSpan").show();
 					},function(){
 						closeDialog();
 					});
			    }else{
				   showArtDiaglog('提示','取消失败',function(){
 					},function(){
 						closeDialog();
 					});
			    }
			 });
		});
		//页面加载完毕，添加阅读纪录，并把阅读次数+1
		readKnowledge(id);
	});
	
	function showCollection(){
		var isCollectionValid = $("#isCollectionValid").val();
		if(isCollectionValid==1){
			 $("#cancelSpan").show();
			 $("#comfirmSpan").hide();
		}
	}
	
	function readKnowledge(id){
		$.post('${path}/knowledgeReadingController/add.do?knowledgeId='+id,function(data){
		 });
	}
	
	function toSubmit(){
		//表单验证
		if(!validate()){
			return;
		}
		//表单提交
		$("#add_form").ajaxSubmit({
			url:"${path}/knowledgeCommentController/add.do",
			data : $("#add_form").serialize(),
			cache : false,
			dataType : 'JSON',
			type:'post',
			success:function(data){
				if(data.success){
			       top.showArtDiaglog('提示','添加成功',function(){  //关闭事件
						
					},function(){   //确定事件
						
					});
				}else{
					top.showArtDiaglog('提示',data.msg,null,function(){
						top.closeDialog();
					});
				}
			}
		});
	}
	
	function validate(){
		var score = $("#knowledgeScore").val().trim();
		var reg = /^([1-9]\d?|100)$/;
        if(!score.match(reg)){
        	top.showArtDiaglog('提示','分数不能为空，且需输入1到100的整数',function(){  //关闭事件
			},function(){   //确定事件
			});
         return false;
        }else{
        	return true;
        }
	}
	
	//返回列表
	function goBackList(){
		window.location="${path}/knowledgeController/toList.do";
	}
</script>
</head>
<body>
<div class="place">
	<span>位置：</span>
	<ul class="placeul">
		<li><a href="#">信息库管理</a></li>
		<li><a href="#">信息管理</a></li>
		<li><a href="#">信息阅读</a></li>
	</ul>
</div>
<div class="container">
<div class="clear"></div>	
	<!-- 左栏_begin -->
	<div class="w670 mt10">
        <div class="clear"></div>
        <div class="newsTex">
        	<input type="hidden" name="id" id="id" value="${vo.id }"/>
        	<input type="hidden" name="isCollectionValid" id="isCollectionValid" value="${vo.isCollectionValid }" />
        	<h1>${vo.knowledgeTitle }</h1>
            <div class="msgbar">发布时间： <fmt:formatDate value="${vo.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /> &nbsp; 作者：${vo.createUserName } &nbsp; 阅读次数：<span id="views"><c:choose><c:when test="${empty  vo.readingTimes }">0</c:when><c:otherwise>${vo.readingTimes }</c:otherwise></c:choose>次&nbsp;</span>
            	<span id="collectCsiDiv"><span id="cancelSpan" style="display:none; cursor: pointer;">取消收藏</span>
					<span id="comfirmSpan" style="cursor: pointer;">收藏</span>
				</span>
            </div>
            <div class="newsCon">
            	<span id="txtShow" name="txtShow"></span>
            	<textarea id="txtHidden" style="display:none;">${vo.knowledgeComment }</textarea>
    		</div>
            </div>
        </div>
        <div class="clear-10"></div>
        <div class="hundred100">
            <div class="rim06">
	        <!--评论_Begin-->
			<div class="pinglun">
				<div class="LanMu01-2">
			        <div class="LanMu01-1"></div>
			        <span>我来说两句</span>
			        <div class="LanMu01-3"></div>
			    </div>
			    
			    <div class="clear-10"></div>
			    <div class="pltable">
			  		<form id="add_form" method="post">
			  			<input type="hidden" name="knowledgeId" id="knowledgeId" value="${vo.id }"/>
					    <div class="plfl1">评论内容：</div>
					    <div class="plfr1"><textarea name="commentContent" id="commentContent" class="cinput" cols="60" rows="5"></textarea></div>
					    <div class="clear"></div>
					    <div class="plfl1">&nbsp;</div>
					    
					    <div class="clear-10"></div>
					    <div id="scoreDiv">
					    	<ul class="forminfo">
							    <li><label style="width:60px">得分：</label><input name="knowledgeScore" id="knowledgeScore" type="text" class="form-control input-primary" style="width:250px"  /></li>
			    		    	<li><span style="color:red">*&nbsp;此项需填入一个整数，满分100分。</span></li>
			    		    </ul>
					    </div>
					    
					    <div class="clear-10"></div>
					    <div class="plfr1">
					    <input type="hidden" name="contentId" value="563">
					    <input type="hidden" name="sessionId" id="sessionId" value="D79FFA91D0FF6E5499A0D4AA7DEDD3EF">
					    <input type="button" name="submit" value=" 马上发表 " class="published" onclick="toSubmit()">
					    </div>
			    	</form>
			    </div>
			</div>
	        <!--评论_End-->
	        <!-- 附件显示begin -->
	        <a href="${vo.knowledgeAttachUrl }">查看附件</a>
	        <!-- 附件显示begin -->
            </div>
        </div>
        </div>
    </div>
    <!-- 左栏_end -->
</div>  

