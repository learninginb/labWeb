package com.simulation.service.knowledge.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import com.simulation.common.page.Pagination;
import com.simulation.dao.mybatis.basedata.MessageDao;
import com.simulation.dao.mybatis.knowledge.CommentDao;
import com.simulation.model.knowledge.Comment;
import com.simulation.service.knowledge.CommentService;
import com.simulation.vo.basedata.MessageVo;
import com.simulation.vo.knowledge.CommentVo;
import com.simulation.vo.sys.SysUserActiveVo;
import com.simulation.websocket.config.MessageUtil;
import com.simulation.websocket.handler.InfoWebSocketHandler;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年4月3日 下午9:59:59  
*/

@Service("commentService")
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private MessageDao messageDao;
	

	public InfoWebSocketHandler infoWebSocketHandler(){
		return new InfoWebSocketHandler();
	}
	
	@Override
	public List<CommentVo> findHotComment(int paperId) {
		// 查询评论
		
		return commentDao.findHotComment(paperId);
	}
	
	@Transactional
	@Override
	public int addPraise(int commentId,int userId) {
		// 点赞评论
		Integer id = commentDao.praiseIsExit(commentId, userId);
		if(id!=null&&id!=0){
			commentDao.subtractPraise(commentId);
			commentDao.deleteHistory(commentId, userId);
		}
		else{
			commentDao.addPraise(commentId);
			commentDao.addHistory(commentId, userId);
			//发送点赞消息
			SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
			int receiverId = commentDao.findCommentById(commentId).getUser_id();
			if(receiverId!=userId){
				MessageVo message = new MessageVo();
				message.setReceiverId(receiverId);
				message.setType(MessageUtil.MESSAGE_TYPE_COMMENT);
				message.setMessageUrl(MessageUtil.COMMENT_PATH);
				message.setCreateTime(new Date());
				String msg = "用户"+user.getUserName()+"点赞了你的评论";
				message.setContent(msg);
				message.setIsRead(0);
				messageDao.addMessage(message);
				infoWebSocketHandler().sendMessageToUser(receiverId, new TextMessage(msg));
			}
		}
		return commentDao.findPraiseCount(commentId);
	}

	@Override
	@Transactional
	public void addComment(Comment comment) {
		Date date = new Date();
		comment.setCreate_time(date);
		comment.setUpdate_time(date);
		comment.setStatus("1");
		comment.setPraise_count(0);
		commentDao.addComment(comment);
	}
	
	@Transactional
	@Override
	public void deleteComment(int commentId) {
		commentDao.deleteComment(commentId);
		
	}

	@Override
	public Pagination findListByPage(int rows, int page,boolean hot,boolean recent, CommentVo vo) {
		Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<CommentVo>  list = this.commentDao.findpage(vo,pagination,null,hot,recent);
	    //查询点赞记录
	    int userId = ((SysUserActiveVo) SecurityUtils.getSubject().getPrincipal()).getId();
	    for(CommentVo cvo : list){
	    	int key = commentDao.praiseIsExit(cvo.getId(),userId);
	    	if (key!=0){
	    		cvo.setPraised(true);
	    	}
	    	else
	    		cvo.setPraised(false);
	    }
	    pagination.setList(list);
	    //获取总记录数
	    pagination.setTotalCount(commentDao.getCommentCount(vo.getPaper_id(), vo.getPaperName()));
		return pagination;
	}
	
	//对我的评论进行分页
	@Override
	public Pagination findMyListByPage(int rows,int page,List<String> keyWordList,boolean hot,boolean recent,CommentVo vo){
		Pagination pagination = new Pagination();
		pagination.setPageNo(page);
		pagination.setPageSize(rows);
		int userId = ((SysUserActiveVo) SecurityUtils.getSubject().getPrincipal()).getId();
		vo.setUser_id(userId);
		List<CommentVo> list = null;
		try{
			 list = this.commentDao.findpage(vo, pagination, keyWordList, hot, recent);
			for(CommentVo cvo : list){
		    	int key = commentDao.praiseIsExit(cvo.getId(),userId);
		    	if (key!=0){
		    		cvo.setPraised(true);
		    	}
		    	else
		    		cvo.setPraised(false);
		    }
		}catch(Exception e){
			
		}
		pagination.setList(list);
		pagination.setTotalCount(commentDao.getMyCommentCount(vo, pagination, keyWordList));
		return pagination;
	}
	
	@Transactional
	@Override
	public void editMyCommentById(Comment comment) {
		try{
			commentDao.editMyCommentById(comment);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
