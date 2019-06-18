package com.simulation.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.common.page.Pagination;
import com.simulation.dao.mybatis.sys.SysRoleResourceDao;
import com.simulation.model.sys.SysRoleResource;
import com.simulation.service.sys.SysRoleResourceService;
import com.simulation.vo.sys.SysRoleResourceVo;

@Transactional
@Service("sysRoleResourceService")
public class SysRoleResourceServiceImpl implements SysRoleResourceService{

	private final static Logger log= Logger.getLogger(SysRoleResourceServiceImpl.class);

	@Autowired
	private SysRoleResourceDao sysRoleResourceDao;

	
	public SysRoleResource selectByPrimaryKey(Integer id){
	    return sysRoleResourceDao.selectByPrimaryKey(id);
	}

    public void deleteByPrimaryKey(Integer id){
    	this.sysRoleResourceDao.deleteByPrimaryKey(id); 
    }

    public void insert(SysRoleResource model) {
    	this.sysRoleResourceDao.insert(model); 
    }
    
    public void insertSelective(SysRoleResource model){
    	this.sysRoleResourceDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(SysRoleResource model){
    	this.sysRoleResourceDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(SysRoleResource model) {
		this.sysRoleResourceDao.updateByPrimaryKey(model);
    }
    
    public List<SysRoleResource> selectList(SysRoleResource sysRoleResource){
    	return sysRoleResourceDao.selectList(sysRoleResource);
    }
    
    public List<SysRoleResource> findAll() {
		return sysRoleResourceDao.findAll();
    }

    public void deleteAll() {
		this.sysRoleResourceDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.sysRoleResourceDao.deleteByIds(idsList);
		}else {
			this.sysRoleResourceDao.deleteByPrimaryKey(Integer.valueOf(ids));
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,SysRoleResourceVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<SysRoleResourceVo>  list = this.sysRoleResourceDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    }

	@Override
	public void deleteByRoleId(Integer roleId) {
		sysRoleResourceDao.deleteByRoleId(roleId);
		
	} 	
    
    
}

