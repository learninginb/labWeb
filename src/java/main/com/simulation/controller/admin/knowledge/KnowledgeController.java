package com.simulation.controller.admin.knowledge;

import java.math.BigDecimal;
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
import com.simulation.core.shiro.ShiroUser;
import com.simulation.core.util.PageUtils;
import com.simulation.model.knowledge.Knowledge;
import com.simulation.model.knowledge.KnowledgeType;
import com.simulation.service.knowledge.KnowledgeHistoryService;
import com.simulation.service.knowledge.KnowledgeService;
import com.simulation.service.knowledge.KnowledgeTypeService;
import com.simulation.vo.knowledge.KnowledgeVo;

@Controller
@RequestMapping("/knowledgeController") 
public class KnowledgeController extends BaseMybatisController {
	
	private final static Logger log= Logger.getLogger(KnowledgeController.class);

	@Autowired(required=false)
	private KnowledgeService knowledgeService;
	
	@Autowired(required=false)
	private KnowledgeTypeService knowledgeTypeService; 
	
	@Autowired(required=false)
	private KnowledgeHistoryService knowledgeHistoryService; 
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toList") 
	public ModelAndView toList()throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledge/knowledge_list");
		return mv;
	}
	
	/**
	 * 知识发布--列出所有自己的知识（不包括回收站）
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/tomyList") 
	public ModelAndView tomyList()throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledge/knowledge_myList");
		Integer currentUser = ShiroUser.getUserId();
		mv.addObject("currentUser", currentUser);
		return mv;
	}
	
	/**
	 * 回收站列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toBinList")
	public ModelAndView toBinList()throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledge/knowledge_binList");
		mv.addObject("knowledgeState", Knowledge.knowledge_state_07);
		return mv;
	}
	
	/**
	 * 未审核列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toNoauditList")
	public ModelAndView toNoauditList()throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledge/knowledge_noAuditList");
		mv.addObject("knowledgeState", Knowledge.knowledge_state_00);
		return mv;
	}
	
	/**
	 * 已发布列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toPublishedList")
	public ModelAndView toPublishedList()throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledge/knowledge_publishedList");
		mv.addObject("knowledgeState", Knowledge.knowledge_state_01);
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
	public DataGrid list (KnowledgeVo vo) throws Exception{
		int pageNo=PageUtils.getPage();
		int pageSize=PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		Pagination pagination = knowledgeService.findListByPage(pageSize, pageNo, vo);
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
		//加载所有的知识分类填充页面select
		List<KnowledgeType> knowledgeTypeList = knowledgeTypeService.findAll();
		ModelAndView mv = new ModelAndView("knowledge/knowledge/knowledge_add");
		mv.addObject("knowledgeTypeList", knowledgeTypeList);
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
	public JsonData  add(@ModelAttribute Knowledge po) {
		JsonData json = new JsonData();
		try {
			this.knowledgeService.insert(po);
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
			//永久删除这条知识
			this.knowledgeService.deleteIds(ids);
			//删除对应的历史版本
			this.knowledgeHistoryService.deleteByKnowledgeIds(ids);
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
	public ModelAndView findById(@ModelAttribute KnowledgeVo vo)throws Exception{
		//加载所有的知识分类填充页面select
		List<KnowledgeType> knowledgeTypeList = knowledgeTypeService.findAll();
		ModelAndView mv = new ModelAndView("knowledge/knowledge/knowledge_view");
		//Knowledge po = this.knowledgeService.selectByPrimaryKey(vo.getId());
		KnowledgeVo po = this.knowledgeService.selectVoByPrimaryKey(vo.getId());
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
	public ModelAndView editById(@ModelAttribute Knowledge po)throws Exception{
		//加载所有的知识分类填充页面select
		List<KnowledgeType> knowledgeTypeList = knowledgeTypeService.findAll();
		ModelAndView mv = new ModelAndView("knowledge/knowledge/knowledge_edit");
		po = this.knowledgeService.selectByPrimaryKey(po.getId());
		BigDecimal a = new BigDecimal(po.getKnowledgeVersion()+0.1);
		po.setKnowledgeVersion(a.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
		mv.addObject("vo", po);
		mv.addObject("knowledgeTypeList", knowledgeTypeList);
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
	public JsonData editSubmit(@ModelAttribute Knowledge po)throws Exception{
		JsonData json = new JsonData();
		try {
			String msg = this.knowledgeService.upadateKnowledge(po);
			if(msg!=null){
				json.setSuccess(false);
				json.setMsg(msg);
				return json;
			}
			/*//修改知识
			this.knowledgeService.updateByPrimaryKeySelective(po);*/
			//添加知识历史版本
			this.knowledgeHistoryService.addVersion(po);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/updateState")
	@ResponseBody
	public JsonData updateState(String  ids,Integer knowledgeState) {
		JsonData json = new JsonData();
		try {
			this.knowledgeService.updateState(ids,knowledgeState);
			json.setSuccess(true);
			json.setMsg("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 知识阅读
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping("/readById")
	public ModelAndView readById(@ModelAttribute KnowledgeVo vo)throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledge/knowledge_reading");
		KnowledgeVo po = this.knowledgeService.readByPrimaryKey(vo.getId());
		mv.addObject("vo", po);
		return mv;
	}
}