package com.simulation.service.knowledge.impl;

import com.simulation.common.page.Pagination;
import com.simulation.core.util.PageUtils;
import com.simulation.dao.mybatis.knowledge.NewsCommentDao;
import com.simulation.dao.mybatis.knowledge.NewsDao;
import com.simulation.dao.mybatis.knowledge.NewsImageDao;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.model.knowledge.NewsComment;
import com.simulation.model.knowledge.NewsImage;
import com.simulation.service.knowledge.NewsService;
import com.simulation.service.sys.SysRoleService;
import com.simulation.vo.knowledge.NewsVo;
import com.simulation.vo.sys.SysUserActiveVo;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月16日 上午9:10:17  
*/
@Service
public class NewsServiceImpl implements NewsService {
	private static final Logger log = Logger.getLogger(NewsServiceImpl.class);
	private static final String FILE_PATH="attached/file/newsImage";
	@Autowired
	private NewsDao newsDao ;
	@Autowired
	private NewsImageDao newsImageDao;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private NewsCommentDao newsCommentDao;

	@Transactional
	@Override
	public ResponseParam addNews(NewsVo vo) {
		ResponseParam res = new ResponseParam();
		if(vo.getNewsName()!=null&&!"".equals(vo.getNewsName())&&vo.getContent()!=null){
			if(vo.getLocation()==null){
				vo.setLocation("默认实验室");
			}
			Date now = new Date();
			vo.setCreateTime(now);
			vo.setStatusId(1);
			try{
				newsDao.insertNews(vo);
				res.setCode(200);
				res.setMsg("发布成功！");
				res.setData(null);
			}catch(Exception e){
				e.printStackTrace();
				res.setCode(500);
				res.setMsg("服务器错误!");
				res.setData(null);
				return res;
			}
			
		}else {
			res.setCode(400);
			res.setMsg("新闻主题或内容不能缺少!");
			res.setData(null);
			return res;
		}
		return res;
	}

	@Override
	public Pagination findListByPage(int rows, int page, NewsVo vo) {
		// TODO Auto-generated method stub
				Pagination pagination = new Pagination();
			    pagination.setPageNo(page); //当前页码
			    pagination.setPageSize(rows);  //每页显示多少行
			    List<NewsVo>  list = newsDao.findListByPage(vo,pagination);
			    pagination.setList(list);
			    return pagination;
	}

