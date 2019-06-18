package com.simulation.service.knowledge.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.common.page.Pagination;
import com.simulation.common.util.StatusUtil;
import com.simulation.dao.mybatis.knowledge.TaskDao;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.model.knowledge.Task;
import com.simulation.service.basedata.MessageService;
import com.simulation.service.knowledge.TaskService;
import com.simulation.vo.knowledge.PaperVo;
import com.simulation.vo.knowledge.TaskVo;
import com.simulation.websocket.config.MessageUtil;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月1日 上午9:37:13  
*/
@Service
public class TaskServiceImpl implements TaskService {
	Logger log = Logger.getLogger(TaskServiceImpl.class);
	@Autowired
	TaskDao taskDao;
	@Autowired
	MessageService messageService;
	//新建父任务
	@Override
	@Transactional
	public void addTask(Task task) {
		//设置父任务的id都为0，表示最高级任务，设置任务状态为新建未被查收
		task.setParentId(0);
		task.setStatusId(StatusUtil.STATUS_NOCHECK);
		task.setAvailable(true);
		taskDao.addTaskIntoPlan(task);
		//发送消息给接收方
		messageService.sendMessageToUser(task.getExecutorId(), "你有一个任务（"+task.getTheme()+"）待查收", MessageUtil.MESSAGE_TYPE_RECEIVEDTASK, null);
		
	}
	
	/**
	 * 查询我负责的任务
	 */
	@Override
	public Pagination findManagerListByPage(int pageSize, int pageNo,Task task) {
		Pagination pagination = new Pagination();
	    pagination.setPageNo(pageNo); //当前页码
	    pagination.setPageSize(pageSize);  //每页显示多少行
	    List<TaskVo>  list = this.taskDao.findManagerListByPage(task,pagination);
	    pagination.setList(list);
	    return pagination;
	}
	
	/**
	 * 查询我完成的任务
	 */
	@Override
	public Pagination findFinishListByPage(int pageSize, int pageNo, Task task) {
		Pagination pagination = new Pagination();
	    pagination.setPageNo(pageNo); //当前页码
	    pagination.setPageSize(pageSize);  //每页显示多少行
	    List<TaskVo>  list = this.taskDao.findFinishListByPage(task,pagination);
	    pagination.setList(list);
	    return pagination;
	}
	
	/**
	 * 查询我未完成的任务
	 */
	@Override
	public Pagination findUnfinishListByPage(int pageSize, int pageNo, Task task) {
		Pagination pagination = new Pagination();
	    pagination.setPageNo(pageNo); //当前页码
	    pagination.setPageSize(pageSize);  //每页显示多少行
	    List<TaskVo>  list = this.taskDao.findUnfinishListByPage(task,pagination);
	    pagination.setList(list);
	    return pagination;
	}
	
	/**
	 * 查询我待审核的任务
	 */
	@Override
	public Pagination findReviewListByPage(int pageSize, int pageNo, Task task) {
		Pagination pagination = new Pagination();
	    pagination.setPageNo(pageNo); //当前页码
	    pagination.setPageSize(pageSize);  //每页显示多少行
	    List<TaskVo>  list = this.taskDao.findReviewListByPage(task,pagination);
	    pagination.setList(list);
	    return pagination;
	}
	
	/**
	 * 查询待我接收的任务
	 */
	@Override
	public Pagination findReceiveListByPage(int pageSize, int pageNo, Task task) {
		Pagination pagination = new Pagination();
	    pagination.setPageNo(pageNo); //当前页码
	    pagination.setPageSize(pageSize);  //每页显示多少行
	    List<TaskVo>  list = this.taskDao.findReceiveListByPage(task,pagination);
	    pagination.setList(list);
	    return pagination;
	}

	@Override
	public Pagination findRecordListByPage(int pageSize, int pageNo, Task task) {
		Pagination pagination = new Pagination();
	    pagination.setPageNo(pageNo); //当前页码
	    pagination.setPageSize(pageSize);  //每页显示多少行
	    List<TaskVo>  list = this.taskDao.findRecordListByPage(task,pagination);
	    pagination.setList(list);
	    return pagination;
	}

	/**
	 * 通过Id查询任务
	 */
	@Override
	public TaskVo findTaskById(int id) {
		try{
			return taskDao.findPlanTaskById(id);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 新建子任务
	 */
	@Override
	@Transactional
	public void addSonTask(Task task) {
		task.setStatusId(StatusUtil.STATUS_NOCHECK);
		task.setAvailable(true);
		taskDao.addSonTask(task);
		messageService.sendMessageToUser(task.getExecutorId(), "你有一个任务（"+task.getTheme()+"）待查收", MessageUtil.MESSAGE_TYPE_RECEIVEDTASK, null);
		
	}

	/**
	 * 获取子任务
	 */
	@Override
	public List<TaskVo> findSonTask(int id) {
		return taskDao.findSonTask(id);
		
	}

	@Override
	public ResponseParam changeStatus(TaskVo taskVo, int statusId) {
		ResponseParam res = new ResponseParam();
		if(statusId<StatusUtil.STATUS_NOCHECK||statusId>StatusUtil.STATUS_ALLFINISH){
			res.setCode(400);
			res.setMsg("状态参数有误!");
			res.setData(null);
			return res;
		}
		try{
			taskDao.changeStatusById(statusId, taskVo);
			res.setCode(200);
			res.setMsg("任务状态更新成功!");
			res.setData(null);
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("状态更新失败！");
			res.setData(null);
			return res;
		}
		return res;
	}
	
	/**
	 * 删除任务
	 */
	@Transactional
	@Override
	public ResponseParam deleteTask(TaskVo taskVo) {
		ResponseParam res = new ResponseParam();
		if(taskVo.getId()!=null&&taskVo.getId()!=0){
			try{
				taskDao.deleteTask(taskVo);
				res.setCode(200);
				res.setMsg("success!");
				res.setData(null);
			}catch(Exception e){
				e.printStackTrace();
				res.setCode(500);
				res.setMsg("删除失败!");
				res.setData(null);
				return res;
			}
		}else{
			res.setCode(400);
			res.setMsg("参数有误！");
			res.setData(null);
			return res;
		}
		return res;
	}
	
	/**
	 * 修改任务
	 */
	@Override
	@Transactional
	public ResponseParam editTask(TaskVo taskVo) {
		ResponseParam res = new ResponseParam();
		
		try{
			TaskVo sonVo = taskDao.findPlanTaskById(taskVo.getId());
			if(sonVo.getParentId()!=0){
				TaskVo parentVo = taskDao.findPlanTaskById(sonVo.getParentId());
				if (taskVo.getImportance()>parentVo.getImportance()){
					res.setCode(400);
					res.setMsg("子任务的重要程度不能超过父任务");
					res.setData(null);
					return res;
				}else if(taskVo.getFinishTime().getTime()>parentVo.getFinishTime().getTime()){
					res.setCode(400);
					res.setMsg("子任务的截至时间不能晚于父任务");
					res.setData(null);
					return res;
				}
			}
			taskDao.editTask(taskVo);
			res.setCode(200);
			res.setMsg("修改成功！");
			res.setData(null);
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("修改失败!");
			res.setData(null);
			return res;
		}
		return res;
	}

}
