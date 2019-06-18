<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文献评论列表</title>
 <style>
        /* 外面盒子样式---自己定义 */
        .page_div{margin:20px 10px 20px 0;color:#666}
        /* 页数按钮样式 */
        .page_div button{display:inline-block;min-width:30px;height:28px;cursor:pointer;color:#666;font-size:13px;line-height:28px;background-color:#f9f9f9;border:1px solid #dce0e0;text-align:center;margin:0 4px;-webkit-appearance: none;-moz-appearance: none;appearance: none;}
        #firstPage,#lastPage,#nextPage,#prePage{width:50px;color:#0073A9;border:1px solid #0073A9}
        #nextPage,#prePage{width:70px}
        .page_div .current{background-color:#0073A9;border-color:#0073A9;color:#FFF}
        /* 页面数量 */
        .totalPages{margin:0 10px}
        .totalPages span,.totalSize span{color:#0073A9;margin:0 5px}
        /*button禁用*/
        .page_div button:disabled{opacity:.5;cursor:no-drop}
  </style>
<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${path }/jslib/pagePlugin/pageMe.js"></script>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<script type="text/javascript" src="${path }/js/dateTime.js"></script>
<script type="text/javascript">
//pageMe.js 使用方法
var paperId = ${param.id};
$(function(){
	pageAjax(0);
});

//点赞事件
function addPraise(obj,commentId){
	var imgUrl = $(obj).find('img').attr('src');
	if (imgUrl=='${path }/images/comment/praise1.png')
		imgUrl = '${path }/images/comment/praise2.png';
	else
		imgUrl = '${path }/images/comment/praise1.png';
	
	getAjax('${path}/PaperController/addPraise.do',{id:commentId},(data) =>{
		$(obj).next().text(data["praise_count"]);
	});
	$(obj).find('img').attr('src',imgUrl);
}

//请求分页数据
function pageAjax(num){
	getAjax('${path}/PaperController/toCommentList.do',{paper_id:paperId,pageNo:num,hot:false,recent:true},(data) => {
 		 console.log(data["pagination"]);
	displayPage(data["pagination"].pageNo,data["pagination"].totalPage,data["pagination"].totalCount);
	updateToUI(data["pagination"].list);
	});
}

//分页管理
function displayPage(pagenum,totalnum,totallist){
	$("#page").paging({
	    pageNum: pagenum, // 当前页面
	    totalNum: totalnum, // 总页码
	    totalList: totallist, // 记录总数量
	    callback: function (num) { //回调函数
	        console.log(num);
	        pageAjax(num);
	    }
	});
}


//数据渲染
function updateToUI(list){
	console.log(list);
	$("#commentList").html("<li class='headTile'><label style='font-size:20px'>精彩评论</label><hr>");
	for(var i=0;i<list.length;i++){ 
		//选择点赞图标
		var imgUrl = '';
		if (list[i].praised)
			imgUrl = '${path }/images/comment/praise2.png';
		else
			imgUrl = '${path }/images/comment/praise1.png';	
			
		$("#commentList").append(
				"<li>"+
				"<ul>"+
					"<li>"+
						"<strong>"+list[i].userName+": </strong><span>"+list[i].comment+"</span>"+
					"</li>"+
					"<li>"+
						"<span class='li_left' style='float:left;'>"+datetimeFormat(list[i].update_time)+"</span> <div class='li_right' style='float:right;'><a href='javascript:;' class='praiseImg' onclick='addPraise(this,"+list[i].id+")'><img style='width:17px;height:17px' src='"+imgUrl+"'></a>&nbsp;<span style='margin-right:15px;'>"+list[i].praise_count+"</span></div>"+
					"</li>"+
				"</ul>"+
				"<hr>"+
			"</li>"
				);
	}
}
</script>
</head>
<body >
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li><a href="#">文献管理</a></li>
			<li><a href="#">文献评论管理</a></li>
			<li><a href="#">评论列表</a></li>
		</ul>
	</div>
	<div>
		<div class="rightinfo">
			<ul id="commentList">
				<li class="headTile"><label style="font-size:20px">精彩评论</label><hr>
				</li>
			</ul>
		</div>
		<div id="page" class="page_div"></div>
	</div>
</body>
</html>