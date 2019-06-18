package com.simulation.model.knowledge;

import java.io.Serializable;
import java.util.Date;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月16日 上午8:59:08  
*/
public class News implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String newsName;
	private Integer authorId;
	private Date createTime;
	private String location;
	private String keyWords;
	private String content;
	private Integer statusId;
	private Integer importance;
	private Integer imageId;
	private Integer readcount;

	public Integer getReadcount() {
		return readcount;
	}

	public void setReadcount(Integer readcount) {
		this.readcount = readcount;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNewsName() {
		return newsName;
	}
	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public Integer getImportance() {
		return importance;
	}
	public void setImportance(Integer importance) {
		this.importance = importance;
	}
	public Integer getImageId() {
		return imageId;
	}
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}
	
	

}
