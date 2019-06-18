

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询我的评论列表</title>
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
 <link rel="stylesheet" href="${path }/jslib/contextMenu/css/contextMenu.css" />
<script type="text/javascript" src="${path }/jslib/pagePlugin/pageMe.js"></script>
<script type="text/javascript" src="${path }/jslib/contextMenu/js/contextMenu.js"></script>
<script type="text/javascript" src="${path }/js/ajax.js"></script>
<script type="text/javascript" src="${path }/js/dateTime.js"></script>
<script type="text/javascript">
//所选择的commentId
var commentId = 0; 
$(function(){
	//modal设置
    $("#myModal").on('show.bs.modal', function(){
        var $this = $(this);
        var $modal_dialog = $this.find('.modal-dialog');
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $this.css('display', 'block');
        $modal_dialog.css({'margin-top': Math.max(0, ($(window).height() - $modal_dialog.height()) / 2) });
      });
    $("#myModal").on('hidden.bs.modal', function(){
    	$("#edittext").val('');
    });
	//请求数据
	submitAjax(1,null,null,true,false);
	
	
	
});



function displayUI(pagenum,totalnum,totallist,commentlist,keyWordList){
	
	//分页
	$("#page").paging({
	    pageNum: pagenum, // 当前页面
	    totalNum: totalnum, // 总页码
	    totalList: totallist, // 记录总数量
	    callback: function (num) { //回调函数
	    	selectMyComment(num);
	    }
	
	});
	//下拉框
	var selectOBJ = document.getElementById("key_wordSelector");
	$('#key_wordSelector').html("");
	for(var i=0;i<keyWordList.length;i++){
		selectOBJ.add(new Option(keyWordList[i].name,keyWordList[i].name));
	}
    $('#key_wordSelector').selectpicker('refresh');
    $('#key_wordSelector').selectpicker('render');

	//渲染数据
	$("#commentList").html('');
	for(var i=0;i<commentlist.length;i++){
		//选择点赞图标
		var imgUrl = '';
		if (commentlist[i].praised)
			imgUrl = '${path }/images/comment/praise2.png';
		else
			imgUrl = '${path }/images/comment/praise1.png';	
			
		$("#commentList").append(
				"<li class='comment'><label id='commentId' style='display:none'>"+commentlist[i].id+"</label>"+
				"<ul  style='padding:0px 0px 10px 0px;'>"+
					"<li>"+
						"<strong>"+commentlist[i].userName+"：</strong><span>"+commentlist[i].comment+"</span>"+
					"</li>"+
					"<li>"+
						"<span class='li_left' style='float:left;'>"+datetimeFormat(commentlist[i].update_time)+"</span> <div class='li_right' style='float:right;'><a href='javascript:;' class='praiseImg' onclick='addPraise(this,"+commentlist[i].id+")'><img style='width:17px;height:17px' src='"+imgUrl+"'></a>&nbsp;<span style='margin-right:15px;'>"+commentlist[i].praise_count+"</span></div>"+
					"</li>"+
				"</ul>"+
				"<hr>"+
			"</li>"		
		)
	}
	
	
	//contextMenu右击菜单
	$(".comment").contextMenu({
		width: 110,// width
		itemHeight: 30,// 菜单项height
		bgColor: "#fff",// 背景颜色
		color: "#333",// 字体颜色
		fontSize: 12,// 字体大小
		hoverBgColor: "#99CC66",// hover背景颜色
		target: function(ele) {// 当前元素
		console.log($(ele).children().html().trim());
		commentId = $(ele).children().html().trim();
		},
		menu: [{// 菜单项
				text: "修改",
				icon: "${path }/jslib/contextMenu/img/edit.png",
				callback: function() {
					$("#myModal").modal('show');
				}
			},
			{
				text: "删除",
				icon: "${path }/jslib/contextMenu/img/delete.png",
				callback: function() {
					getAjax("${path}/PaperController/deleteCommentById.do",{id:commentId},(data)=>{
						top.showArtDiaglog('提示', '删除成功', null, function() {
							top.closeDialog();
							selectMyComment(1);
						});
						
						},
						(data)=>{
							top.showArtDiaglog('提示', '删除失败', null, function() {
								top.closeDialog();
							});
						
					});
					
				}
			}
		]
	
	});
}
//查询我的评论
function selectMyComment(num){
	var name = $("#name").val();
	var keyWordList = $("#key_wordSelector").val();
	var order = $("input[name='order']:checked").val();
	var hot ;
	var recent ;
	console.log(name+","+order+","+keyWordList);
	if(order=="hot"){
		hot=true;recent=false;
	}
	else if(order=="recent"){
		hot=false;recent=true;
	}
	submitAjax(num,name,keyWordList,hot,recent);
	
}

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
//请求数据
function submitAjax(pageNo,paperName,keyWordList,hot,recent){
	postAjax("${path}/PaperController/getMyCommentPage.do",{pageNo,paperName:paperName,keyWordList:JSON.stringify(keyWordList),hot:hot,recent:recent},(data)=>{
		console.log(data);
		displayUI(data["pagination"].pageNo,data["pagination"].totalPage,data["pagination"].totalCount,data["pagination"].list,data["keyWordList"]);
	});
	
}
//修改提交
function editSubmit(){
	console.log("修改提交 "+commentId+","+$("#edittext").val());
	postAjax("${path}/PaperController/editMyComment.do",{id:commentId,comment:$("#edittext").val()},(data)=>{
		top.showArtDiaglog('提示', '修改成功', null, function() {
			top.closeDialog();
			selectMyComment(1);
		});
	});
	$("#myModal").modal('hide');
}

