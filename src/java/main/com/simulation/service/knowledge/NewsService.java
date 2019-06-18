package com.simulation.service.knowledge;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.simulation.common.page.Pagination;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.vo.knowledge.NewsVo;


/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月16日 上午9:08:55  
*/
public interface NewsService {
	/**
	 * 添加新闻
	 * @Desc
	 * @param vo
	 * @return
	 * @author spxin
	 * @Date 2019年5月16日 ${time}
	 */
	public ResponseParam addNews(NewsVo vo);
	/**
	 * 我的新闻分页
	 * @Desc
	 * @param rows
	 * @param page
	 * @param vo
	 * @return
	 * @author spxin
	 * @Date 2019年5月16日 ${time}
	 */
	Pagination findListByPage(int rows, int page, NewsVo vo);
	/**
	 * 根据id获取新闻的具体内容
	 * @Desc
	 * @param id
	 * @return
	 * @author spxin
	 * @Date 2019年5月16日 ${time}
	 */
	public ResponseParam getNews(int id);
	/**
	 * 根据ids list删除新闻
	 * @Desc
	 * @param ids
	 * @return
	 * @author spxin
	 * @Date 2019年5月18日 ${time}
	 */
	public ResponseParam deleteNews(String ids,String roleType);
	
	/**
	 * 修改新闻
	 * @Desc
	 * @param multipartFile 主图文件
	 * @param id 新闻id
	 * @param newsVo 修改的属性
	 * @return
	 * @author spxin
	 * @Date 2019年5月19日 ${time}
	 */
	public ResponseParam editorNews(String file_path,MultipartFile multipartFile,int id,NewsVo newsVo);
	/**
	 * 获取所有的新闻
	 * @Desc
	 * @return
	 * @author spxin
	 * @Date 2019年5月19日 
	 */
	public ResponseParam getAllNews();
	/**
	 * 查找最热的新闻
	 * @Desc
	 * @param createTime
	 * @param page
	 * @return
	 * @author spxin
	 * @Date 2019年5月24日 ${time}
	 */
	public Pagination findPageByCreateTime(Date createTime ,Pagination page);

	/**
	 * 修改新闻状态
	 * @param id
	 * @param status
	 * @return
	 */
	public ResponseParam ChangeStatus(int id, int status);

}
