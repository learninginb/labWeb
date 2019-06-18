package com.simulation.dao.mybatis.basedata;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.basedata.NoticeInfo;
import com.simulation.vo.basedata.NoticeInfoVo;

public interface NoticeInfoDao extends BaseMybatisDao<NoticeInfo, Integer>{

	
	void deleteByIds(@Param("ids") List<Integer> ids);
	
	List<NoticeInfo> selectList(NoticeInfo po);

	List<NoticeInfoVo> findListByPage(@Param("vo") NoticeInfoVo vo,
			@Param("page") Pagination page);

	
}
