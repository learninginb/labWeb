package com.simulation.controller.admin.knowledge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.simulation.common.page.Pagination;
import com.simulation.common.util.StatusUtil;
import com.simulation.common.web.DataGrid;
import com.simulation.common.web.JsonData;
import com.simulation.core.util.PageUtils;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.model.knowledge.Paper;
import com.simulation.model.knowledge.Task;
import com.simulation.model.sys.SysUser;
import com.simulation.service.basedata.MessageService;
import com.simulation.service.knowledge.TaskReviewService;
import com.simulation.service.knowledge.TaskService;
import com.simulation.service.sys.SysUserService;
import com.simulation.vo.knowledge.TaskReviewVo;
import com.simulation.vo.knowledge.TaskVo;
import com.simulation.vo.sys.SysUserActiveVo;
import com.simulation.websocket.config.MessageUtil;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年4月27日 上午9:17:51  
*/
@Controller
@RequestMapping("/TaskController")
public class TaskController {
	private static final Logger log = Logger.getLogger(TaskController.class);
	private static final String FILE_SAVE_ROOTPATH = "jsp/task/upload";
	@Autowired
	private SysUserService userService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskReviewService taskReviewService;
	@Autowired
	private MessageService messageService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/toAddTask")
	public String getAddTask(){
		return "knowledge/task/task_add";
	}
	
	@RequestMapping("/toFinishTask")
	public String toFinishTask(){
		return "knowledge/task/task_finish_list";
	}
	
	@RequestMapping("/toUnfinishTask")
	public String toUnfinishTask(){
		return "knowledge/task/task_unfinish_list";
	}
	
	@RequestMapping("/toReviewTask")
	public String toReviewTask(){
		return "knowledge/task/task_review_list";
	}
	
	@RequestMapping("/toReceiveTask")
	public String toReceiveTask(){
		return "knowledge/task/task_receive_list";
	}
	
	@RequestMapping("/toManageTask")
	public String toManageTask(){
		return "knowledge/task/task_manage_list";
	}
	
	@RequestMapping("/toRecordTask")
	public String toRecordTask(){
		return "knowledge/task/task_record_list";
	}
	
	/**
	 * 获取我负责的任务列表
	 * @Desc
	 * @param task
	 * @return
	 * @author spxin
	 * @Date 2019年5月1日 ${time}
	 */
	@RequestMapping("/managerList")
	@ResponseBody
	public DataGrid managerList(Task task){
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		task.setDistributorId(user.getId());
		task.setPicId(user.getId());
		Pagination pagination = taskService.findManagerListByPage(pageSize, pageNo, task);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}
	
	/**
	 * 查找已完成的任务
	 * @Desc
	 * @param task
	 * @return
	 * @author spxin
	 * @Date 2019年5月2日 ${time}
	 */
	@RequestMapping("/finishList")
	@ResponseBody
	public DataGrid finishList(Task task){
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		task.setExecutorId(user.getId());
		Pagination pagination = taskService.findFinishListByPage(pageSize, pageNo, task);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}
	
	/**
	 * 查找未完成的任务
	 * @Desc
	 * @param task
	 * @return
	 * @author spxin
	 * @Date 2019年5月2日 ${time}
	 */
	@RequestMapping("/unfinishList")
	@ResponseBody
	public DataGrid unfinishList(Task task){
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		task.setExecutorId(user.getId());
		Pagination pagination = taskService.findUnfinishListByPage(pageSize, pageNo, task);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}
	
	/**
	 * 查找我要审核的任务列表
	 * @Desc
	 * @param task
	 * @return
	 * @author spxin
	 * @Date 2019年5月2日 ${time}
	 */
	@RequestMapping("/reviewList")
	@ResponseBody
	public DataGrid reviewList(Task task){
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		task.setDistributorId(user.getId());
		Pagination pagination = taskService.findReviewListByPage(pageSize, pageNo, task);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}
	
	/**
	 * 查询待我接收的任务
	 * @Desc
	 * @param task
	 * @return
	 * @author spxin
	 * @Date 2019年5月2日 ${time}
	 */
	@RequestMapping("/receiveList")
	@ResponseBody
	public DataGrid receiveList(Task task){
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		task.setExecutorId(user.getId());
		Pagination pagination = taskService.findReceiveListByPage(pageSize, pageNo, task);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}
	
	@RequestMapping("/recordList")
	@ResponseBody
	public DataGrid recordList(Task task){
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		task.setExecutorId(user.getId());
		task.setDistributorId(user.getId());
		Pagination pagination = taskService.findRecordListByPage(pageSize, pageNo, task);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}
	
