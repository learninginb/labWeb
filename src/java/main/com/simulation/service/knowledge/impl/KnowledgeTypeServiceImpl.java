package com.simulation.service.knowledge.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.common.page.Pagination;
import com.simulation.dao.mybatis.knowledge.KnowledgeDao;
import com.simulation.dao.mybatis.knowledge.KnowledgeTypeDao;
import com.simulation.model.knowledge.Knowledge;
import com.simulation.model.knowledge.KnowledgeType;
import com.simulation.service.knowledge.KnowledgeTypeService;
import com.simulation.vo.knowledge.KnowledgeTypeVo;

@Transactional
@Service("knowledgeTypeService")
public class KnowledgeTypeServiceImpl implements KnowledgeTypeService{

	private final static Logger log= Logger.getLogger(KnowledgeTypeServiceImpl.class);

	@Autowired
	private KnowledgeTypeDao knowledgeTypeDao;
	
	@Autowired
	private KnowledgeDao knowledgeDao;

	
	public KnowledgeType selectByPrimaryKey(Integer id){
	    return knowledgeTypeDao.selectByPrimaryKey(id);
	    
	}

    public void deleteByPrimaryKey(Integer id){
    	this.knowledgeTypeDao.deleteByPrimaryKey(id); 
    }

    public void insert(KnowledgeType model) {
    	model.setCreateTime(new Date());
    	model.setModifyTime(new Date());
    	this.knowledgeTypeDao.insert(model);
    }
    
    public void insertSelective(KnowledgeType model){
    	this.knowledgeTypeDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(KnowledgeType model){
    	this.knowledgeTypeDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(KnowledgeType model) {
		this.knowledgeTypeDao.updateByPrimaryKey(model);
    }
    
    public List<KnowledgeType> selectList(KnowledgeType knowledgeType){
    	return knowledgeTypeDao.selectList(knowledgeType);
    }
    
    public List<KnowledgeType> findAll() {
		return knowledgeTypeDao.findAll();
    }

    public void deleteAll() {
		this.knowledgeTypeDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.knowledgeTypeDao.deleteByIds(idsList);
		}else {
			this.knowledgeTypeDao.deleteByPrimaryKey(Integer.valueOf(ids));
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,KnowledgeTypeVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<KnowledgeTypeVo>  list = this.knowledgeTypeDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    }
    
    @Override
	public List<KnowledgeType> selectListByParentId(Integer parentId) {
		return this.knowledgeTypeDao.selectListByParentId(parentId,KnowledgeType.KNOWLEDGE_TYPE_STATE_1);
	}

	@Override
	public String saveKnowledgeType(KnowledgeType po) {
		String msg = validate(po);
		if(msg!=null){
			return msg;
		}
		if(po.getId()!=null){
			this.updateByPrimaryKeySelective(po);
		}else{
			this.insert(po);
		}
		return null;
	}
	
	public String validate(KnowledgeType po){
		if(po.getParentId()==null){
			return "上级目录不能为空";
		}
		if(po.getKnowledgeType()==null){
			return "知识分类不能为空";
		}
		if(po.getKnowledgeTypeDesc()==null){
			return "知识描述不能为空";
		}
		KnowledgeType knowledgeType = this.knowledgeTypeDao.selectObj(po.getParentId(),po.getKnowledgeType());
		if(knowledgeType!=null){
			if(po.getId()==null){//添加
				return "当前分类下已存在名称项目的分类";
			}
			//修改时可能查找同一个，修改的时候没有更改上级分类和自己的分类名称
			if(knowledgeType.getId().intValue()!=po.getId().intValue()){
				return "当前分类下已存在名称项目的分类";
			}
		}
		return null;
	}

	@Override
	public String deleteObjs(String ids) {
		//该分类下有子分类或者有知识点不能删除
		String msg = deleteValidate(ids);
		if(msg!=null){
			return msg;
		}
		this.deleteIds(ids);
		return null;
	}

	/**
	 * 删除时校验
	 * @param ids
	 * @return
	 */
	private String deleteValidate(String ids) {
		List<KnowledgeType> knowledgeTypeList = null;
		List<Knowledge> knowledgeList = null;
		String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> parentIdsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				parentIdsList.add(Integer.valueOf(idArr[i]));
			}
			knowledgeTypeList = this.knowledgeTypeDao.selectListByParentIds(parentIdsList,null);
			//子分类
	    	if(knowledgeTypeList!=null && knowledgeTypeList.size()>0){
				return "选中的分类下有子分类，不能删除！";
			}
	    	//知识点判断
	    	knowledgeList = this.knowledgeDao.selectListByKnowledgeTypes(parentIdsList);
	    	if(knowledgeList!=null && knowledgeList.size()>0){
				return "选中的分类下有知识条目，不能删除！";
			}
		}else {
			knowledgeTypeList = this.knowledgeTypeDao.selectListByParentId(Integer.valueOf(ids),null);
			//子分类
	    	if(knowledgeTypeList!=null && knowledgeTypeList.size()>0){
				return "选中的分类下有子分类，不能删除！";
			}
	    	//知识点判断
	    	Knowledge knowledge = new Knowledge();
	    	knowledge.setKnowledgeType(ids);
	    	knowledgeList = this.knowledgeDao.selectList(knowledge);
	    	if(knowledgeList!=null && knowledgeList.size()>0){
				return "选中的分类下有知识条目，不能删除！";
			}
		}
    	
		return null;
	}
	
}

