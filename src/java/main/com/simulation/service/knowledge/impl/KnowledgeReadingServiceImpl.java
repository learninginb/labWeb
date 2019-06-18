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
import com.simulation.dao.mybatis.knowledge.KnowledgeReadingDao;
import com.simulation.model.knowledge.KnowledgeReading;
import com.simulation.service.knowledge.KnowledgeReadingService;
import com.simulation.vo.knowledge.KnowledgeReadingVo;

@Transactional
@Service("knowledgeReadingService")
public class KnowledgeReadingServiceImpl implements KnowledgeReadingService{

	private final static Logger log= Logger.getLogger(KnowledgeReadingServiceImpl.class);

	@Autowired
	private KnowledgeReadingDao knowledgeReadingDao;

	
	public KnowledgeReading selectByPrimaryKey(Integer id){
	    return knowledgeReadingDao.selectByPrimaryKey(id);
	}

    public void deleteByPrimaryKey(Integer id){
    	this.knowledgeReadingDao.deleteByPrimaryKey(id); 
    }

    public void insert(KnowledgeReading model) {
    	model.setUserId(ShiroUser.getUserId());
    	model.setReadTime(new Date());
    	this.knowledgeReadingDao.insert(model); 
    }
    
    public void insertSelective(KnowledgeReading model){
    	this.knowledgeReadingDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(KnowledgeReading model){
    	this.knowledgeReadingDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(KnowledgeReading model) {
		this.knowledgeReadingDao.updateByPrimaryKey(model);
    }
    
    public List<KnowledgeReading> selectList(KnowledgeReading knowledgeReading){
    	return knowledgeReadingDao.selectList(knowledgeReading);
    }
    
    public List<KnowledgeReading> findAll() {
		return knowledgeReadingDao.findAll();
    }

    public void deleteAll() {
		this.knowledgeReadingDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.knowledgeReadingDao.deleteByIds(idsList);
		}else {
			this.knowledgeReadingDao.deleteByPrimaryKey(Integer.valueOf(ids));
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,KnowledgeReadingVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<KnowledgeReadingVo>  list = this.knowledgeReadingDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    } 	
}

