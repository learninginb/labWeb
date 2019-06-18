package com.simulation.controller.admin.basedata;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.simulation.common.base.BaseMybatisController;
import com.simulation.common.web.JsonData;
import com.simulation.core.shiro.ShiroUser;
import com.simulation.model.sys.SysUser;
import com.simulation.service.sys.SysUserRoleService;
import com.simulation.service.sys.SysUserService;
import com.simulation.vo.sys.SysUserRoleVo;

@Controller
@RequestMapping("/personalInfoController")
public class PersonalInfoController extends BaseMybatisController{
	
	
	private final static Logger log= Logger.getLogger(PersonalInfoController.class);
	
	
	@Autowired(required=false) 
	private SysUserService sysUserService; 
	
	@Autowired(required=false) 
	private SysUserRoleService  sysUserRoleService;
	
	
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toList") 
	public ModelAndView toList()throws Exception{
		ModelAndView mv = new ModelAndView("base_data/personal_info/personal_info_list");
		SysUser po=this.sysUserService.selectByPrimaryKey(ShiroUser.getUserId());
		List<SysUserRoleVo> list=this.sysUserRoleService.selectByUserId(po.getId());
		String roleNames="";
		if(list!=null&&list.size()>1){
			for(int i=0;i<list.size()-1;i++){
				roleNames=roleNames+list.get(i).getRoleName()+";";
			}
			roleNames=roleNames+list.get(list.size()-1);
		}
		if(list!=null&&list.size()==1){
			roleNames=list.get(0).getRoleName();
		}
		mv.addObject("vo",po);
		mv.addObject("roleNames", roleNames);
		return mv;
	}
	
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toEdit") 
	public ModelAndView toEdit()throws Exception{
		ModelAndView mv = new ModelAndView("base_data/personal_info/personal_info_edit");
		SysUser po=this.sysUserService.selectByPrimaryKey(ShiroUser.getUserId());
		List<SysUserRoleVo> list=this.sysUserRoleService.selectByUserId(po.getId());
		String roleNames="";
		if(list!=null&&list.size()>1){
			for(int i=0;i<list.size()-1;i++){
				roleNames=roleNames+list.get(i).getRoleName()+";";
			}
			roleNames=roleNames+list.get(list.size()-1);
		}
		if(list!=null&&list.size()==1){
			roleNames=list.get(0).getRoleName();
		}
		mv.addObject("vo",po);
		mv.addObject("roleNames", roleNames);
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
	
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toEditPassword") 
	public ModelAndView toEditPassword()throws Exception{
		ModelAndView mv = new ModelAndView("base_data/personal_info/personal_info_edit_password");
		SysUser po=this.sysUserService.selectByPrimaryKey(ShiroUser.getUserId());
		List<SysUserRoleVo> list=this.sysUserRoleService.selectByUserId(po.getId());
		String roleNames="";
		if(list!=null&&list.size()>1){
			for(int i=0;i<list.size()-1;i++){
				roleNames=roleNames+list.get(i).getRoleName()+";";
			}
			roleNames=roleNames+list.get(list.size()-1);
		}
		if(list!=null&&list.size()==1){
			roleNames=list.get(0).getRoleName();
		}
		mv.addObject("vo",po);
		mv.addObject("roleNames", roleNames);
		return mv;
	}
	
	/**
	 * 更新提交
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editPasswordSubmit")
	@ResponseBody
	public JsonData editPasswordSubmit(@ModelAttribute SysUser po)throws Exception{
		JsonData json = new JsonData();
		try {
			String msg=this.sysUserService.updatePassword(po);
			
			if(msg==null){
				json.setSuccess(true);
				json.setMsg("修改成功");	
			}else{
				json.setSuccess(false);
				json.setMsg(msg);	
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	@RequestMapping("/toTest") 
	public ModelAndView toTest()throws Exception{
		ModelAndView mv = new ModelAndView("base_data/personal_info/personal_info_test");
		SysUser po=this.sysUserService.selectByPrimaryKey(ShiroUser.getUserId());
		List<SysUserRoleVo> list=this.sysUserRoleService.selectByUserId(po.getId());
		String roleNames="";
		if(list!=null&&list.size()>1){
			for(int i=0;i<list.size()-1;i++){
				roleNames=roleNames+list.get(i).getRoleName()+";";
			}
			roleNames=roleNames+list.get(list.size()-1);
		}
		if(list!=null&&list.size()==1){
			roleNames=list.get(0).getRoleName();
		}
		mv.addObject("vo",po);
		mv.addObject("roleNames", roleNames);
		return mv;
	}
	
	
}
