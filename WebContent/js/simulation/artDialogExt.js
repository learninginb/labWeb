function showArtDiaglog(title,msg,closeFun,okFunction){
	art.dialog({
		id:'artDialog',
		title:title,
	    content: msg,
	    ok: okFunction,
	    close:closeFun
	});
}

//关闭窗口
function closeDialog(){
	art.dialog.list["artDialog"].close();
}

//弹出确认窗口
function showConfirmDiaglog(title,msg,closeFun,okFunction){
	art.dialog({
		id:'artDialog',
		title:title,
	    content: msg,
	    cancelVal: '取消',
	    cancel: true,
	    ok: okFunction,
	    close:closeFun
	});
}