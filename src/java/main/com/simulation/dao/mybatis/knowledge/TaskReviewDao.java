package com.simulation.dao.mybatis.knowledge;

import org.apache.ibatis.annotations.Param;

import com.simulation.vo.knowledge.TaskReviewVo;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月7日 下午9:57:25  
*/
public interface TaskReviewDao {
	public void addTaskReview(@Param("vo")TaskReviewVo vo);
	public TaskReviewVo findReviewByTaskId(@Param("vo")TaskReviewVo vo);
}
