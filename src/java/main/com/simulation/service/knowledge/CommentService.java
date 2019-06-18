package com.simulation.service.knowledge;

import java.util.List;

import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.Comment;
import com.simulation.vo.knowledge.CommentVo;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年4月3日 下午9:55:30  
*/
public interface CommentService {
	List<CommentVo> findHotComment(int paperId);
	int addPraise(int commentId,int userId);
	void addComment(Comment comment);
	void deleteComment(int commentId);
	Pagination findListByPage(int rows,int page,boolean hot,boolean recent,CommentVo commentVo);
	Pagination findMyListByPage(int rows,int page,List<String> keyWordList,boolean hot,boolean recent,CommentVo commentVo);
	void editMyCommentById(Comment comment);
}
