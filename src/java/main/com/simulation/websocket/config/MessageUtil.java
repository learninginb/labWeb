package com.simulation.websocket.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import com.simulation.dao.mybatis.basedata.MessageDao;
import com.simulation.vo.basedata.MessageVo;
import com.simulation.websocket.handler.InfoWebSocketHandler;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月9日 下午2:15:02  
*/
public class MessageUtil {
	//消息类型
	public static final String MESSAGE_TYPE_COMMENT="评论点赞消息";
	public static final String MESSAGE_TYPE_RECEIVETASK="任务查收消息";
	public static final String MESSAGE_TYPE_REVIEWTASK="任务审核消息";
	public static final String MESSAGE_TYPE_REVIEWTASK_RESULT="任务审核结果消息";
	public static final String MESSAGE_TYPE_RECEIVEDTASK="任务待查收消息";
	//消息路径
	public static final String COMMENT_PATH="/shiyanshi/PaperController/toMyCommentList.do";
	public static final String RECEIVETASK_PATH="/shiyanshi/TaskController/toManageTask.do";
	public static final String REVIEWTASK_PATH="/shiyanshi/TaskController/toReviewTask.do";
	public static final String REVIEWTASK_RESULT_NO_PATH="/shiyanshi/TaskController/toUnfinishTask.do";
	public static final String REVIEWTASK_RESULT_YES_PATH="/shiyanshi/TaskController/toFinishTask.do";
	public static final String RECEIVEDTASK_PATH="/shiyanshi/TaskController/toReceiveTask.do";
	
	
}
