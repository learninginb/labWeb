package com.simulation.dao.mybatis.knowledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.Comment;
import com.simulation.vo.knowledge.CommentVo;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年4月3日 下午7:27:33  
*/
public interface CommentDao {
//	查询文献相关的评论
	List<CommentVo> findHotComment(@Param("paperId")int paperId);
	void addPraise(@Param("commentId")int commentId);
	void subtractPraise(@Param("commentId")int commentId);
	void addComment(@Param("comment")Comment comment);
	void deleteComment(@Param("commentId") int commentId);
	int findPraiseCount(@Param("commentId") int commentId);
	Integer praiseIsExit(@Param("commentId")int commentId,@Param("userId")int userId);
	void deleteHistory(@Param("commentId")int commentId,@Param("userId")int userId);
	void addHistory(@Param("commentId")int commentId,@Param("userId")int userId);
	List<CommentVo> findpage(@Param("vo")CommentVo vo,@Param("page")Pagination page,@Param("keyWordList")List<String> keyWordList,@Param("hot")boolean hot,@Param("recent")boolean recent);
	int getCommentCount(@Param("paperId")int paperId,@Param("paperName")String paperName);
	int getMyCommentCount(@Param("vo")CommentVo vo,@Param("page")Pagination page,@Param("keyWordList")List<String> keyWordList);
	void editMyCommentById(@Param("comment")Comment comment);
	Comment findCommentById(@Param("id")int id);
}
