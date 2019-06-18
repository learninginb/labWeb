package com.simulation.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.common.page.Pagination;
import com.simulation.dao.mybatis.sys.SysUserRoleDao;
import com.simulation.model.sys.SysUserRole;
import com.simulation.service.sys.SysUserRoleService;
import com.simulation.vo.sys.SysUserRoleVo;

@Transactional
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService{

	private final static Logger log= Logger.getLogger(SysUserRoleServiceImpl.class);

	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	
	public SysUserRole selectByPrimaryKey(Integer id){
	    return sysUserRoleDao.selectByPrimaryKey(id);
	}

    public void deleteByPrimaryKey(Integer id){
    	this.sysUserRoleDao.deleteByPrimaryKey(id); 
    }

    public void insert(SysUserRole model) {
    	this.sysUserRoleDao.insert(model); 
    }
    
    public void insertSelective(SysUserRole model){
    	this.sysUserRoleDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(SysUserRole model){
    	this.sysUserRoleDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(SysUserRole model) {
		this.sysUserRoleDao.updateByPrimaryKey(model);
    }
    
    public List<SysUserRole> selectList(SysUserRole sysUserRole){
    	return sysUserRoleDao.selectList(sysUserRole);
    }
    
    public List<SysUserRole> findAll() {
		return sysUserRoleDao.findAll();
    }

    public void deleteAll() {
		this.sysUserRoleDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.sysUserRoleDao.deleteByIds(idsList);
		}else {
			this.sysUserRoleDao.deleteByPrimaryKey(Integer.valueOf(ids));
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,SysUserRoleVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<SysUserRoleVo>  list = this.sysUserRoleDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    } 	
    
    @Override
    public List<SysUserRoleVo> selectByUserId(Integer userId){
    	return this.sysUserRoleDao.selectByUserId(userId);
    }

	@Override
	public void deleteByUserId(Integer userId) {
		this.sysUserRoleDao.deleteByUserId(userId);
		
	}

	@Override
	public void deleteByRoleId(Integer roleId) {
		this.sysUserRoleDao.deleteByRoleId(roleId);
	}
}

