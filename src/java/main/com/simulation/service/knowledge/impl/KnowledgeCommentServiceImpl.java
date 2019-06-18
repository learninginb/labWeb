package com.simulation.service.knowledge.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.common.page.Pagination;
import com.simulation.core.shiro.ShiroUser;
import com.simulation.dao.mybatis.knowledge.KnowledgeCommentDao;
import com.simulation.dao.mybatis.knowledge.KnowledgeReadingDao;
import com.simulation.model.knowledge.KnowledgeComment;
import com.simulation.model.knowledge.KnowledgeReading;
import com.simulation.service.knowledge.KnowledgeCommentService;
import com.simulation.vo.knowledge.KnowledgeCommentVo;

@Transactional
@Service("knowledgeCommentService")
public class KnowledgeCommentServiceImpl implements KnowledgeCommentService{

	private final static Logger log= Logger.getLogger(KnowledgeCommentServiceImpl.class);

	@Autowired
	private KnowledgeCommentDao knowledgeCommentDao;
	
	@Autowired
	private KnowledgeReadingDao knowledgeReadingDao;

	
	public KnowledgeComment selectByPrimaryKey(Integer id){
	    return knowledgeCommentDao.selectByPrimaryKey(id);
	}

    public void deleteByPrimaryKey(Integer id){
    	this.knowledgeCommentDao.deleteByPrimaryKey(id); 
    }

    public void insert(KnowledgeComment model) {
    	model.setCommentTime(new Date());
    	model.setCommentUserId(ShiroUser.getUserId());
    	model.setIsComment(KnowledgeComment.IS_VALID);//设为有效
    	this.knowledgeCommentDao.insert(model); 
    }
    
    public void insertSelective(KnowledgeComment model){
    	this.knowledgeCommentDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(KnowledgeComment model){
    	this.knowledgeCommentDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(KnowledgeComment model) {
		this.knowledgeCommentDao.updateByPrimaryKey(model);
    }
    
    public List<KnowledgeComment> selectList(KnowledgeComment knowledgeComment){
    	return knowledgeCommentDao.selectList(knowledgeComment);
    }
    
    public List<KnowledgeComment> findAll() {
		return knowledgeCommentDao.findAll();
    }

    public void deleteAll() {
		this.knowledgeCommentDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.knowledgeCommentDao.deleteByIds(idsList);
		}else {
			this.knowledgeCommentDao.deleteByPrimaryKey(Integer.valueOf(ids));
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,KnowledgeCommentVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<KnowledgeCommentVo>  list = this.knowledgeCommentDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    }

	@Override
	public KnowledgeComment findByKnowledgeId(Integer knowledgeId) {
		KnowledgeComment po = this.knowledgeCommentDao.findByKnowledgeId(knowledgeId);
		return po;
	}

	@Override
	public void comment(KnowledgeComment po) {
		Integer knowledgeId = po.getKnowledgeId();
		if(knowledgeId!=null){
			//插入评论
			this.insert(po);
			//设置阅读后的评论操作
			KnowledgeReading reading = this.knowledgeReadingDao.selectLastestReading(knowledgeId,ShiroUser.getUserId());
			reading.setAboutOperation((reading.getAboutOperation()!=null?reading.getAboutOperation():"")+" "+KnowledgeReading.COMMENT);
			this.knowledgeReadingDao.updateByPrimaryKeySelective(reading);
		}
	} 	
	
}

