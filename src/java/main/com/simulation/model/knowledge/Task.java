package com.simulation.model.knowledge;

import java.io.Serializable;
import java.util.Date;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年4月28日 下午8:53:15  
*/
public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer parentId;
	private String type;
	private String theme;
	private String description;
	private Integer picId;
	private Integer distributorId ;
	private Integer executorId;
	private Date beginTime;
	private Date finishTime;
	private Integer statusId;
	private String fileUrl;
	private Integer importance;
	private Boolean available;
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	public Integer getParentId() { return parentId; }
	public void setParentId(Integer parentId) { this.parentId = parentId; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getTheme() { return theme; }
	public void setTheme(String theme) { this.theme = theme; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public Integer getPicId() { return picId; }
	public void setPicId(Integer picId) { this.picId = picId; }
	public Integer getDistributorId() { return distributorId; }
	public void setDistributorId(Integer distributorId) { this.distributorId = distributorId; }
	public Integer getExecutorId() { return executorId; }
	public void setExecutorId(Integer executorId) { this.executorId = executorId; }
	public Date getBeginTime() { return beginTime; }
	public void setBeginTime(Date beginTime) { this.beginTime = beginTime; }
	public Date getFinishTime() { return finishTime; }
	public void setFinishTime(Date finishTime) { this.finishTime = finishTime; }
	public Integer getStatusId() { return statusId; }
	public void setStatusId(Integer statusId) { this.statusId = statusId; }
	public String getFileUrl() { return fileUrl; }
	public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
	public Integer getImportance() { return importance; }
	public void setImportance(Integer importance) { this.importance = importance; }
	public Boolean getAvailable() { return available; }
	public void setAvailable(Boolean available) { this.available = available; }
}
