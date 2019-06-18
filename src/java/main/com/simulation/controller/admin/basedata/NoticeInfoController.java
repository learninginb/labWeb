package com.simulation.controller.admin.basedata;

import java.util.Date;

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
import com.simulation.model.basedata.NoticeInfo;
import com.simulation.service.basedata.NoticeInfoService;
import com.simulation.vo.basedata.NoticeInfoVo;

@Controller
@RequestMapping("/noticeController")
public class NoticeInfoController extends BaseMybatisController{
	
	
	private final static Logger log= Logger.getLogger(NoticeInfoController.class);
	
	
	@Autowired(required=false) 
	private NoticeInfoService noticeInfoService; 
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toList") 
	public ModelAndView toList()throws Exception{
		ModelAndView mv = new ModelAndView("base_data/notice_info/notice_info_list");
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
	public DataGrid list (NoticeInfoVo vo) throws Exception{
		int pageNo=PageUtils.getPage();
		int pageSize=PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		Pagination pagination = this.noticeInfoService.findListByPage(pageSize, pageNo, vo);
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
		ModelAndView mv = new ModelAndView("base_data/notice_info/notice_info_add");
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
	public JsonData  add(@ModelAttribute NoticeInfo po) {
		po.setCreate_time(new Date());

		po.setCreate_user_id(ShiroUser.getUserId());
		po.setUpdate_user_id(po.getCreate_user_id());
		JsonData json = new JsonData();
		try {
			this.noticeInfoService.insert(po);
			json.setSuccess(true);
			json.setMsg("添加成功");
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
	public ModelAndView findById(@ModelAttribute NoticeInfoVo vo)throws Exception{
		ModelAndView mv = new ModelAndView("base_data/notice_info/notice_info_view");
		NoticeInfo po = this.noticeInfoService.selectByPrimaryKey(vo.getId());
		mv.addObject("vo", po);
		return mv;
	}

	
	
	

}
