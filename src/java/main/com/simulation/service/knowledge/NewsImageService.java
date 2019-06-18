package com.simulation.service.knowledge;

import java.util.Date;

import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.NewsImage;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月17日 下午10:31:16  
*/
public interface NewsImageService {
	public void addImage(String imageUrl,String imageName);
	public NewsImage getImageByImagePath(String imagePath);
	
}
