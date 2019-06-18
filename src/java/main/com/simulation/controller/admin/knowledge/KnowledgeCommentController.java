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
import com.simulation.model.knowledge.KnowledgeComment;
import com.simulation.service.knowledge.KnowledgeCommentService;
import com.simulation.vo.knowledge.KnowledgeCommentVo;

@Controller
@RequestMapping("/knowledgeCommentController") 
public class KnowledgeCommentController extends BaseMybatisController {
	
	private final static Logger log= Logger.getLogger(KnowledgeCommentController.class);

	@Autowired(required=false) 
	private KnowledgeCommentService knowledgeCommentService; 
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toList") 
	public ModelAndView toList()throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgecomment/knowledgecomment_list");
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
	public DataGrid list (KnowledgeCommentVo vo) throws Exception{
		int pageNo=PageUtils.getPage();
		int pageSize=PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		Pagination pagination = knowledgeCommentService.findListByPage(pageSize, pageNo, vo);
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
		ModelAndView mv = new ModelAndView("knowledge/knowledgecomment/knowledgecomment_add");
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
	public JsonData  add(@ModelAttribute KnowledgeComment po) {
		JsonData json = new JsonData();
		try {
			//校验
			KnowledgeComment comment = this.knowledgeCommentService.findByKnowledgeId(po.getKnowledgeId());
			if(comment!=null){
				json.setSuccess(false);
				json.setMsg("您已经对此知识做过评论，不能再次评论!");
				return json;
			}
			//评论
			this.knowledgeCommentService.comment(po);
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
			this.knowledgeCommentService.deleteIds(ids);
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
	public ModelAndView findById(@ModelAttribute KnowledgeCommentVo vo)throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgecomment/knowledgecomment_view");
		KnowledgeComment po = this.knowledgeCommentService.selectByPrimaryKey(vo.getId());
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
	public ModelAndView editById(@ModelAttribute KnowledgeComment po)throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgecomment/knowledgecomment_edit");
		po = this.knowledgeCommentService.selectByPrimaryKey(po.getId());
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
	public JsonData editSubmit(@ModelAttribute KnowledgeComment po)throws Exception{
		JsonData json = new JsonData();
		try {
			this.knowledgeCommentService.updateByPrimaryKeySelective(po);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 评论验证
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validate")
	@ResponseBody
	public JsonData validate(Integer knowledgeId)throws Exception{
		JsonData json = new JsonData();
		try {
			KnowledgeComment po = this.knowledgeCommentService.findByKnowledgeId(knowledgeId);
			json.setObj(po);
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}