</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li><a href="#">文献管理</a></li>
			<li><a href="#">文摘评论管理</a></li>
			<li><a href="#">我的文献评论</a></li>
		</ul>
	</div>
	<!-- modal组件 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   	 	<div class="modal-dialog">
        	<div class="modal-content">
            	<div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                	<h4 class="modal-title" id="myModalLabel">修改评论</h4>
            	</div>
				<textarea class="form-control" rows="3" id=edittext></textarea>
            	<div class="modal-footer">
                	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                	<button  type="button" class="btn btn-primary" onclick="editSubmit()">提交</button>
            	</div>
        	</div><!-- /.modal-content -->
    	</div><!-- /.modal -->
	</div>
	
	<div class="rightinfo">
		<div>
		   <input type="hidden" name="resource_path" id="resource_path" value="${resource_path }">
			<form id="searchForm" name="searchForm" method="post">
				<label>名称：</label><input type="text" id="name" name="name" class="txtSearch">&nbsp;
				<label>关键词:</label><select id="key_wordSelector" name="key_wordSelector" class="selectpicker show-tick" noneSelectedText="关键词"  multiple>
							
						</select>&nbsp;
				<input type="button" class="btn btn-info btn-round" value="查询"
					onclick="selectMyComment(1)">&nbsp;&nbsp; <input type="button"
					class="btn btn-warning btn-round" value="重置"
					onclick="$('#searchForm')[0].reset();">
			</form>
		</div>
		<div id="toolbar" class="btn-group" >
				<ul>
					<li style="margin:20px 0px 20px 0px;">
						
						<input type="radio" name="order" value="hot" checked="checked"> 最热
						<input type="radio" name="order" value="recent" >最新												
					</li>
				</ul>
		</div>
		<div>
			<ul id="commentList">
				<li id='comment' class="comment">
					<ul  style="padding:0px 0px 10px 0px;">
						<li>
							<strong>用户1：</strong><span>用户1的评论</span>
						</li>
						<li>
							<span class="li_left" style="float:left;">2019年4月17日</span> <div class="li_right" style="float:right;"><a href=""  onclick=""><img style="width:17px;height:17px" src="${path }/images/comment/praise1.png"></a>&nbsp;<span style="margin-right:15px;">1111</span></div>
						</li>
					</ul>
					<hr>
				</li>
			</ul>
		</div>
		
		<div id="page" class="page_div"></div>
	</div>
	
</body>
</html>