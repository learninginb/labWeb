package com.simulation.service.knowledge.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simulation.common.page.Pagination;
import com.simulation.dao.mybatis.knowledge.NewsImageDao;
import com.simulation.model.knowledge.NewsImage;
import com.simulation.service.knowledge.NewsImageService;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月17日 下午10:58:26  
*/
@Service
public class NewsImageServiceImpl implements NewsImageService {
	@Autowired
	private NewsImageDao newsImageDao;
	
	@Override
	public void addImage(String imageUrl, String imageName) {
		NewsImage image = new NewsImage();
		image.setImageName(imageName);
		image.setImageUrl(imageUrl);
		newsImageDao.addNewsImage(image);

	}

	@Override
	public NewsImage getImageByImagePath(String imagePath) {
		
		return newsImageDao.getNewsImageByImagePath(imagePath);
	}


}
