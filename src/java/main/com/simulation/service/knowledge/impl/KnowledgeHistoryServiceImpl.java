package com.simulation.service.knowledge.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.common.page.Pagination;
import com.simulation.dao.mybatis.knowledge.KnowledgeDao;
import com.simulation.dao.mybatis.knowledge.KnowledgeHistoryDao;
import com.simulation.model.knowledge.Knowledge;
import com.simulation.model.knowledge.KnowledgeHistory;
import com.simulation.service.knowledge.KnowledgeHistoryService;
import com.simulation.vo.knowledge.KnowledgeHistoryVo;

@Transactional
@Service("knowledgeHistoryService")
public class KnowledgeHistoryServiceImpl implements KnowledgeHistoryService{

	private final static Logger log= Logger.getLogger(KnowledgeHistoryServiceImpl.class);

	@Autowired
	private KnowledgeHistoryDao knowledgeHistoryDao;
	
	@Autowired
	private KnowledgeDao knowledgeDao;

	
	public KnowledgeHistory selectByPrimaryKey(Integer id){
	    return knowledgeHistoryDao.selectByPrimaryKey(id);
	}

    public void deleteByPrimaryKey(Integer id){
    	this.knowledgeHistoryDao.deleteByPrimaryKey(id); 
    }

    public void insert(KnowledgeHistory model) {
    	this.knowledgeHistoryDao.insert(model); 
    }
    
    public void insertSelective(KnowledgeHistory model){
    	this.knowledgeHistoryDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(KnowledgeHistory model){
    	this.knowledgeHistoryDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(KnowledgeHistory model) {
		this.knowledgeHistoryDao.updateByPrimaryKey(model);
    }
    
    public List<KnowledgeHistory> selectList(KnowledgeHistory knowledgeHistory){
    	return knowledgeHistoryDao.selectList(knowledgeHistory);
    }
    
    public List<KnowledgeHistory> findAll() {
		return knowledgeHistoryDao.findAll();
    }

    public void deleteAll() {
		this.knowledgeHistoryDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.knowledgeHistoryDao.deleteByIds(idsList);
		}else {
			this.knowledgeHistoryDao.deleteByPrimaryKey(Integer.valueOf(ids));
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,KnowledgeHistoryVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<KnowledgeHistoryVo>  list = this.knowledgeHistoryDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    }

	@Override
	public void deleteByKnowledgeIds(String ids) {
		// TODO Auto-generated method stub
		String [] idArr=ids.split(",");
		List<Integer> idsList=new ArrayList<Integer>();
    	if (idArr.length>1) {
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.knowledgeHistoryDao.deleteByKnowledgeIds(idsList);
    	}else{
    		//单个删除
    		idsList.add(Integer.valueOf(ids));
    		this.knowledgeHistoryDao.deleteByKnowledgeIds(idsList);
    	}
	}

	@Override
	public void addVersion(Knowledge po) {
		KnowledgeHistory obj = new KnowledgeHistory();
		obj.setKnowledgeId(po.getId());
		obj.setKnowledgeVersion(po.getKnowledgeVersion());
		obj.setKnowledgeType(po.getKnowledgeType());
		obj.setKnowledgeTitle(po.getKnowledgeTitle());
		obj.setKnowledgeComment(po.getKnowledgeComment());
		obj.setKnowledgeIconUrl(po.getKnowledgeIconUrl());
		obj.setKnowledgeAttachUrl(po.getKnowledgeAttachUrl());
		Knowledge knowledge =  this.knowledgeDao.selectByPrimaryKey(po.getId());
		obj.setKnowledgeState(knowledge.getKnowledgeState());
		obj.setCreateUser(knowledge.getCreateUser());
		this.insert(obj);
	} 	
}

