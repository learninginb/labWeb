/**
 * ajax.js 封装ajax请求
 */

function ajaxBase(config){
	const {url,data,type="get",dataType="json",cache=false,success,error} = config;
	$.ajax({
		url,
		data,
		type,
		dataType,
		cache
	}).done((res) => {
		if (res.code==200){
			if (success){
				success(res.data);
			}else{
				console.log("Success!");
			}
		}else if(res.code==302){
			console.log("Redirect to other place!");
		}else{
			if (error){
				error(res);
			}else if(res.code==500){
				console.log("Server is error!");
			}else if(res.code==400){
				console.log(res.msg);
			}else{
				console.warn(JSON.stringify(res));
			}
			
			
		}
	});
}

function getAjax(url,data,sucFun,errFun){
	return ajaxBase({url,data,success:sucFun,error:errFun});
}

function postAjax(url,data,sucFun,errFun){
	return ajaxBase({url,data,type:"post",success:sucFun,error:errFun});
}
function deleteAjax(url,data,sucFun,errFun){
	return ajaxBase({url,data,type:"delete",success:sucFun,error:errFun});
}