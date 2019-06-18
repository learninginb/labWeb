package com.simulation.controller.admin.knowledge;




import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.simulation.common.base.BaseMybatisController;
import com.simulation.common.page.Pagination;
import com.simulation.common.web.DataGrid;
import com.simulation.common.web.JsonData;
import com.simulation.core.shiro.ShiroUser;
import com.simulation.core.util.PageUtils;
import com.simulation.dao.mybatis.knowledge.KeyWordDao;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.model.knowledge.Comment;
import com.simulation.model.knowledge.KeyWord;
import com.simulation.model.knowledge.Paper;
import com.simulation.service.knowledge.CommentService;
import com.simulation.service.knowledge.PaperService;
import com.simulation.vo.knowledge.CommentVo;
import com.simulation.vo.knowledge.PaperVo;
import com.simulation.vo.sys.SysUserActiveVo;


/**
 * @author ASUS
 *
 */
@Controller
@RequestMapping("/PaperController")
public class PaperController extends BaseMybatisController {

	public static String PAPER_PATH="jsp/paper/upload";
	
	private final static Logger log = Logger.getLogger(PaperController.class);

	@Autowired(required=false) 
	private PaperService paperService; 
	@Autowired(required=false)
	private CommentService commentService;
	
	@Autowired(required=false) 
	private KeyWordDao keyWordDao;
	
	/**
	 * 列表页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toList")
	public ModelAndView toList() throws Exception {
		ModelAndView mv = new ModelAndView("knowledge/paper/paper_list");
		mv.addObject("resource_path", PAPER_PATH);
		return mv;
	}
	
	/**
	 * 请求列表数据
	 * 
	 * @param request
	 * @param vo
	 *            查询对象
	 * @param rows
	 *            每页显示多少行
	 * @param page
	 *            当前页码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid list(PaperVo vo) throws Exception {
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		Pagination pagination = this.paperService.findListByPage(pageSize, pageNo, vo);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}

	
	/**
	 * 文献评论列表
	 * 
	 * 
	 */
	@RequestMapping("/toCommentList")
	@ResponseBody
	public ResponseParam toCommentList(@ModelAttribute CommentVo commentVo,@RequestParam("pageNo")int pageNo,@RequestParam("hot") boolean hot,@RequestParam("recent") boolean recent ){
		int pageSize = PageUtils.getRows();
		ResponseParam responseParam = new ResponseParam();
		responseParam.setCode(200);
		responseParam.setMsg("request success");
		Map<String,Object> data = new HashMap<String,Object>();
		Pagination pagination = commentService.findListByPage(pageSize, pageNo, hot, recent, commentVo);
		data.put("pagination", pagination);
		responseParam.setData(data);
		log.info("------------------"+pageNo);
		return responseParam;
	}
	
	@RequestMapping("/toMyCommentList")
	public ModelAndView toMyCommentList(){
		return new ModelAndView("knowledge/paper/paper_myComment_list");
	}
	
	@RequestMapping("/getMyCommentPage")
	@ResponseBody
	public ResponseParam getMyCommentPage(@RequestParam("paperName") String paperName,@RequestParam("keyWordList") String keyWordList,@RequestParam("pageNo") int pageNo,@RequestParam("hot") boolean hot,@RequestParam("recent") boolean recent){
		List<String> KYWordList = JSON.parseArray(keyWordList, String.class);
		
		int pageSize = PageUtils.getRows();
		CommentVo commentVo = new CommentVo();
		if(paperName!=null&&!"".equals(paperName)){
			commentVo.setPaperName(paperName);
		}
		ResponseParam responseParam = new ResponseParam();
		Map<String,Object> data = new HashMap<String,Object>();
		Pagination pagination = new Pagination();
		List<KeyWord> KYList = null;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		try{
		KYList = keyWordDao.findAll();
		pagination = commentService.findMyListByPage(pageSize, pageNo,KYWordList,hot, recent, commentVo);
		}catch(Exception e){
			e.printStackTrace();
			responseParam.setCode(500);
			responseParam.setMsg("server error!");
			responseParam.setData(null);
			return responseParam;
		}
		data.put("pagination", pagination);
		responseParam.setCode(200);
		responseParam.setMsg("request success");
		data.put("pagination", pagination);
		data.put("keyWordList", KYList);
		responseParam.setData(data);
		return responseParam;
	}
	
	
	
