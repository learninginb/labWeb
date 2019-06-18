package com.simulation.service.basedata.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import com.simulation.common.page.Pagination;
import com.simulation.dao.mybatis.basedata.MessageDao;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.service.basedata.MessageService;
import com.simulation.vo.basedata.MessageVo;
import com.simulation.vo.sys.SysUserActiveVo;
import com.simulation.websocket.config.MessageUtil;
import com.simulation.websocket.handler.InfoWebSocketHandler;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月9日 下午4:34:18  
*/
@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDao messageDao;
	Logger log = Logger.getLogger(MessageServiceImpl.class);
	public InfoWebSocketHandler infoWebSocketHandler(){
		return new InfoWebSocketHandler();
	}
	
	@Override
	public Pagination findListByPage(int pageSize, int pageNo, MessageVo message) {
		Pagination pagination = new Pagination();
	    pagination.setPageNo(pageNo); //当前页码
	    pagination.setPageSize(pageSize);  //每页显示多少行
	    List<MessageVo> list = messageDao.findListByPage(message,pagination);
	    pagination.setList(list);
	    return pagination;
	}

	@Override
	public ResponseParam findMessageCount() {
		ResponseParam res = new ResponseParam();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		MessageVo message = new MessageVo();
		message.setReceiverId(user.getId());
		try{
			int count = messageDao.findMessageCount(message);
			res.setCode(200);
			res.setMsg("success");
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("count", count);
			res.setData(data);
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("获取消息总数失败");
			res.setData(null);
			return res;
		}
		return res;
	}
	
	//发送消息服务
	@Override
	public void sendMessageToUser(Integer userId, String msg, String type,String url) {
		MessageVo message = new MessageVo();
		message.setIsRead(0);
		message.setCreateTime(new Date());
		message.setReceiverId(userId);
		message.setContent(msg);
		
		switch(type){
			case MessageUtil.MESSAGE_TYPE_COMMENT:
			case MessageUtil.MESSAGE_TYPE_RECEIVETASK:
			case MessageUtil.MESSAGE_TYPE_REVIEWTASK:
			case MessageUtil.MESSAGE_TYPE_REVIEWTASK_RESULT:
			case MessageUtil.MESSAGE_TYPE_RECEIVEDTASK:
				message.setType(type);
				break;
			default:
				message.setType(type+" is lack of standardization");
				break;
			}
		if(url!=null){
			message.setMessageUrl(url);	
		}
		else{
			switch(type){
			case MessageUtil.MESSAGE_TYPE_COMMENT:
				message.setMessageUrl(MessageUtil.COMMENT_PATH);
				break;
			case MessageUtil.MESSAGE_TYPE_RECEIVETASK:
				message.setMessageUrl(MessageUtil.RECEIVETASK_PATH);
				break;
			case MessageUtil.MESSAGE_TYPE_REVIEWTASK:
				message.setMessageUrl(MessageUtil.REVIEWTASK_PATH);
				break;
			case MessageUtil.MESSAGE_TYPE_REVIEWTASK_RESULT:
				message.setMessageUrl("path is null");
				break;
			case MessageUtil.MESSAGE_TYPE_RECEIVEDTASK:
				message.setMessageUrl(MessageUtil.RECEIVEDTASK_PATH);
				break;
			default:
				message.setMessageUrl("path is null");
			}
		}
		
		messageDao.addMessage(message);
		infoWebSocketHandler().sendMessageToUser(userId, new TextMessage(msg));
		
	}

	@Override
	public ResponseParam changeMessage(MessageVo messageVo) {
		ResponseParam res = new ResponseParam();
		if(messageVo!=null&&messageVo.getId()!=null&&messageVo.getId()!=0){
			try{
				messageDao.changeMessage(messageVo);
				res.setCode(200);
				res.setMsg("success");
				res.setData(null);
			}catch(Exception e){
				e.printStackTrace();
				res.setCode(500);
				res.setMsg("更新message失败");
				res.setData(null);
				return res;
			}
		}else{
			res.setCode(400);
			res.setMsg("不存在这样的message");
			res.setData(null);
			return res;
		}
		return res;
	}

}
