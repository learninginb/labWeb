package com.simulation.service.knowledge.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import com.simulation.common.util.StatusUtil;
import com.simulation.dao.mybatis.basedata.MessageDao;
import com.simulation.dao.mybatis.knowledge.TaskDao;
import com.simulation.dao.mybatis.knowledge.TaskReviewDao;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.service.knowledge.TaskReviewService;
import com.simulation.vo.basedata.MessageVo;
import com.simulation.vo.knowledge.TaskReviewVo;
import com.simulation.vo.knowledge.TaskVo;
import com.simulation.websocket.config.MessageUtil;
import com.simulation.websocket.handler.InfoWebSocketHandler;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月7日 下午9:56:24  
*/
@Service
public class TaskReviewServiceImpl implements TaskReviewService {
	@Autowired
	private TaskReviewDao taskReviewDao;
	@Autowired
	private TaskDao taskDao;
	@Autowired
	private MessageDao messageDao;
	
	public InfoWebSocketHandler infoWebSocketHandler(){
		return new InfoWebSocketHandler();
	}

	
	/**
	 * 添加审核
	 */
	@Override
	@Transactional
	public boolean addReview(TaskReviewVo vo) {
		try{
			taskReviewDao.addTaskReview(vo);
			TaskVo taskVo = new TaskVo();
			taskVo.setId(vo.getTaskId());
			taskDao.changeStatusById(StatusUtil.STATUS_REVIEWING,taskVo);
			taskVo = taskDao.findPlanTaskById(vo.getTaskId());
			MessageVo message = new MessageVo();
			System.out.println("---------------"+taskVo.getDistributorId());
			message.setReceiverId(taskVo.getDistributorId());
			message.setCreateTime(new Date());
			message.setType(MessageUtil.MESSAGE_TYPE_REVIEWTASK);
			message.setIsRead(0);
			message.setMessageUrl(MessageUtil.REVIEWTASK_PATH);
			String msg = "用户"+taskVo.getExecutor()+"提交了审核";
			message.setContent(msg);
			messageDao.addMessage(message);
			infoWebSocketHandler().sendMessageToUser(taskVo.getDistributorId(), new TextMessage(msg));
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public ResponseParam getReview(TaskReviewVo vo) {
		ResponseParam res = new ResponseParam();
		if(vo.getTaskId()==null||vo.getTaskId()==0){
			res.setCode(400);
			res.setMsg("不存在这样的审核表");
			res.setData(null);
			return res;
		}
		try{
			TaskReviewVo reviewVo = taskReviewDao.findReviewByTaskId(vo);
			res.setCode(200);
			res.setMsg("success!");
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("taskReview", reviewVo);
			res.setData(data);
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("查询错误!");
			res.setData(null);
			return res;
		}
		
		return res;
	}

}
