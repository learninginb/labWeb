package com.simulation.controller.admin.knowledge;

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
import com.simulation.model.knowledge.KnowledgeCollection;
import com.simulation.service.knowledge.KnowledgeCollectionService;
import com.simulation.service.knowledge.KnowledgeReadingService;
import com.simulation.vo.knowledge.KnowledgeCollectionVo;

@Controller
@RequestMapping("/knowledgeCollectionController") 
public class KnowledgeCollectionController extends BaseMybatisController {
	
	private final static Logger log= Logger.getLogger(KnowledgeCollectionController.class);

	@Autowired(required=false) 
	private KnowledgeCollectionService knowledgeCollectionService; 
	
	@Autowired(required=false) 
	private KnowledgeReadingService knowledgeReadingService; 
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toList") 
	public ModelAndView toList()throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgecollection/knowledgecollection_list");
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
	public DataGrid list (KnowledgeCollectionVo vo) throws Exception{
		int pageNo=PageUtils.getPage();
		int pageSize=PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		Pagination pagination = knowledgeCollectionService.findListByPage(pageSize, pageNo, vo);
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
		ModelAndView mv = new ModelAndView("knowledge/knowledgecollection/knowledgecollection_add");
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
	public JsonData  add(@ModelAttribute KnowledgeCollection po) {
		JsonData json = new JsonData();
		try {
			this.knowledgeCollectionService.insert(po);
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
			this.knowledgeCollectionService.deleteIds(ids);
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
	public ModelAndView findById(@ModelAttribute KnowledgeCollectionVo vo)throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgecollection/knowledgecollection_view");
		KnowledgeCollection po = this.knowledgeCollectionService.selectByPrimaryKey(vo.getId());
		mv.addObject("vo", po);
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
	public ModelAndView editById(@ModelAttribute KnowledgeCollection po)throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgecollection/knowledgecollection_edit");
		po = this.knowledgeCollectionService.selectByPrimaryKey(po.getId());
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
	public JsonData editSubmit(@ModelAttribute KnowledgeCollection po)throws Exception{
		JsonData json = new JsonData();
		try {
			this.knowledgeCollectionService.updateByPrimaryKeySelective(po);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 收藏
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/collection")
	@ResponseBody
	public JsonData collection(@ModelAttribute KnowledgeCollection po)throws Exception{
		JsonData json = new JsonData();
		try {
			//添加收藏纪录或更新收藏状态为有效,阅读后收藏操作设置为收藏(最新一条纪录)
			this.knowledgeCollectionService.collection(po);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 取消收藏
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cancelCollection")
	@ResponseBody
	public JsonData cancelCollection(@ModelAttribute KnowledgeCollection po)throws Exception{
		JsonData json = new JsonData();
		try {
			//取消收藏并设置阅读后收藏纪录为取消
			this.knowledgeCollectionService.cancelCollection(po);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}