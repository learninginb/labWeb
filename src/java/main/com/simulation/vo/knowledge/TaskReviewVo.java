package com.simulation.vo.knowledge;

import com.simulation.model.knowledge.TaskView;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月7日 下午9:27:05  
*/
public class TaskReviewVo extends TaskView {
	private String taskTheme;
	private String pic;
	private String distributor;
	private String executor;
	public String getTaskTheme() {
		return taskTheme;
	}
	public void setTaskTheme(String taskTheme) {
		this.taskTheme = taskTheme;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getDistributor() {
		return distributor;
	}
	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	public String getExecutor() {
		return executor;
	}
	public void setExecutor(String executor) {
		this.executor = executor;
	}
	
	

}
