<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.apache.commons.lang3.StringUtils,org.apache.commons.lang3.ObjectUtils" %>
<%@ page isELIgnored="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

	<c:set var="path" value="${pageContext.request.contextPath}"/>
	<script type="text/javascript">
		var path = '<%=request.getContextPath() %>';
		var _pageSize=10;
	</script>
	<%-- <script src="${path }/jslib/table/docs/assets/js/jquery.min.js"></script>   --%>
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="${path }/jslib/browser.js"></script>
	
	
	<%-- common js class --%>
	<script type="text/javascript" src="${path}/js/simulation/Tools.js"></script>
	<script type="text/javascript" src="${path}/js/simulation/util.js"></script>
	<!-- 日期 -->
	<script type="text/javascript" src="${path}/jslib/My97DatePicker/4.7/WdatePicker.js"></script>
	<link href="${path}/css/layout/style.css" rel="stylesheet" type="text/css" />
	<script src="${path}/jslib/artDialog/jquery.artDialog.js?skin=blue"></script>   
	<script src="${path}/jslib/artDialog/plugins/iframeTools.js"></script>  
	<script src="${path}/js/simulation/artDialogExt.js"></script>
	<!-- bootstrap table 引入 -->
<%--<link rel="stylesheet" href="${path }/jslib/table/docs/assets/bootstrap/css/bootstrap-theme.min.css"> --%>
	
	<link rel="stylesheet" href="${path }/jslib/table/docs/assets/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${path }/jslib/table/docs/assets/bootstrap/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="${path }/jslib/table/dist/bootstrap-table.min.css">
    <!-- 日期控件所用css -->
	<link rel="stylesheet" href="${path }/jslib/bootstrap/css/bootstrap-datetimepicker.min.css">
    <script src="${path }/jslib/table/docs/assets/bootstrap/js/bootstrap.min.js"></script>
    
    <!-- 日期所用js -->
    <script src="${path }/jslib/bootstrap/js/bootstrap-datetimepicker.js"></script>
    <script src="${path }/jslib/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${path }/jslib/table/docs/assets/bootstrap/js/bootstrap-select.min.js"></script>
    <script src="${path }/jslib/table/docs/assets/bootstrap/js/defaults-zh_CN.min.js"></script>
    <script src="${path }/jslib/table/dist/bootstrap-table.min.js"></script>
    <script src="${path }/jslib/table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
    <!-- bootstrap table 操作公共方法 -->
	<script src="${path }/js/simulation/tableCommon.js"></script>
	<!-- 字体样式 -->
	<link rel="stylesheet" href="${path }/css/font-awesome-4.4.0/css/font-awesome.min.css">
	<%-- jquery --%>
	<script type="text/javascript" src="${path}/jslib/jquery.form.js"></script>
		<style type="text/css">
a:link{
text-decoration:none;
}
a:visited{
text-decoration:none;
}
a:hover{
text-decoration:none;
}
a:active{
text-decoration:none;
}
</style>
	<script type="text/javascript">
	//屏蔽readonly下按backspace键返回的功能
		$(document).keydown(function(e){
			var event=arguments.callee.caller.arguments[0]||window.event;// 修正浏览器兼容 
			var target = e.target ;
			var tag = e.target.tagName.toUpperCase();
			if(event.keyCode == 8){
				if((tag == 'INPUT' && !$(target).attr("readonly"))||(tag == 'TEXTAREA' && !$(target).attr("readonly"))){
					if((target.type.toUpperCase() == "RADIO") || (target.type.toUpperCase() == "CHECKBOX")){
						return false ;
					}else{
						return true ;
					}
				}else{
					return false ;
				}
			}
		}); 
	</script>
<script type="text/javascript">
	//图片上传预览    IE是用了滤镜。
	function previewImage(file) {
		var MAXWIDTH = 120;
		var MAXHEIGHT = 120;
		var div = document.getElementById('preview');
		if (file.files && file.files[0]) {
			div.innerHTML = '<img id=imghead>';
			var img = document.getElementById('imghead');
			img.onload = function() {
				var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
						img.offsetWidth, img.offsetHeight);
				img.width = rect.width;
				img.height = rect.height;
				//                 img.style.marginLeft = rect.left+'px';
				img.style.marginTop = rect.top + 'px';
			}
			var reader = new FileReader();
			reader.onload = function(evt) {
				img.src = evt.target.result;
			}
			reader.readAsDataURL(file.files[0]);
		} else //兼容IE
		{
			var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
			file.select();
			var src = document.selection.createRange().text;
			div.innerHTML = '<img id=imghead>';
			var img = document.getElementById('imghead');
			img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
					img.offsetHeight);
			status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width
					+ ',' + rect.height);
			div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
		}
	}
	function clacImgZoomParam(maxWidth, maxHeight, width, height) {
		var param = {
			top : 0,
			left : 0,
			width : width,
			height : height
		};
		if (width > maxWidth || height > maxHeight) {
			rateWidth = width / maxWidth;
			rateHeight = height / maxHeight;

			if (rateWidth > rateHeight) {
				param.width = maxWidth;
				param.height = Math.round(height / rateWidth);
			} else {
				param.width = Math.round(width / rateHeight);
				param.height = maxHeight;
			}
		}
		param.left = Math.round((maxWidth - param.width) / 2);
		param.top = Math.round((maxHeight - param.height) / 2);
		return param;
	}
</script>
<style type="text/css">
#preview{width:120px;height:120px;border:1px solid gray;overflow:hidden;}
#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
</style>