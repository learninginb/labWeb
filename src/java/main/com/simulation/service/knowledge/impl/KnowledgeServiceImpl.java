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
import com.simulation.dao.mybatis.knowledge.KnowledgeDao;
import com.simulation.dao.mybatis.knowledge.KnowledgeHistoryDao;
import com.simulation.model.knowledge.Knowledge;
import com.simulation.model.knowledge.KnowledgeHistory;
import com.simulation.service.knowledge.KnowledgeService;
import com.simulation.vo.knowledge.KnowledgeVo;

@Transactional
@Service("knowledgeService")
public class KnowledgeServiceImpl implements KnowledgeService{

	private final static Logger log= Logger.getLogger(KnowledgeServiceImpl.class);

	@Autowired
	private KnowledgeDao knowledgeDao;
	
	@Autowired
	private KnowledgeHistoryDao knowledgeHistoryDao;
	
	public Knowledge selectByPrimaryKey(Integer id){
	    return knowledgeDao.selectByPrimaryKey(id);
	}

    public void deleteByPrimaryKey(Integer id){
    	this.knowledgeDao.deleteByPrimaryKey(id); 
    }

    public void insert(Knowledge model) {
    	model.setCreateUser(ShiroUser.getUserId());
    	model.setKnowledgeState(0);
    	model.setCreateTime(new Date());
    	model.setModifyTime(new Date());
    	this.knowledgeDao.insert(model); 
    }
    
    public void insertSelective(Knowledge model){
    	this.knowledgeDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(Knowledge model){
    	if(model.getKnowledgeState().equals(Knowledge.knowledge_state_05)){//被驳回的知识修改后进入待审核
    		model.setKnowledgeState(Knowledge.knowledge_state_00);
    	}
    	this.knowledgeDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(Knowledge model) {
		this.knowledgeDao.updateByPrimaryKey(model);
    }
    
    public List<Knowledge> selectList(Knowledge knowledge){
    	return knowledgeDao.selectList(knowledge);
    }
    
    public List<Knowledge> findAll() {
		return knowledgeDao.findAll();
    }

    public void deleteAll() {
		this.knowledgeDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.knowledgeDao.deleteByIds(idsList);
		}else {
			this.knowledgeDao.deleteByPrimaryKey(Integer.valueOf(ids));
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,KnowledgeVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<KnowledgeVo>  list = this.knowledgeDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    }

	@Override
	public KnowledgeVo selectVoByPrimaryKey(Integer id) {
		return knowledgeDao.selectVoByPrimaryKey(id);
	}

	@Override
	public KnowledgeVo readByPrimaryKey(Integer id) {
		return knowledgeDao.readByPrimaryKey(id);
	}
	
	@Override
	public void updateState(String ids, Integer state) {
		String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.knowledgeDao.updateState(idsList,state);
		}else {
			this.knowledgeDao.updateStateByPrimaryKey(Integer.valueOf(ids),state);
		}
	}
	
	@Override
	public String validate(Knowledge po) {
		// TODO Auto-generated method stub
		Double knowledgeVersion = po.getKnowledgeVersion();
		if(knowledgeVersion == null || "".equals(knowledgeVersion)){
			return "版本号不能为空！";
		}
		KnowledgeHistory history = this.knowledgeHistoryDao.selectLastest(po.getId());
		if(history!=null){
			Double version = history.getKnowledgeVersion();
			if(version>knowledgeVersion){
				return "知识版本号必须大于历史版本中的最高版本"+version;
			}
		}
		return null;
	}

	@Override
	public String upadateKnowledge(Knowledge po) {
		String msg = this.validate(po);
		if(msg!=null){
			return msg;
		}
		//修改知识
		this.updateByPrimaryKeySelective(po);
		//添加知识历史版本
		return null;
	}
}

