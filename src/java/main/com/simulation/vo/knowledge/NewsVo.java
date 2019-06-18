package com.simulation.vo.knowledge;

import com.simulation.model.knowledge.News;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月16日 上午9:04:28  
*/
public class NewsVo extends News {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String author;
	private String status;
	private String imageUrl;
	private String imageName;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
}