	@Override
	public ResponseParam getNews(int id) {
		ResponseParam res = new ResponseParam();
		
		if(id!=0){
			try{
				NewsVo newsVo = newsDao.getNewsById(id);
				List<NewsComment> commentList = newsCommentDao.findCommentByNewsId(id,null);
				newsDao.addReadcount(id);
				res.setCode(200);
				res.setMsg("新闻内容获取成功！");
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("newsVo", newsVo);
				data.put("list",commentList);
				res.setData(data);
			}catch(Exception e){
				e.printStackTrace();
				res.setCode(500);
				res.setMsg("新闻内容获取失败!");
				res.setData(null);
				return res;
			}
		}else{
			res.setCode(400);
			res.setMsg("该新闻不存在!");
			res.setData(null);
			return res;
		}
		return res;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResponseParam deleteNews(String ids,String roleType) {
		// TODO Auto-generated method stub
		ResponseParam res = new ResponseParam();
		String[] idList = ids.split(",");
		for(int i=0;i<idList.length;i++){
			String id = idList[i];
			NewsVo newsVo = newsDao.getNewsById(Integer.parseInt(id));
				if(((SysUserActiveVo)SecurityUtils.getSubject().getPrincipal()).getId()==newsVo.getAuthorId()||"1".equals(roleType)){
					log.info("作者为当前用户");
					Integer newsImageId = newsVo.getImageId();
					String imagePath = newsImageDao.findNewsImageById(newsImageId).getImageUrl();
					System.out.println("图片路径       "+imagePath);
					File file = new File(System.getProperty("user.dir")+File.separator+FILE_PATH+File.separator+imagePath);
					if(file.exists()){
						log.info("主图文件存在");
						file.delete();
					}else{
						log.info("文件不存在，无法删除文件");
					}
					try{
						newsImageDao.deleteById(newsImageId);
						newsDao.deleteById(Integer.parseInt(id));
					}catch(Exception e){
						log.info("删除出错!");
						e.printStackTrace();
						res.setCode(500);
						res.setMsg("删除出错");
						res.setData(null);
						return res;
					}
				}else{
					log.info("用户不是超级管理员且用户不是作者,返回删除失败!");
					res.setCode(500);
					res.setMsg("用户不是超级管理员且用户不是作者,删除失败");
					res.setData(null);
					return res;
				}
			
		}
		res.setCode(200);
		res.setMsg("删除成功");
		res.setData(null);
		return res;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public ResponseParam editorNews(String file_path,MultipartFile multipartFile, int id, NewsVo newsVo) {
		// TODO Auto-generated method stub
		ResponseParam res = new ResponseParam();
		if(multipartFile!=null&&!multipartFile.isEmpty()){
			log.info("图片不为空");
			String originalFileName = multipartFile.getOriginalFilename();
			String endName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
			String filePath = UUID.randomUUID()+endName;
			if(endName.equals(".bmp") || endName.equals(".png") || endName.equals(".jpg") || endName.equals(".jpeg")){
				log.info("文件为图片");
				try {
					FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(file_path, filePath));
				} catch (IOException e) {
					log.info("文件上传失败");
					e.printStackTrace();
					res.setCode(500);
					res.setMsg("文件上传失败");
					res.setData(null);
					return res;
				}
			}else{
				log.info("文件不为图片");
				res.setCode(400);
				res.setMsg("文件不为图片");
				res.setData(null);
				return res;
			}
			NewsImage newsImage = newsImageDao.findNewsImageById(newsDao.getNewsById(id).getImageId());
			String imageUrl = newsImage.getImageUrl();
			File file = new File(System.getProperty("user.dir")+File.separator+FILE_PATH+File.separator+imageUrl);
			if(file.exists()){
				log.info("主图文件存在");
				file.delete();
			}else{
				log.info("文件不存在，无法删除文件");
			}
			NewsImage newImage = new NewsImage();
			newImage.setId(newsImage.getId());
			newImage.setImageUrl(filePath);
			newImage.setImageName(originalFileName.substring(originalFileName.lastIndexOf("/")+1,originalFileName.lastIndexOf(".")));
			newsImageDao.editorNewsImage(newImage);
			newsVo.setId(id);
			newsVo.setCreateTime(new Date());
			newsDao.editorNews(newsVo);
		}else{
			log.info("图片为空");
			newsVo.setId(id);
			newsVo.setCreateTime(new Date());
			newsDao.editorNews(newsVo);
		}
		if(newsDao.getNewsById(id).getStatusId()==3){
			newsDao.changeStatusId(id,1);
		}
		res.setCode(200);
		res.setMsg("修改成功");
		res.setData(null);
		return res;
	}

	@Override
	public ResponseParam getAllNews() {
		// TODO Auto-generated method stub
		ResponseParam res = new ResponseParam();
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		try{
			List<NewsVo> newsList =  newsDao.findAllList(pagination);
			pagination.setList(newsList);
			pagination.setTotalCount(newsDao.findAllListCount());
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("pagination",pagination);
			res.setCode(200);
			res.setMsg("获取成功");
			res.setData(data);
		}catch(Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("获取新闻失败");
			res.setData(null);
			return res;
		}
		
		return res;
	}

	@Override
	public Pagination findPageByCreateTime(Date createTime, Pagination page) {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String time = formatter.format(createTime);
		List<NewsVo> list = newsDao.findPageByCreateTime(time ,page);
		Pagination page1 = new Pagination();
		page1.setList(list);
		return page1;
	}

	@Override
	public ResponseParam ChangeStatus(int id, int status) {
		ResponseParam res = new ResponseParam();
		try{
			newsDao.changeStatusId(id,status);
		}catch (Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMsg("更改新闻状态失败");
			return res;
		}
		res.setCode(200);
		res.setMsg("更改新闻状态成功");
		return res;
	}

}
