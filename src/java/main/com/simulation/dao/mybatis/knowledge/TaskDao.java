package com.simulation.dao.mybatis.knowledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.Task;
import com.simulation.vo.knowledge.TaskVo;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月1日 上午9:37:45  
*/
public interface TaskDao {
	public void addTaskIntoPlan(@Param("task")Task task);
	public void addSonTask(@Param("task")Task task);
	public void deleteTask(@Param("vo")TaskVo vo);
	public void editTask(@Param("vo")TaskVo vo);
	public List<TaskVo> findManagerListByPage(@Param("task")Task task,@Param("page")Pagination page);
	public List<TaskVo> findFinishListByPage(@Param("task")Task task,@Param("page")Pagination page);
	public List<TaskVo> findUnfinishListByPage(@Param("task")Task task,@Param("page")Pagination page);
	public List<TaskVo> findReviewListByPage(@Param("task")Task task,@Param("page")Pagination page);
	public List<TaskVo> findReceiveListByPage(@Param("task")Task task,@Param("page")Pagination page);
	public List<TaskVo> findRecordListByPage(@Param("task")Task task,@Param("page")Pagination page);
	public TaskVo findPlanTaskById(@Param("id")int id);
	public List<TaskVo> findSonTask(@Param("id")int id);
	public void changeStatusById(@Param("statusId")int statusId,@Param("vo")TaskVo vo);
	public List<TaskVo> findCycleTask(@Param("task")Task task);

}
