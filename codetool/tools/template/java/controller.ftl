package ${table.controllerPackage};

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.simulation.common.base.BaseMybatisController;
import com.simulation.common.page.Pagination;
import com.simulation.common.web.DataGrid;
import com.simulation.common.web.JsonData;
import com.simulation.yhact.core.util.PageUtils;
import ${table.domainQualifiClassName};
import ${table.serviceQualifiClassName};
import ${table.voQualifiClassName};

@Controller
@RequestMapping("/${table.controllerName?uncap_first}") 
public class ${table.controllerName} extends BaseMybatisController {
	
	private final static Logger log= Logger.getLogger(${table.controllerName}.class);

	@Autowired(required=false) 
	private ${table.serviceName} ${table.serviceName?uncap_first}; 
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toList") 
	public ModelAndView toList()throws Exception{
		ModelAndView mv = new ModelAndView("${table.module}/${table.domainName?lower_case}/${table.domainName?lower_case}_list");
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
	public DataGrid list (${table.voName} vo) throws Exception{
		int pageNo=PageUtils.getPage();
		int pageSize=PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		Pagination pagination = ${table.serviceName?uncap_first}.findListByPage(pageSize, pageNo, vo);
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
		ModelAndView mv = new ModelAndView("${table.module}/${table.domainName?lower_case}/${table.domainName?lower_case}_add");
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
	public JsonData  add(@ModelAttribute ${table.domainName} po) {
		JsonData json = new JsonData();
		try {
			this.${table.serviceName?uncap_first}.insert(po);
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
			this.${table.serviceName?uncap_first}.deleteIds(ids);
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
	public ModelAndView findById(@ModelAttribute ${table.voName} vo)throws Exception{
		ModelAndView mv = new ModelAndView("${table.module}/${table.domainName?lower_case}/${table.domainName?lower_case}_view");
		<#list table.columnInfos as u>
		<#if u.isPK>
		${table.domainName} po = this.${table.serviceName?uncap_first}.selectByPrimaryKey(vo.get${u.javaFirstUpCase}${u.javaOhterLowCase}());
		mv.addObject("vo", po);
		</#if>
		</#list>
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
	public ModelAndView editById(@ModelAttribute ${table.domainName} po)throws Exception{
		ModelAndView mv = new ModelAndView("${table.module}/${table.domainName?lower_case}/${table.domainName?lower_case}_edit");
		<#list table.columnInfos as u>
		<#if u.isPK>
		po = this.${table.serviceName?uncap_first}.selectByPrimaryKey(po.get${u.javaFirstUpCase}${u.javaOhterLowCase}());
		mv.addObject("vo", po);
		</#if>
		</#list>
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
	public JsonData editSubmit(@ModelAttribute ${table.domainName} po)throws Exception{
		JsonData json = new JsonData();
		try {
			this.${table.serviceName?uncap_first}.updateByPrimaryKeySelective(po);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}