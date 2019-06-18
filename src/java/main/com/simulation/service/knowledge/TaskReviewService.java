package com.simulation.service.knowledge;

import com.simulation.model.basedata.ResponseParam;
import com.simulation.vo.knowledge.TaskReviewVo;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月7日 下午9:55:09  
*/
public interface TaskReviewService {
	public boolean addReview(TaskReviewVo vo);
	public ResponseParam getReview(TaskReviewVo vo);
}
