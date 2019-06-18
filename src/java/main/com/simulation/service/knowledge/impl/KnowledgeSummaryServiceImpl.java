package com.simulation.service.knowledge.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.common.page.Pagination;
import com.simulation.dao.mybatis.knowledge.KnowledgeSummaryDao;
import com.simulation.model.knowledge.KnowledgeSummary;
import com.simulation.service.knowledge.KnowledgeSummaryService;
import com.simulation.vo.knowledge.KnowledgeSummaryVo;

@Transactional
@Service("knowledgeSummaryService")
public class KnowledgeSummaryServiceImpl implements KnowledgeSummaryService{

	private final static Logger log= Logger.getLogger(KnowledgeSummaryServiceImpl.class);

	@Autowired
	private KnowledgeSummaryDao knowledgeSummaryDao;

	
	public KnowledgeSummary selectByPrimaryKey(Integer id){
	    return knowledgeSummaryDao.selectByPrimaryKey(id);
	}

    public void deleteByPrimaryKey(Integer id){
    	this.knowledgeSummaryDao.deleteByPrimaryKey(id); 
    }

    public void insert(KnowledgeSummary model) {
    	this.knowledgeSummaryDao.insert(model); 
    }
    
    public void insertSelective(KnowledgeSummary model){
    	this.knowledgeSummaryDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(KnowledgeSummary model){
    	this.knowledgeSummaryDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(KnowledgeSummary model) {
		this.knowledgeSummaryDao.updateByPrimaryKey(model);
    }
    
    public List<KnowledgeSummary> selectList(KnowledgeSummary knowledgeSummary){
    	return knowledgeSummaryDao.selectList(knowledgeSummary);
    }
    
    public List<KnowledgeSummary> findAll() {
		return knowledgeSummaryDao.findAll();
    }

    public void deleteAll() {
		this.knowledgeSummaryDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.knowledgeSummaryDao.deleteByIds(idsList);
		}else {
			this.knowledgeSummaryDao.deleteByPrimaryKey(Integer.valueOf(ids));
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,KnowledgeSummaryVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<KnowledgeSummaryVo>  list = this.knowledgeSummaryDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    }

	@Override
	public void plusReadingTimes(Integer knowledgeId) {
		//根据knowledgeid查找，没有找到新增，并把阅读次数设置为1，找到则修改阅读次数+1
		KnowledgeSummary summary = this.knowledgeSummaryDao.findByKnowledgeId(knowledgeId);
		if(summary!=null){
			summary.setReadTimes(summary.getReadTimes()+1);
			this.knowledgeSummaryDao.updateByPrimaryKeySelective(summary);
		}else{
			summary = new KnowledgeSummary();
			summary.setKnowledgeId(knowledgeId);
			summary.setReadTimes(1);
			summary.setCollectionTimes(0);
			summary.setCommentTimes(0);
			summary.setFollowTimes(0);
			this.knowledgeSummaryDao.insert(summary);
		}
	} 	
}

