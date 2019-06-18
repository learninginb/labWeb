package com.simulation.controller.admin.sys;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.simulation.common.base.BaseMybatisController;
import com.simulation.common.page.Pagination;
import com.simulation.common.util.PinyinUtil;
import com.simulation.common.web.DataGrid;
import com.simulation.common.web.JsonData;
import com.simulation.core.util.PageUtils;
import com.simulation.model.sys.SysUser;
import com.simulation.model.sys.SysUserRole;
import com.simulation.service.sys.SysUserService;
import com.simulation.vo.sys.SysUserVo;

@Controller
@RequestMapping("/sysUserController") 
public class SysUserController extends BaseMybatisController {
	
	private final static Logger log= Logger.getLogger(SysUserController.class);

	@Autowired(required=false) 
	private SysUserService sysUserService; 
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toList") 
	public ModelAndView toList()throws Exception{
		ModelAndView mv = new ModelAndView("sys/sysuser/sysuser_list");
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
	public DataGrid list (SysUserVo vo) throws Exception{
		int pageNo=PageUtils.getPage();
		int pageSize=PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		Pagination pagination = sysUserService.findListByPage(pageSize, pageNo, vo);
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
		ModelAndView mv = new ModelAndView("sys/sysuser/sysuser_add");
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
	public JsonData  add(@ModelAttribute SysUser po) {
		JsonData json = new JsonData();
		try {
			this.sysUserService.insert(po);
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
			this.sysUserService.deleteIds(ids);
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
	public ModelAndView findById(@ModelAttribute SysUserVo vo)throws Exception{
		ModelAndView mv = new ModelAndView("sys/sysuser/sysuser_view");
		SysUser po = this.sysUserService.selectByPrimaryKey(vo.getId());
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
	public ModelAndView editById(@ModelAttribute SysUser po)throws Exception{
		ModelAndView mv = new ModelAndView("sys/sysuser/sysuser_edit");
		po = this.sysUserService.selectByPrimaryKey(po.getId());
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
	public JsonData editSubmit(@ModelAttribute SysUser po)throws Exception{
		JsonData json = new JsonData();
		try {
			this.sysUserService.updateByPrimaryKeySelective(po);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/toSetUserRoles")
	@ResponseBody
	public JsonData toSetUserRoles(@RequestBody SysUserRole[] sysUserRoles){
		JsonData jsonData = new JsonData();
		try {
			this.sysUserService.setUserRole(sysUserRoles);
			jsonData.setSuccess(true);
		} catch (Exception e) {
			jsonData.setSuccess(false);
		}
	
		return jsonData;
	}
	
	@RequestMapping("/findJsonById")
	@ResponseBody
	public JsonData findJsonById(@ModelAttribute SysUser po)throws Exception{
		JsonData json = new JsonData();
		try {
			po = this.sysUserService.selectByPrimaryKey(po.getId());
			json.setSuccess(true);
			json.setObj(po);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	* Description:获得中文字的拼音    
	* @Title: getUserAccount  
	* @author Jalf
	* @since 2016年6月6日 上午11:52:06
	* @param name
	* @param type
	* @return
	* Copyright  https://shop106571874.taobao.com All right reserved.
	 */
	@RequestMapping("/getUserAccount")
	@ResponseBody
	public JsonData getUserAccount(String name){
		JsonData jsonData=new JsonData();
		try {
			String pinyinForName = PinyinUtil.getPinyinForName(name);
			jsonData.setSuccess(true);
			jsonData.setObj(pinyinForName);
		} catch (Exception e) {
			jsonData.setSuccess(false);
		}
		return jsonData;
	}
	
	
	@RequestMapping("/getIsExist")
	@ResponseBody
	public JsonData  getIsExist(String name,String type){
		JsonData jsonData=new JsonData();
		boolean findIsExist = sysUserService.findIsExist(name, type);
		jsonData.setSuccess(findIsExist);
		return jsonData;
		
	}
	
}