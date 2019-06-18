package com.simulation.dao.mybatis.knowledge;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.page.Pagination;
import com.simulation.vo.knowledge.NewsVo;


/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月16日 上午9:10:43  
*/
public interface NewsDao {
	public void insertNews(@Param("vo")NewsVo vo);
	public List<NewsVo> findListByPage(@Param("vo") NewsVo vo,@Param("page") Pagination page);
	public NewsVo getNewsById(@Param("id")int id);
	public void deleteById(@Param("id") int id);
	public void editorNews(@Param("newsVo")NewsVo newsVo);
	public List<NewsVo> findAllList(@Param("page")Pagination page);
	public int findAllListCount();
	public List<NewsVo> findPageByCreateTime(@Param("createTime")String createTime,@Param("page")Pagination page);
	public void addReadcount(@Param("id")int id);
	public void changeStatusId(@Param("id")int id,@Param("statusId")int statusId);
}
