<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/global.jsp"%>	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://open.web.meitu.com/sources/xiuxiu.js" type="text/javascript"></script>
<title>上传图像</title>
<script type="text/javascript">
	//如果指定id，则参数归各编辑所属(如下例中设置两个编辑器的上传按钮的不同文字标签)
	xiuxiu.setLaunchVars("uploadBtnLabel", "确认", "lite");
	//如果未指定id，则参数为所有编辑器共有
	xiuxiu.setLaunchVars("language", "zh_cn");
	xiuxiu.embedSWF("altContent1", 5, 800, 400, "lite");
	
	xiuxiu.onInit = function (id)
	{
	    xiuxiu.setUploadURL("${httpPath}/sysReleaseUserController/uploadImages.do", id);
	    xiuxiu.setUploadType(2, id);
	    xiuxiu.setUploadDataFieldName("Filedata");
	    if('${imagesPath}'){
			xiuxiu.loadPhoto('${httpPath}/${imagesPath}');
	    }
	}
	
	xiuxiu.onBeforeUpload = function (data, id)
	{
	  	xiuxiu.setUploadArgs({type:data.type});
	  	return true;
	}
	
	xiuxiu.onUploadResponse = function (data, id)
	{
		var json=$.parseJSON(data);
		if(json&&json.success){
			artDialog.open.origin.document.getElementById('imgFilePath').value=json.msg;
			artDialog.open.origin.closeDialog();
		}else{
			alert('上传失败，请重新上传');
		}
	}
	
	xiuxiu.onDebug = function (data,id)
	{
	    alert("错误响应" + data);
	}
	
	xiuxiu.onClose = function (id)
	{
	    alert('关闭窗口');
	}
</script>
</head>
<body>
<div id="flashEditorOut">
	<div id="altContent1">
    </div>
</div>
        
</body>
</html>