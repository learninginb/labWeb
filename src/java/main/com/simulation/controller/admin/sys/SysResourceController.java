package com.simulation.controller.admin.sys;

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
import com.simulation.model.sys.SysResource;
import com.simulation.service.sys.SysResourceService;
import com.simulation.vo.sys.SysResourceVo;
import com.simulation.vo.tree.TreeNode;

@Controller
@RequestMapping("/sysResourceController") 
public class SysResourceController extends BaseMybatisController {
	
	private final static Logger log= Logger.getLogger(SysResourceController.class);

	@Autowired(required=false) 
	private SysResourceService sysResourceService; 
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toList") 
	public ModelAndView toList()throws Exception{
		ModelAndView mv = new ModelAndView("sys/sysresource/sysresource_list");
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
	public DataGrid list (SysResourceVo vo) throws Exception{
		int pageNo=PageUtils.getPage();
		int pageSize=PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		Pagination pagination = sysResourceService.findListByPage(pageSize, pageNo, vo);
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
	public ModelAndView  toAdd(Integer parentId,Integer orderNum) throws Exception{
		log.debug("打开新增页面");
		ModelAndView mv = new ModelAndView("sys/sysresource/sysresource_add");
		SysResource sysResource = sysResourceService.selectByPrimaryKey(parentId);
		sysResource.setLevel(sysResource.getLevel()+1);
		sysResource.setOrderNum(orderNum+1);
		mv.addObject("vo",sysResource);
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
	public JsonData  add(@ModelAttribute SysResource po,boolean createButton) {
		JsonData json = new JsonData();
		try {
			this.sysResourceService.saveAndCreateRes(po,createButton);
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
			this.sysResourceService.deleteIds(ids);
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
	public ModelAndView findById(@ModelAttribute SysResourceVo vo)throws Exception{
		ModelAndView mv = new ModelAndView("sys/sysresource/sysresource_view");
		SysResourceVo po = this.sysResourceService.selectVoByPrimaryKey(vo.getId());
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
	public ModelAndView editById(@ModelAttribute SysResourceVo vo)throws Exception{
		ModelAndView mv = new ModelAndView("sys/sysresource/sysresource_edit");
		vo = this.sysResourceService.selectVoByPrimaryKey(vo.getId());
		mv.addObject("vo", vo);
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
	public JsonData editSubmit(@ModelAttribute SysResource po)throws Exception{
		JsonData json = new JsonData();
		try {
			this.sysResourceService.updateByPrimaryKeySelective(po);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
	@RequestMapping("/toTree") 
	public ModelAndView toTree()throws Exception{
		ModelAndView mv = new ModelAndView("jsp/sys/sysresource/sysresource_tree");
		return mv;
	}
	
	@RequestMapping("/loadTree") 
	@ResponseBody
	public List<TreeNode> loadTree(Integer id){
		if(id==null)
		{
			id =Integer.valueOf(0);
		}
		List<TreeNode> treenodes = new ArrayList<TreeNode>();
		
		List<SysResource> list = this.sysResourceService.selectListByParentId(id);
		for(SysResource sysResource :list){
			TreeNode treeNode = new TreeNode();
			Map<String,Object> attributes = new HashMap<String,Object>();
			treeNode.setId(sysResource.getId()+"");
			treeNode.setText(sysResource.getResourceName());
			treeNode.setpId(sysResource.getParentId()+"");
			treeNode.setState("closed");
			
			List<SysResource> listChilden = sysResourceService.selectListByParentId(sysResource.getId());
			if(listChilden.size() == 0){
				treeNode.setIsParent(false);
			}
			attributes.put("resourceLevel", sysResource.getLevel());
			treeNode.setAttributes(attributes);
			
			treenodes.add(treeNode);
		}
		return treenodes;
	}
	
	@RequestMapping("/toSelectTree") 
	public ModelAndView toSelectTree(Integer roleId)throws Exception{
		ModelAndView mv = new ModelAndView("sys/sysresource/sysresource_selecttree");
		mv.addObject("roleId", roleId);
		return mv;
	}
	
	@RequestMapping("/loadSelectTree") 
	@ResponseBody
	public List<TreeNode> loadSelectTree(Integer id){

		List<TreeNode> treenodes = new ArrayList<TreeNode>();
		List<SysResourceVo> list = sysResourceService.findAuthorizationAll(id);
		for(SysResourceVo sysResource:list){
			TreeNode node = new TreeNode();
			node.setId(sysResource.getId()+"");
			node.setText(sysResource.getResourceName());
			node.setpId(sysResource.getParentId()+"");
			List<SysResource> listChilden = sysResourceService.selectListByParentId(sysResource.getId());
			if(listChilden.size() == 0){
				node.setIsParent(false);
			}
			if(null != sysResource.getParentId() && "0".equals(sysResource.getParentId())){
				node.setOpen(true);
			}
			if(null != sysResource.getRoleId()){
				node.setChecked(true);
			}
			treenodes.add(node);
		}
		
		return treenodes;
	}
}