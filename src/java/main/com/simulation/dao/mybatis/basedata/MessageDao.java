package com.simulation.dao.mybatis.basedata;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.page.Pagination;
import com.simulation.vo.basedata.MessageVo;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月9日 下午1:07:59  
*/
public interface MessageDao {
	public void addMessage(@Param("message")MessageVo message);
	public List<MessageVo> findMessage(@Param("message")MessageVo message);
	public int findMessageCount(@Param("message")MessageVo message);
	public List<MessageVo> findListByPage(@Param("message")MessageVo message,@Param("page")Pagination page);
	public void changeMessage(@Param("message")MessageVo message);
}
