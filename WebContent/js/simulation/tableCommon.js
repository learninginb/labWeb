/**
 * 刷新数据
 */
function refleshData(tableId){
	$("#"+tableId).bootstrapTable('refresh');
}

/**
 * 获得选择行
 * @returns
 */
function getRow(tableId){
	var rowSelected=$("#"+tableId).bootstrapTable('getSelections');
	if(rowSelected.length>0){
		return rowSelected[0];
	}else{
		return null;
	}
}

/**
 * 获得选择行
 * @returns
 */
function getSelectedRows(tableId){
	var rowSelected=$("#"+tableId).bootstrapTable('getSelections');
	if(rowSelected.length>0){
		return rowSelected;
	}else{
		return null;
	}
}

/**
 * 获得所选择行的id
 */
function getSelectedRowsIds(tableId){
	var selectIds=getSelectedRowsArr(tableId);
	if(selectIds.length>0){
		return selectIds.join(",");
	}else{
		return null;
	}
}


/**
 * 获得所选择行的数组id
 */
function getSelectedRowsArr(tableId){
	var selectRows=getSelectedRows(tableId);
	var selectIds=[];
	if(selectRows){
		for(var i=0;i<selectRows.length;i++){
			selectIds.push(selectRows[i].id);
		}
	}
	return selectIds;
}

/**
 * 日期格式化
 */
dateFormatter=function(value,row,index){
	if(value){
		var now=new Date(value);
		var year=now.getFullYear();  
		var month=now.getMonth()+1; 
		if(month<10){
			month="0"+month;
		}
		var date=now.getDate();  
		if(date<10){
			date="0"+date;
		}
		var hour=now.getHours();    
		if(hour<10){
			hour="0"+hour;
		}
		var minute=now.getMinutes();    
		if(minute<10){
			minute="0"+minute;
		}
		var second=now.getSeconds(); 
		if(second<10){
			second="0"+second;
		}
		return year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;
	}else{
		return   "-";
	}
           
}


