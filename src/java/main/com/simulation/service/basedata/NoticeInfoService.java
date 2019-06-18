package com.simulation.service.basedata;

import java.util.List;


import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.basedata.NoticeInfo;
import com.simulation.vo.basedata.NoticeInfoVo;

public interface NoticeInfoService extends BaseMybatisService<NoticeInfo, Integer> {

	void deleteIds(String ids);

	List<NoticeInfo> selectList(NoticeInfo po);

	Pagination findListByPage(int rows, int page, NoticeInfoVo vo);

	String save(NoticeInfo po);
	
	String deleteByIds(String ids);

//	HSSFWorkbook export();

}