	/**
	 * 打开新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toAdd() throws Exception {
		log.debug("打开新增页面");
		ModelAndView mv = new ModelAndView("knowledge/paper/paper_add");
		mv.addObject("list", paperService.findAll());
		List<KeyWord> keyWordList = this.keyWordDao.findAll();
		//List<SysProject> projectList=sysProjectService.findAll();
		mv.addObject("keyWordList", keyWordList);
		return mv;
	}

	/**
	 * 新增方法
	 * 
	 * @param request
	 * @param po
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	@ResponseBody
	public JsonData add(@ModelAttribute Paper po, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {
		po.setCreate_time(new Date());
		po.setUpdate_time(po.getCreate_time());
		po.setCreate_user_id(ShiroUser.getUserId());
		po.setUpdate_user_id(po.getCreate_user_id());
		JsonData json = new JsonData();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		String filePath = "";
		
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myfile = multipartRequest.getFile("file_path");

			if (null != myfile && !myfile.isEmpty()) {
				filePath = this.uploadpic(myfile,
						request.getSession().getServletContext().getRealPath("/") + PAPER_PATH);
			}
		}
		if (filePath != null && !filePath.equals("")) {
			po.setFile_url(filePath);
		} else {
			json.setSuccess(false);
			json.setMsg("文件上传失败");
			return json;
		}
		try {
			System.out.println(request.getParameter("key_word"));
			String key_word = URLDecoder.decode(request.getParameter("key_word"), "utf-8");
			po.setKey_word(key_word);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			String msg = this.paperService.save(po);
			if (msg == null) {
				json.setSuccess(true);
				json.setMsg("添加成功");
			} else {
				json.setSuccess(false);
				json.setMsg(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	

	/**
	 * 添加摘要
	 * @param comment
	 * @return
	 */
	@RequestMapping("/addComment")
	@ResponseBody
	public ResponseParam addComent(@ModelAttribute Comment comment){
		ResponseParam resParam = new ResponseParam();
		SysUserActiveVo sysUserVo = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		comment.setUser_id(sysUserVo.getId());
		try{
			commentService.addComment(comment);
		}catch(Exception e){
			e.printStackTrace();
			resParam.setCode(500);
			resParam.setMsg("request fail!");
			return resParam;
		}
		resParam.setCode(200);
		resParam.setMsg("request success!");
		resParam.setData(null);
		return resParam;
	}
	
	
	@RequestMapping("/addPraise")
	@ResponseBody
	public ResponseParam addPraise(@RequestParam int id){
		ResponseParam res = new ResponseParam();
		int praise_count = 0;
		SysUserActiveVo sysUserVo = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		try {
			praise_count = commentService.addPraise(id,sysUserVo.getId());
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("点赞失败");
			return res;
		}
		res.setCode(200);
		res.setMsg("request success!");
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("praise_count", praise_count);
		res.setData(data);
		return res;
	}
	
