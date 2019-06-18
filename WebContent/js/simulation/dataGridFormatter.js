/**
 * easyui dataGrid formatter common use method
 * create by dengwm
 */

/**
 * 日期格式化
 */
var dateFormatter =function (value,rowData,rowIndex){
	if(value!=undefined&&value){
		var date=new Date(value);
		if(date){
			var year=date.getFullYear();
			var month=date.getMonth()+1;
			var day=date.getDate();
			var hours=date.getHours();
			var minutes=date.getMinutes();
			var seconds=date.getSeconds();
			if(month<10){
				month="0"+month;
			}
			if(day<10){
				day="0"+day;
			}
			if(hours<10){
				hours="0"+hours;
			}
			if(minutes<10){
				minutes="0"+minutes;
			}
			if(seconds<10){
				seconds="0"+seconds;
			}
			return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
		}else{
			return '';
		}
	}else {
		return '';
	}
	
};

var formatDateToYHM =function (value){
	var date=new Date(value);
	if(date){
		var year=date.getFullYear();
		var month=date.getMonth()+1;
		var day=date.getDate();
		var hours=date.getHours();
		var minutes=date.getMinutes();
		var seconds=date.getSeconds();
		if(month<10){
			month="0"+month;
		}
		if(day<10){
			day="0"+day;
		}
		if(hours<10){
			hours="0"+hours;
		}
		if(minutes<10){
			minutes="0"+minutes;
		}
		if(seconds<10){
			seconds="0"+seconds;
		}
		return year+"-"+month+"-"+day;
	}else{
		return '';
	}
};
