package com.simulation.service.knowledge;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.Paper;
import com.simulation.vo.knowledge.PaperVo;


	public interface PaperService extends BaseMybatisService<Paper, Integer> {

		void deleteIds(String ids);

		List<Paper> selectList(Paper po);

		Pagination findListByPage(int rows, int page, PaperVo vo);

		String save(Paper po);
		
		String deleteByIds(String ids);

		HSSFWorkbook export();

	}
