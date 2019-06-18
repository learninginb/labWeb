package com.simulation.controller.admin.knowledge;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.simulation.model.knowledge.NewsComment;
import com.simulation.service.knowledge.NewsCommentService;
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
import com.simulation.common.web.DataGrid;
import com.simulation.core.util.PageUtils;
import com.simulation.dao.mybatis.knowledge.NewsDao;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.model.knowledge.NewsImage;
import com.simulation.model.sys.SysResource;
import com.simulation.service.knowledge.NewsImageService;
import com.simulation.service.knowledge.NewsService;
import com.simulation.service.sys.SysResourceService;
import com.simulation.service.sys.SysRoleService;
import com.simulation.service.sys.SysUserService;
import com.simulation.vo.knowledge.NewsVo;
import com.simulation.vo.sys.SysUserActiveVo;


/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月15日 下午10:07:05  
*/
@Controller
@RequestMapping("/NewsController")
public class NewsController {
	Logger log = Logger.getLogger(NewsController.class);
	private static final String FILE_PATH="attached/file/newsImage";
	@Autowired
	private NewsService newsService;
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private SysRoleService roleService;
	@Autowired
	private SysResourceService resourceService;
	@Autowired
	private NewsImageService newsImageService;
	@Autowired
	private NewsCommentService newsCommentService;
	
	@RequestMapping(value="/toAddNews")
	public String toNews(){
		return "knowledge/news/news_add";
	}
	@RequestMapping(value="/toMyNews")
	public String toMyList(){
		return "knowledge/news/news_list";
	}
	@RequestMapping(value = "/toReviewList")
	public String toReviewList(){return "knowledge/news/news_review";}
	
	@RequestMapping("/myList")
	@ResponseBody
	public DataGrid list(NewsVo vo) throws Exception {
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		vo.setAuthorId(user.getId());
		Pagination pagination = this.newsService.findListByPage(pageSize, pageNo, vo);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}

	@RequestMapping(value = "/reviewList")
	@ResponseBody
	public DataGrid reviewList(NewsVo vo){
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		vo.setStatusId(2);
		vo.setLocation(user.getLocation());
		Pagination pagination = newsService.findListByPage(pageSize,pageNo,vo);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}