	/**
	 * 根据ID删除对象
	 * 
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public JsonData deleteById(String ids) {
		JsonData json = new JsonData();
		try {
			this.paperService.deleteIds(ids);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	
	@RequestMapping("/deleteCommentById")
	@ResponseBody
	public ResponseParam deleteComment(@RequestParam int id){
		ResponseParam res = new ResponseParam();
		if(id!=0)
		try{
			commentService.deleteComment(id);
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("server error!");
			res.setData(null);
			return res;
		}
		else{
			res.setCode(400);
			res.setMsg("id is null");
			res.setData(null);
			return res;
		}
		res.setCode(200);
		res.setMsg("success!");
		res.setData(null);
		return res;
	}
	
	
	
	
	/**
	 * 根据ID下载对象
	 * 
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downLoadById")
	@ResponseBody
	public void downLoadById(@ModelAttribute Paper vo,HttpServletResponse response,
			HttpServletRequest request) {
		JsonData json = new JsonData();
		vo=paperService.selectByPrimaryKey(vo.getId());
		try {
			
			String fileName = vo.getFile_url();
			String fileSaveRootPath=request.getSession().getServletContext().getRealPath("/") + PAPER_PATH;
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
	/**
	 * 根据ID找到对象
	 * 
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(@ModelAttribute Paper vo) throws Exception {
		ModelAndView mv = new ModelAndView("knowledge/paper/paper_view");
		Paper po = this.paperService.selectByPrimaryKey(vo.getId());
		mv.addObject("vo", po);
		int id = po.getId();
		List<CommentVo> commentVoList = commentService.findHotComment(id);
		mv.addObject("paper_id",id);
		mv.addObject("commentVoList", commentVoList);
		mv.addObject("resource_path", PAPER_PATH);
		
		return mv;
	}

	/**
	 * 跳转到更新页面
	 * 
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editById")
	public ModelAndView editById(@ModelAttribute Paper po) throws Exception {
		ModelAndView mv = new ModelAndView("knowledge/paper/paper_edit");
		po = this.paperService.selectByPrimaryKey(po.getId());
		mv.addObject("resource_path", PAPER_PATH);
		mv.addObject("list", paperService.findAll());
		mv.addObject("vo", po);
		return mv;
	}

	@RequestMapping("/editMyComment")
	@ResponseBody
	public ResponseParam editMyComment(@ModelAttribute Comment comment){
		ResponseParam res = new ResponseParam();
		if(comment.getId()!=null&&comment.getComment()!=null&&comment.getId()!=0&&!"".equals(comment.getComment()))
		try{
			commentService.editMyCommentById(comment);
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("server is error!");
			res.setData(null);
			return null;
		}
		else{
			res.setCode(400);
			res.setMsg("param error");
			res.setData(null);
			return res;
		}
		res.setCode(200);
		res.setMsg("success!");
		res.setData(null);
		return res;
	}
	
	/**
	 * 更新提交
	 * 
	 * @param request
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editSubmit")
	@ResponseBody
	public JsonData editSubmit(@ModelAttribute Paper po, String[] names, String[] units,
			Integer[] data_type_id, String[] str_names, String[] changeable, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) throws Exception {
		po.setUpdate_time(po.getCreate_time());
		po.setUpdate_user_id(po.getCreate_user_id());
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		String filePath = "";
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myfile = multipartRequest.getFile("file_path");
			if (null != myfile && !myfile.isEmpty()) {
				filePath = this.uploadpic(myfile,
						request.getSession().getServletContext().getRealPath("/") + PAPER_PATH);
			}
		}
		if (filePath == null || filePath.equals("")) {
			po.setFile_url(null);
		} else {
			po.setFile_url(filePath);
		}
		JsonData json = new JsonData();
		try {
			 this.paperService.updateByPrimaryKeySelective(po);
				 json.setSuccess(true); 
				 json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false); 
			 json.setMsg("修改异常");
		}
		return json;
	}

	public String uploadpic(MultipartFile myfile, String picdir) {

		String originalFilename = myfile.getOriginalFilename();
		String endName = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
		String filename = UUID.randomUUID() + endName;
		if (endName.equals(".bmp") || endName.equals(".png") || endName.equals(".jpg") || endName.equals(".jpeg") || endName.equals(".txt") || endName.equals(".pdf") || endName.equals(".doc") || endName.equals(".docx") || endName.equals(".caj")) {
			try {
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(picdir, filename));
			} catch (Exception e) {
				log.error("图片导入失败");
				log.error(e.toString());
				e.printStackTrace();
			}
			return filename;
		} else if (endName.equals(".log")) {
			try {
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(picdir, originalFilename));
			} catch (Exception e) {
				log.error("图片导入失败");
				log.error(e.toString());
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * @description 动态获取图片
	 * @param po
	 * @return
	 */
	@RequestMapping("/getImages")
	@ResponseBody
	public JsonData getImages(@ModelAttribute Paper po) {
		JsonData json = new JsonData();
		try {
			List<Paper> list=this.paperService.selectList(po);
			json.setMsg(PAPER_PATH);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
		}
		return json;
	}
	
}
