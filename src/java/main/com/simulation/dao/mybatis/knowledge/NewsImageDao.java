package com.simulation.dao.mybatis.knowledge;

import org.apache.ibatis.annotations.Param;

import com.simulation.model.knowledge.NewsImage;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月17日 下午10:58:53  
*/
public interface NewsImageDao {
	void addNewsImage(@Param("image")NewsImage image);
	NewsImage getNewsImageByImagePath(@Param("imagePath")String imagePath);
	NewsImage findNewsImageById(@Param("id")int id);
	void deleteById(@Param("id")int id);
	void editorNewsImage(@Param("newsImage")NewsImage newsImage);
}
