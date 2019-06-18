package com.simulation.controller.admin.knowledge;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.simulation.common.base.BaseMybatisController;
import com.simulation.common.page.Pagination;
import com.simulation.common.web.DataGrid;
import com.simulation.common.web.JsonData;
import com.simulation.core.util.PageUtils;
import com.simulation.model.knowledge.KnowledgeHistory;
import com.simulation.model.knowledge.KnowledgeType;
import com.simulation.service.knowledge.KnowledgeHistoryService;
import com.simulation.service.knowledge.KnowledgeTypeService;
import com.simulation.vo.knowledge.KnowledgeHistoryVo;

@Controller
@RequestMapping("/knowledgeHistoryController") 
public class KnowledgeHistoryController extends BaseMybatisController {
	
	private final static Logger log= Logger.getLogger(KnowledgeHistoryController.class);

	@Autowired(required=false) 
	private KnowledgeHistoryService knowledgeHistoryService; 
	
	@Autowired(required=false) 
	private KnowledgeTypeService knowledgeTypeService; 
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toList") 
	public ModelAndView toList(KnowledgeHistoryVo vo)throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgehistory/knowledgehistory_list");
		mv.addObject("vo", vo);
		return mv;
	}

	/**
	 * 请求列表数据
	 * @param request
	 * @param vo 查询对象
	 * @param rows  每页显示多少行
	 * @param page  当前页码
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list") 
	@ResponseBody
	public DataGrid list (KnowledgeHistoryVo vo) throws Exception{
		int pageNo=PageUtils.getPage();
		int pageSize=PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		Pagination pagination = knowledgeHistoryService.findListByPage(pageSize, pageNo, vo);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}
		
	/**
	 * 打开新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAdd") 
	public ModelAndView  toAdd() throws Exception{
		log.debug("打开新增页面");
		ModelAndView mv = new ModelAndView("knowledge/knowledgehistory/knowledgehistory_add");
		return mv;
	}
	
	/**
	 * 新增方法
	 * @param request
	 * @param po
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add") 
	@ResponseBody
	public JsonData  add(@ModelAttribute KnowledgeHistory po) {
		JsonData json = new JsonData();
		try {
			this.knowledgeHistoryService.insert(po);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 根据ID删除对象
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public JsonData deleteById(String  ids) {
		JsonData json = new JsonData();
		try {
			this.knowledgeHistoryService.deleteIds(ids);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 根据ID找到对象
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(@ModelAttribute KnowledgeHistoryVo vo)throws Exception{
		//加载所有的知识分类填充页面select
		List<KnowledgeType> knowledgeTypeList = knowledgeTypeService.findAll();
		ModelAndView mv = new ModelAndView("knowledge/knowledgehistory/knowledgehistory_view");
		KnowledgeHistory po = this.knowledgeHistoryService.selectByPrimaryKey(vo.getId());
		mv.addObject("vo", po);
		mv.addObject("knowledgeTypeList", knowledgeTypeList);
		return mv;
	}

	/**
	 * 跳转到更新页面
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editById")
	public ModelAndView editById(@ModelAttribute KnowledgeHistory po)throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgehistory/knowledgehistory_edit");
		po = this.knowledgeHistoryService.selectByPrimaryKey(po.getId());
		mv.addObject("vo", po);
		return mv;
	}
	
	/**
	 * 更新提交
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editSubmit")
	@ResponseBody
	public JsonData editSubmit(@ModelAttribute KnowledgeHistory po)throws Exception{
		JsonData json = new JsonData();
		try {
			this.knowledgeHistoryService.updateByPrimaryKeySelective(po);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}