package com.simulation.controller.admin.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.simulation.model.knowledge.KnowledgeType;
import com.simulation.service.knowledge.KnowledgeTypeService;
import com.simulation.vo.knowledge.KnowledgeTypeVo;
import com.simulation.vo.tree.TreeNode;

@Controller
@RequestMapping("/knowledgeTypeController") 
public class KnowledgeTypeController extends BaseMybatisController {
	
	private final static Logger log= Logger.getLogger(KnowledgeTypeController.class);

	@Autowired(required=false) 
	private KnowledgeTypeService knowledgeTypeService; 
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toList") 
	public ModelAndView toList()throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgetype/knowledgetype_list");
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
	public DataGrid list (KnowledgeTypeVo vo) throws Exception{
		int pageNo=PageUtils.getPage();
		int pageSize=PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		Pagination pagination = knowledgeTypeService.findListByPage(pageSize, pageNo, vo);
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
		ModelAndView mv = new ModelAndView("knowledge/knowledgetype/knowledgetype_add");
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
	public JsonData  add(@ModelAttribute KnowledgeType po) {
		JsonData json = new JsonData();
		try {
			//this.knowledgeTypeService.insert(po);
			String msg = this.knowledgeTypeService.saveKnowledgeType(po);
			if(msg!=null){
				json.setSuccess(false);
				json.setMsg(msg);
				return json;
			}
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
			//this.knowledgeTypeService.deleteIds(ids);
			String msg = this.knowledgeTypeService.deleteObjs(ids);
			if(msg!=null){
				json.setSuccess(false);
				json.setMsg(msg);
				return json;
			}
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
	public ModelAndView findById(@ModelAttribute KnowledgeTypeVo vo)throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgetype/knowledgetype_view");
		KnowledgeType po = this.knowledgeTypeService.selectByPrimaryKey(vo.getId());
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
	public ModelAndView editById(@ModelAttribute KnowledgeType po)throws Exception{
		ModelAndView mv = new ModelAndView("knowledge/knowledgetype/knowledgetype_edit");
		po = this.knowledgeTypeService.selectByPrimaryKey(po.getId());
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
	public JsonData editSubmit(@ModelAttribute KnowledgeType po)throws Exception{
		JsonData json = new JsonData();
		try {
			String msg = this.knowledgeTypeService.saveKnowledgeType(po);
			if(msg!=null){
				json.setSuccess(false);
				json.setMsg(msg);
				return json;
			}
			//this.knowledgeTypeService.updateByPrimaryKeySelective(po);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 查找json对象
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findJsonById")
	@ResponseBody
	public JsonData findJsonById(@ModelAttribute KnowledgeType po)throws Exception{
		JsonData json = new JsonData();
		try {
			po = this.knowledgeTypeService.selectByPrimaryKey(po.getId());
			json.setSuccess(true);
			json.setObj(po);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/loadTree") 
	@ResponseBody
	public List<TreeNode> loadTree(Integer id){
		if(id==null)
		{
			id =Integer.valueOf(0);
		}
		List<TreeNode> treenodes = new ArrayList<TreeNode>();
		
		List<KnowledgeType> list = this.knowledgeTypeService.selectListByParentId(id);
		for(KnowledgeType knowledgeType :list){
			TreeNode treeNode = new TreeNode();
			Map<String,Object> attributes = new HashMap<String,Object>();
			treeNode.setId(knowledgeType.getId()+"");
			treeNode.setText(knowledgeType.getKnowledgeType());
			treeNode.setpId(knowledgeType.getParentId()+"");
			treeNode.setState("closed");
			
			List<KnowledgeType> listChilden = knowledgeTypeService.selectListByParentId(knowledgeType.getId());
			if(listChilden.size() == 0){
				treeNode.setIsParent(false);
			}
			treeNode.setAttributes(attributes);
			
			treenodes.add(treeNode);
		}
		return treenodes;
	}
}