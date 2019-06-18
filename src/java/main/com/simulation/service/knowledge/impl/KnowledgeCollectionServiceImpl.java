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
import com.simulation.dao.mybatis.knowledge.KnowledgeCollectionDao;
import com.simulation.dao.mybatis.knowledge.KnowledgeReadingDao;
import com.simulation.dao.mybatis.knowledge.KnowledgeSummaryDao;
import com.simulation.model.knowledge.KnowledgeCollection;
import com.simulation.model.knowledge.KnowledgeReading;
import com.simulation.model.knowledge.KnowledgeSummary;
import com.simulation.service.knowledge.KnowledgeCollectionService;
import com.simulation.vo.knowledge.KnowledgeCollectionVo;

@Transactional
@Service("knowledgeCollectionService")
public class KnowledgeCollectionServiceImpl implements KnowledgeCollectionService{

	private final static Logger log= Logger.getLogger(KnowledgeCollectionServiceImpl.class);

	@Autowired
	private KnowledgeCollectionDao knowledgeCollectionDao;
	
	@Autowired
	private KnowledgeSummaryDao knowledgeSummaryDao;
	
	@Autowired
	private KnowledgeReadingDao knowledgeReadingDao;

	
	public KnowledgeCollection selectByPrimaryKey(Integer id){
	    return knowledgeCollectionDao.selectByPrimaryKey(id);
	}

    public void deleteByPrimaryKey(Integer id){
    	this.knowledgeCollectionDao.deleteByPrimaryKey(id); 
    }

    public void insert(KnowledgeCollection model) {
    	model.setUserId(ShiroUser.getUserId());
    	model.setIsValid(KnowledgeCollection.IS_VALID);//设置收藏为有效
    	model.setCollectionTime(new Date());
    	this.knowledgeCollectionDao.insert(model); 
    }
    
    public void insertSelective(KnowledgeCollection model){
    	this.knowledgeCollectionDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(KnowledgeCollection model){
    	this.knowledgeCollectionDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(KnowledgeCollection model) {
		this.knowledgeCollectionDao.updateByPrimaryKey(model);
    }
    
    public List<KnowledgeCollection> selectList(KnowledgeCollection knowledgeCollection){
    	return knowledgeCollectionDao.selectList(knowledgeCollection);
    }
    
    public List<KnowledgeCollection> findAll() {
		return knowledgeCollectionDao.findAll();
    }

    public void deleteAll() {
		this.knowledgeCollectionDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.knowledgeCollectionDao.deleteByIds(idsList);
		}else {
			this.knowledgeCollectionDao.deleteByPrimaryKey(Integer.valueOf(ids));
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,KnowledgeCollectionVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<KnowledgeCollectionVo>  list = this.knowledgeCollectionDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    }

    @Override
	public void collection(KnowledgeCollection po) {
		Integer knowledgeId = po.getKnowledgeId();
		//根据knowlegeId查找收藏纪录，有则更新状态为有效没有则新增（可能之前有收藏但被取消了）
		KnowledgeCollection collection = this.knowledgeCollectionDao.findByKnowledgeId(knowledgeId);
		if(collection!=null){
			collection.setIsValid(KnowledgeCollection.IS_VALID);
			this.updateByPrimaryKeySelective(collection);
		}else{
			this.insert(po);
			//收藏次数+1
			KnowledgeSummary summary = this.knowledgeSummaryDao.findByKnowledgeId(knowledgeId);
			if(summary!=null){
				summary.setCollectionTimes(summary.getCollectionTimes()+1);
				this.knowledgeSummaryDao.updateByPrimaryKeySelective(summary);
			}else{
				summary = new KnowledgeSummary();
				summary.setKnowledgeId(knowledgeId);
				summary.setCollectionTimes(1);
				summary.setReadTimes(0);
				summary.setFollowTimes(0);
				summary.setCommentTimes(0);
				this.knowledgeSummaryDao.insert(summary);
			}
		}
		//阅读后收藏操作设置为收藏(最新一条纪录)
		KnowledgeReading reading = this.knowledgeReadingDao.selectLastestReading(knowledgeId,ShiroUser.getUserId());
		reading.setAboutOperation((reading.getAboutOperation()!=null?reading.getAboutOperation():"")+" "+KnowledgeReading.COLLECTION);
		this.knowledgeReadingDao.updateByPrimaryKeySelective(reading);
	} 	
    
	@Override
	public void cancelCollection(KnowledgeCollection po) {
		Integer knowledgeId = po.getKnowledgeId();
		this.knowledgeCollectionDao.cancelByKnowledgeId(knowledgeId,ShiroUser.getUserId(),KnowledgeCollection.IS_INVALID);
		//阅读后收藏操作设置为收藏(最新一条纪录)
		KnowledgeReading reading = this.knowledgeReadingDao.selectLastestReading(knowledgeId,ShiroUser.getUserId());
		reading.setAboutOperation((reading.getAboutOperation()!=null?reading.getAboutOperation():"")+" "+KnowledgeReading.COLLECTION_CANCEL);
		this.knowledgeReadingDao.updateByPrimaryKeySelective(reading);
	}
}

