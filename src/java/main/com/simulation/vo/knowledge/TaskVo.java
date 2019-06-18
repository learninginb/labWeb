package com.simulation.vo.knowledge;

import java.util.List;

import com.simulation.model.knowledge.Task;

/**
* @author 作者 ：spxin
* @version 创建时间：2019年5月1日 下午5:33:31
*/
public class TaskVo extends Task {
	private String distributor ;
	private String pic;
	private String executor;
	private String status;
	private List<TaskVo> sonList;//子任务集合
	public String getDistributor() { return distributor; }
	public void setDistributor(String distributor) { this.distributor = distributor; }
	public String getPic() { return pic; }
	public void setPic(String pic) { this.pic = pic; }
	public String getExecutor() { return executor; }
	public void setExecutor(String executor) { this.executor = executor; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public List<TaskVo> getSonList() { return sonList; }
	public void setSonList(List<TaskVo> sonList) { this.sonList = sonList; }


}
