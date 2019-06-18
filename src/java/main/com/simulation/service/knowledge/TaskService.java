package com.simulation.service.knowledge;

import java.util.List;

import com.simulation.common.page.Pagination;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.model.knowledge.Task;
import com.simulation.vo.knowledge.TaskVo;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年4月28日 下午9:17:29  
*/
public interface TaskService {
	public void addTask(Task task);
	public void addSonTask(Task task);
	public ResponseParam deleteTask(TaskVo taskVo);
	public ResponseParam editTask(TaskVo taskvo);
	public Pagination findManagerListByPage(int pageSize,int pageNo,Task task);
	public Pagination findFinishListByPage(int pageSize,int pageNo,Task task);
	public Pagination findUnfinishListByPage(int pageSize,int pageNo,Task task);
	public Pagination findReviewListByPage(int pageSize,int pageNo,Task task);
	public Pagination findReceiveListByPage(int pageSize,int pageNo,Task task);
	public Pagination findRecordListByPage(int pageSize,int pageNo,Task task);
	public TaskVo findTaskById(int id);
	public List<TaskVo> findSonTask(int id);
	public ResponseParam changeStatus(TaskVo taskVo,int statusId);
}