	/**
	 * 新建任务
	 * @return ResponseParam
	 * @throws ParseException 
	 */
	@RequestMapping("/addTask")
	@ResponseBody
	public ResponseParam addTask(@ModelAttribute Task task,@RequestParam String start,@RequestParam String end, HttpServletRequest request) throws ParseException{
		ResponseParam res = new ResponseParam();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		String filePath = "";
		
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myfile = multipartRequest.getFile("file_path");

			if (null != myfile && !myfile.isEmpty()) {
				filePath = this.uploadpic(myfile,
						request.getSession().getServletContext().getRealPath(FILE_SAVE_ROOTPATH));
			}
		}
		if (filePath == null||filePath.equals("")) {
			log.info("不上传文件！");
			filePath = "";
		}
		Date beginTime = sdf.parse(start);
		Date finishTime = null;
		if(!"".equals(end)&&"计划性任务".equals(task.getType())){
			finishTime = sdf.parse(end);
		}
		else{
			Calendar c = Calendar.getInstance(); 
			 c.setTime(beginTime);  
			if("日周期任务".equals(task.getType())){
	             c.add(Calendar.DAY_OF_MONTH, 1);
	             finishTime = c.getTime();
			}
			else if ("周周期任务".equals(task.getType())){
	             c.add(Calendar.DAY_OF_MONTH, 7);
	             finishTime = c.getTime();
			}else if("月周期任务".equals(task.getType())){
	             c.add(Calendar.DAY_OF_MONTH, 30);
	             finishTime = c.getTime();
			}
		}
		task.setBeginTime(beginTime);
		task.setFinishTime(finishTime);
		task.setDistributorId(user.getId());
		task.setFileUrl(filePath);
		try{
			taskService.addTask(task);
		
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("server error!");
			res.setData(null);
			return null;
		}
		res.setCode(200);
		res.setMsg("success!");
		res.setData(null);
		return res;
	}
	
	/**
	 * 
	 * @Desc 新建子任务
	 * @param task
	 * @param start
	 * @param end
	 * @param request
	 * @return
	 * @throws ParseException
	 * @author spxin
	 * @Date 2019年5月2日 ${time}
	 */
	@RequestMapping("/addSonTask")
	@ResponseBody
	public ResponseParam addSonTask(@ModelAttribute Task task,@RequestParam String start,@RequestParam String end,HttpServletRequest request) throws ParseException{
		ResponseParam res = new ResponseParam();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		String filePath = "";
		
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myfile = multipartRequest.getFile("file_path");

			if (null != myfile && !myfile.isEmpty()) {
				filePath = this.uploadpic(myfile,
						request.getSession().getServletContext().getRealPath(FILE_SAVE_ROOTPATH));
			}
		}
		if (filePath == null||filePath.equals("")) {
			log.info("不上传文件！");
			filePath = "";
		}
		Date beginTime = sdf.parse(start);
		Date finishTime = sdf.parse(end);
		task.setBeginTime(beginTime);
		task.setFinishTime(finishTime);
		task.setDistributorId(user.getId());
		task.setFileUrl(filePath);
		try{
			taskService.addSonTask(task);
			res.setCode(200);
			res.setMsg("success!");
			res.setData(null);
		}catch(Exception e)
		{
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("server error!");
			res.setData(null);
			return res;
		}
		return res;
	}
	
	@RequestMapping(value="/task/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseParam deleteTask(@PathVariable("id")int id){
		TaskVo vo = new TaskVo();
		vo.setId(id);
		return taskService.deleteTask(vo);
	}
	
	/**
	 * 请求新建任务页面初始的数据
	 * @return
	 */
	@RequestMapping("/requestAddData")
	@ResponseBody
	public ResponseParam requestAddData(){
		ResponseParam res = new ResponseParam();
		List<SysUser> list = null;
		try{
			list = userService.findAllUserIdAndName();
			res.setCode(200);
			res.setMsg("request success");
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("list", list);
			SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
			data.put("distributor", user.getUserName());
			res.setData(data);
			return res;
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("server error!");
			res.setData(null);
			return res;
		}
	}
	
	/**
	 * 请求新建子任务页面的数据
	 * @Desc
	 * @param parentId
	 * @return
	 * @author spxin
	 * @Date 2019年5月2日 
	 */
	@RequestMapping("/requestAddSonData")
	@ResponseBody
	public ResponseParam requestAddSonData(@RequestParam("parentId")int parentId){
		ResponseParam res = new ResponseParam();
		List<SysUser> list = null;
		if(parentId!=0)
		try{
			TaskVo vo = taskService.findTaskById(parentId);
			list = userService.findAllUserIdAndName();
			res.setCode(200);
			res.setMsg("request success");
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("list", list);
			data.put("taskVo", vo);
			SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
			data.put("distributor", user.getUserName());
			res.setData(data);
			return res;
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("server error!");
			res.setData(null);
			return res;
		}
		else{
			res.setCode(400);
			res.setMsg("parentId is null!");
			res.setData(null);
			return res;
		}
	}
	
	/**
	 *
	 * @Desc 获取子任务
	 * @param parentId
	 * @return
	 * @author spxin
	 * @Date 2019年5月4日 ${time}
	 */
	@RequestMapping("/getSonTask")
	@ResponseBody
	public ResponseParam getSonTask(@RequestParam int parentId){
		ResponseParam res = new ResponseParam();
		if(parentId==0){
			res.setCode(400);
			res.setMsg("parentId must be not null!");
			res.setData(null);
			return res;
		}
		else{
			List<TaskVo> sonlist = taskService.findSonTask(parentId);
			res.setCode(200);
			res.setMsg("success!");
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("sonlist", sonlist);
			res.setData(data);
		}
		
		
		return res;
	}
	
	/**
	 * 获取任务
	 * @Desc
	 * @param vo
	 * @return
	 * @author spxin
	 * @Date 2019年5月6日 ${time}
	 */
	@RequestMapping(value="/task",method=RequestMethod.GET)
	@ResponseBody
	public ResponseParam getTask(@ModelAttribute TaskVo vo){
		ResponseParam res = new ResponseParam();
		if(vo.getId()==0||vo.getId()==null){
			res.setCode(400);
			res.setMsg("参数有误!");
			res.setData(null);
			return res;
		}
		vo = taskService.findTaskById(vo.getId());
		res.setCode(200);
		res.setMsg("success!");
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("task", vo);
		res.setData(data);
		return res;
	}
	
	@RequestMapping(value="/task/{id}",method=RequestMethod.POST)
	@ResponseBody
	public ResponseParam editTask(@PathVariable("id")int id,@ModelAttribute TaskVo vo,@RequestParam String start,@RequestParam String end, HttpServletRequest request) throws ParseException{
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		String filePath = "";
		
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myfile = multipartRequest.getFile("file_path");

			if (null != myfile && !myfile.isEmpty()) {
				filePath = this.uploadpic(myfile,
						request.getSession().getServletContext().getRealPath(FILE_SAVE_ROOTPATH));
			}
		}
		vo.setId(id);
		vo.setBeginTime(sdf.parse(start));
		vo.setFinishTime(sdf.parse(end));
		vo.setFileUrl(filePath);
		
		return taskService.editTask(vo);
	}
	
	/**
	 * 添加审核
	 * @Desc
	 * @param vo
	 * @return
	 * @author spxin
	 * @Date 2019年5月7日 ${time}
	 */
	@RequestMapping(value="/reviews",method=RequestMethod.POST)
	@ResponseBody
	public ResponseParam addReview(@ModelAttribute TaskReviewVo vo,HttpServletRequest request){
		ResponseParam res = new ResponseParam();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		String filePath = "";
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myfile = multipartRequest.getFile("filePath");

			if (null != myfile && !myfile.isEmpty()) {
				filePath = this.uploadpic(myfile,
						request.getSession().getServletContext().getRealPath(FILE_SAVE_ROOTPATH));
			}
		}
		if (filePath == null||filePath.equals("")) {
			log.info("不上传文件！");
			filePath = "";
		}
		vo.setFileUrl(filePath);
		vo.setCreateTime(new Date());
		if(taskReviewService.addReview(vo)){
			res.setCode(200);
			res.setMsg("success");
		}else{
			res.setCode(500);
			res.setMsg("审核申请失败!");
		}
		
		return res;
	}
	
	/**
	 * 获取审核详情
	 * @Desc
	 * @param vo
	 * @return
	 * @author spxin
	 * @Date 2019年5月8日 ${time}
	 */
	@RequestMapping(value="/reviews",method=RequestMethod.GET)
	@ResponseBody
	public ResponseParam getTaskReview(@ModelAttribute TaskReviewVo vo){
		return taskReviewService.getReview(vo);
	}
	
	/**
	 * 审核任务
	 * @Desc
	 * @param taskId
	 * @param isApprove
	 * @return
	 * @author spxin
	 * @Date 2019年5月8日 ${time}
	 */
	@RequestMapping(value="/reviews/{taskId}",method=RequestMethod.POST)
	@ResponseBody
	public ResponseParam reviewTask(@PathVariable("taskId")int taskId,@RequestParam("isApprove")boolean isApprove){
		TaskVo vo = new TaskVo();
		vo.setId(taskId);
		ResponseParam res ;
		if(isApprove){
			res = taskService.changeStatus(vo,StatusUtil.STATUS_FINISH);
			if(res.getCode()==200){
				TaskVo taskVo = taskService.findTaskById(taskId);
				String msg="任务（"+taskVo.getTheme()+"）审核通过";
				messageService.sendMessageToUser(taskVo.getExecutorId(), msg, MessageUtil.MESSAGE_TYPE_REVIEWTASK_RESULT,MessageUtil.REVIEWTASK_RESULT_YES_PATH);
			}
			
		}else{
			res = taskService.changeStatus(vo,StatusUtil.STATUS_UNPASS);
			if(res.getCode()==200){
				TaskVo taskVo = taskService.findTaskById(taskId);
				String msg="任务（"+taskVo.getTheme()+"）审核未通过";
				messageService.sendMessageToUser(taskVo.getDistributorId(), msg, MessageUtil.MESSAGE_TYPE_RECEIVETASK,MessageUtil.REVIEWTASK_RESULT_NO_PATH);
			}
		}
		return res;
	}
	
	/**
	 * 查收任务
	 * @Desc
	 * @param id
	 * @param isApprove
	 * @return
	 * @author spxin
	 * @Date 2019年5月8日 ${time}
	 */
	@RequestMapping(value="/receives/{id}",method=RequestMethod.POST)
	@ResponseBody
	public ResponseParam receiveTask(@PathVariable("id")int id,@RequestParam("isApprove")boolean isApprove){
		TaskVo vo = new TaskVo();
		vo.setId(id);
		ResponseParam res ;
		if(isApprove){
			res = taskService.changeStatus(vo,StatusUtil.STATUS_UNFINISH);
			if(res.getCode()==200){
				TaskVo taskVo = taskService.findTaskById(id);
				String msg="用户"+taskVo.getExecutor()+"接收了任务";
				messageService.sendMessageToUser(taskVo.getDistributorId(), msg, MessageUtil.MESSAGE_TYPE_RECEIVETASK,null);
			}
		}else{
			res = taskService.changeStatus(vo,StatusUtil.STATUS_REFUSE);
			if(res.getCode()==200){
				TaskVo taskVo = taskService.findTaskById(id);
				String msg="用户"+taskVo.getExecutor()+"拒绝了任务";
				messageService.sendMessageToUser(taskVo.getDistributorId(), msg, MessageUtil.MESSAGE_TYPE_RECEIVETASK,null);
			}
		}
		
		return res;
	}
	
	/**
	 * 上传附件
	 * @Desc
	 * @param myfile
	 * @param picdir
	 * @return
	 * @author spxin
	 * @Date 2019年5月2日 ${time}
	 */
	public String uploadpic(MultipartFile myfile, String picdir) {
		System.out.println(picdir+"------------------");
		String originalFilename = myfile.getOriginalFilename();
		String endName = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
		String filename = UUID.randomUUID() + endName;
		
			try {
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(picdir, filename));
			} catch (Exception e) {
				log.error("文件上传失败");
				log.error(e.toString());
				e.printStackTrace();
			
			}
			return filename;
		 
		
		
	}
	
	/**
	 * 根据fileUrl下载对象
	 * 
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downLoadById")
	@ResponseBody
	public void downLoadById(@ModelAttribute Task vo,HttpServletResponse response,
			HttpServletRequest request) {
		
		try {
			
			String fileName = vo.getFileUrl();
			String fileSaveRootPath=request.getSession().getServletContext().getRealPath(FILE_SAVE_ROOTPATH);
			response.setHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"), "ISO_8859_1")); 
			response.setCharacterEncoding("utf-8");
			FileInputStream in = new FileInputStream(fileSaveRootPath + File.separator + fileName);
			OutputStream out = response.getOutputStream();
			//创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			//循环将输入流中的内容读取到缓冲区当中
			while((len=in.read(buffer))>0){
			//输出缓冲区的内容到浏览器，实现文件下载
			out.write(buffer, 0, len);
			}
			//关闭文件输入流
			in.close();
			//关闭输出流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
}