	/**
	 * 发布新闻
	 * @Desc
	 * @param vo
	 * @return
	 * @author spxin
	 * @Date 2019年5月16日 ${time}
	 */
	@RequestMapping(value="/news",method=RequestMethod.POST)
	@ResponseBody
	public ResponseParam addNews(@ModelAttribute NewsVo vo,HttpServletRequest request){
		ResponseParam res = new ResponseParam();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		if(user!=null){
			//用户已登录
			//判断用户有没有添加新闻的权限
			List<SysResource> resourceList = resourceService.findAllByUserId(user.getId());
			for(SysResource resource:resourceList){
				if("News:add".equals(resource.getPermissionStr())){
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					String filePath = "";
					if(multipartResolver!=null&&multipartResolver.isMultipart(request)){
						MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
						MultipartFile multipartFile = multipartRequest.getFile("imageFile");
						if (null != multipartFile && !multipartFile.isEmpty()) {
							String originalFileName = multipartFile.getOriginalFilename();
							String endName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
							filePath = UUID.randomUUID()+endName;
							if(endName.equals(".bmp") || endName.equals(".png") || endName.equals(".jpg") || endName.equals(".jpeg")){
								try{
									FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(request.getSession().getServletContext().getRealPath(FILE_PATH), filePath));
									newsImageService.addImage(filePath,originalFileName.substring(originalFileName.lastIndexOf("/")+1,originalFileName.lastIndexOf(".")));
								}catch(Exception e){//图片上传失败
									e.printStackTrace();
								}
							}else{
								//图片不符合
								res.setCode(400);
								res.setMsg("该文件不是图片，上传失败!");
								res.setData(null);
								return res;
							}
							
						}else{
							res.setCode(400);
							res.setMsg("主图片为空");
							res.setData(null);
							return res;
						}
					}else{
						res.setCode(400);
						res.setMsg("该请求不是multipartRequest");
						res.setData(null);
						return res;
					}
					NewsImage newsImage = newsImageService.getImageByImagePath(filePath);
					vo.setImageId(newsImage.getId());
					vo.setLocation(user.getLocation());
					vo.setAuthorId(user.getId());
					return newsService.addNews(vo);
				}
			}
			res.setCode(403);
			res.setMsg("没有权限");
			res.setData(null);
			return res;
			
		}else{
			//用户未登陆
			res.setCode(403);
			res.setMsg("用户未登录");
			res.setData(null);
			return res;
		}
	}
	
	@RequestMapping(value="/news/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseParam getNews(@PathVariable("id")int id){	
		
		return newsService.getNews(id);
	}
	
	/**
	 * 根据id集合删除新闻
	 * @Desc
	 * @param ids
	 * @return
	 * @author spxin
	 * @Date 2019年5月19日 ${time}
	 */
	@RequestMapping(value="/news",method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseParam deleteNews(@RequestParam("ids")String ids){
		System.out.println("-----------"+ids);
		ResponseParam res = new ResponseParam();
		if(ids!=null&&!"".equals(ids)){
			log.info("参数校验成功");
			SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
			if(user!=null){
				log.info("用户已登录");
				if(roleService.findRoleTypesByUserId(user.getId()).contains("1")){
					log.info("用户是超级管理！");
					return newsService.deleteNews(ids, "1");
				}else{
					log.info("用户不是超级管理");
					List<SysResource> resourceList = resourceService.findAllByUserId(user.getId());
					for(SysResource resource:resourceList){
						if("News:delete".equals(resource.getPermissionStr())){
							log.info("用户有权限");
							return newsService.deleteNews(ids, "2");
						}
					}
						
						log.info("用户没有权限");
						res.setCode(403);
						res.setMsg("用户没有权限");
						res.setData(null);
						return res;
				}
			}else{
				log.info("用户未登录");
				res.setCode(403);
				res.setMsg("用户未登录");
				res.setData(null);
				return res;
			}
		}else{
			log.info("参数校验失败");
			res.setCode(400);
			res.setMsg("参数校验失败");
			res.setData(null);
			return res;
		}
	}
	
	/**
	 * 根据id修改新闻
	 * @Desc
	 * @param
	 * @param id
	 * @param request
	 * @return
	 * @author spxin
	 * @Date 2019年5月19日 ${time}
	 */
	@RequestMapping(value="/news/{id}",method=RequestMethod.POST)
	@ResponseBody
	public ResponseParam editorNews(@ModelAttribute NewsVo newsVo,@PathVariable("id")int id,HttpServletRequest request){
		ResponseParam res = new ResponseParam();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartFile multipartFile = null;
		if(multipartResolver!=null&&multipartResolver.isMultipart(request)){
			log.info("请求是multipart请求");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			multipartFile = multipartRequest.getFile("imageFile");
		}else{
			log.info("请求不是multipart请求");
			multipartFile=null;
		}
		
		if(id!=0){
			log.info("参数校验成功");
			SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
			if(user!=null){
				log.info("用户已登录");
				List<SysResource> resourceList = resourceService.findAllByUserId(user.getId());
				for(SysResource resource:resourceList){
					if("News:edit".equals(resource.getPermissionStr())){
						log.info("用户有权限");
						
						if(roleService.findRoleTypesByUserId(user.getId()).contains("1")){
							log.info("用户为超级管理员,不需要判断是否为当前实验室的新闻");
							return newsService.editorNews(request.getSession().getServletContext().getRealPath(FILE_PATH),multipartFile,id,newsVo);
						}else{
							if(user.getLocation().equals(newsDao.getNewsById(id).getLocation())){
								log.info("当前新闻所属于当前用户的实验室");
								return newsService.editorNews(request.getSession().getServletContext().getRealPath(FILE_PATH),multipartFile,id,newsVo);
							}else{
								log.info("当前新闻不属于当前用户的实验室");
								res.setCode(403);
								res.setMsg("当前新闻不属于当前用户的实验室");
								res.setData(null);
								return res;
							}
						}
					}
				}
				res.setCode(403);
				res.setMsg("用户没有权限");
				res.setData(null);
				return res;
			}else{
				log.info("用户未登录");
				res.setCode(403);
				res.setMsg("用户未登录");
				res.setData(null);
				return res;
			}
		}else{
			log.info("参数校验失败");
			res.setCode(403);
			res.setMsg("参数校验失败");
			res.setData(null);
			return res;
		}
	}
	
	/**
	 * 获取所有新闻列表
	 * @Desc
	 * @return
	 * @author spxin
	 * @Date 2019年5月19日 ${time}
	 */
	@RequestMapping(value="/allNews",method=RequestMethod.GET)
	@ResponseBody
	public ResponseParam getAllNews(){
		
		return newsService.getAllNews();
	}
	
	@RequestMapping(value="/news/hot",method=RequestMethod.GET)
	@ResponseBody
	public ResponseParam getHotNews(){
		ResponseParam res = new ResponseParam();
		Pagination pagination = new Pagination();
		pagination.setPageNo(1);
		pagination.setPageSize(2);
		Pagination todayPage = newsService.findPageByCreateTime(new Date(),pagination);
		Pagination yesterdayPage = newsService.findPageByCreateTime(new Date(new Date().getTime()-24*60*60*1000),pagination);
		Pagination beforeyesterdayPage = newsService.findPageByCreateTime(new Date(new Date().getTime()-24*60*60*1000*2),pagination);
		res.setCode(200);
		res.setMsg("获取成功");
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("todayPage", todayPage);
		data.put("yesterdayPage",yesterdayPage);
		data.put("beforeyesterdayPage", beforeyesterdayPage);
		res.setData(data);
		return res;
	}

	@RequestMapping(value = "/{newsId}/comments",method = RequestMethod.POST)
	@ResponseBody
	public ResponseParam addComment(@PathVariable("newsId") int newsId,@RequestParam Map<String,Object> inputParam){
		NewsComment comment = new NewsComment();
		comment.setAuthor((String) inputParam.get("author"));
		comment.setNewsId(newsId);
		comment.setEmail((String) inputParam.get("email"));
		comment.setContent((String) inputParam.get("content"));
		comment.setCreateTime(new Date());
		return newsCommentService.addComment(comment);
	}

	@RequestMapping(value = "/{newsId}/comments",method = RequestMethod.GET)
	@ResponseBody
	public ResponseParam findCommentByNewsId(@PathVariable("newsId")int newsId){
		Pagination page = new Pagination();
		page.setPageNo(PageUtils.getPage());
		page.setPageSize(PageUtils.getRows());
		return newsCommentService.findCommentByNewsId(newsId,page);
	}

	@RequestMapping(value = "/{id}/review/{result}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseParam reviewNews(@PathVariable("id")int id,@PathVariable("result")String result){
		ResponseParam res = new ResponseParam();
		if("true".equals(result)){
			return newsService.ChangeStatus(id,3);
		}else if ("false".equals(result)){
			return newsService.ChangeStatus(id,4);
		}else{
			res.setCode(400);
			res.setMsg("参数不符合规范");
			return res;
		}
	}

	@RequestMapping(value = "/submit",method = RequestMethod.POST)
	@ResponseBody
	public ResponseParam submitNews(@RequestParam("ids")String ids){
		ResponseParam res = new ResponseParam();
		if(ids!=null&&!"".equals(ids)){
			String[] idList = ids.split(",");

			for (int i=0;i<idList.length;i++){
				newsService.ChangeStatus(Integer.parseInt(idList[i]),2);
				res.setCode(200);
				res.setMsg("提交成功");
				return res;
			}
		}
		res.setCode(400);
		res.setMsg("参数有误");
		return res;
	}
}
