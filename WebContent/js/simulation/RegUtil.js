/**
 * 匹配正整数
 */
function regPositiveInt(val){
	var reg=/^[0-9]*[1-9][0-9]*$/;
	if(val){
		return  reg.test(val);
	}else{
		return false;
	}
}

/**
 * 匹配非负整数
 */
function regPositiveInt2(val){
	var reg=/^\d+$/;
	if(val){
		reg.test(val);
	}else{
		return false;
	}
}

/**
 * 匹配中文
 */
function regChinese(val){
	var reg=/^[\u4e00-\u9fa5]/;
	if(val){
		return reg.test(val);
	}else{
		return false;
	}
}

/**
 * 匹配邮箱
 */
function regEmail(val){
	var reg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
	if(val){
		return reg.test(val);
	}else{
		return false;
	}
}

/**
 * 匹配邮政编码
 */
function regZipCode(val){
	var reg=/^[1-9]\d{5}$/;
	if(val){
		return reg.test(val);
	}else{
		return false;
	}
}

/**
 * 匹配IP地址
 */
function regIpAddress(val){
	var reg=/\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\b/;
	if(val){
		return reg.test(val);
	}else{
		return false;
	}
}

/**
 * 匹配身份证号码
 */
function regIdNumber(val){
	var reg=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}(\d|x|X)$/;
	if(val){
		return reg.test(val);
	}else{
		return false;
	}
}

/**
 * 匹配URL
 */
function regURL(val){
	var reg= /^[a-zA-z]+:\/\/[^s]*$/;
	if(val){
		return reg.test(val);
	}else{
		return false;
	}
}

/**
 * 匹配电话号码
 */
function regPhone(val){
	var reg=/^d{3}-d{8}|d{4}-d{7}$/;
	if(val){
		return reg.test(val);
	}else{
		return false;
	}
}