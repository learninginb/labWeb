package com.simulation.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.common.page.Pagination;
import com.simulation.common.util.PublicUtil;
import com.simulation.core.shiro.CustomRealm;
import com.simulation.core.shiro.ShiroUser;
import com.simulation.dao.mybatis.sys.SysRoleDao;
import com.simulation.dao.mybatis.sys.SysRoleResourceDao;
import com.simulation.dao.mybatis.sys.SysUserRoleDao;
import com.simulation.model.sys.SysRole;
import com.simulation.model.sys.SysRoleResource;
import com.simulation.service.sys.SysRoleService;
import com.simulation.vo.sys.SysRoleVo;

@Transactional
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService{

	private final static Logger log= Logger.getLogger(SysRoleServiceImpl.class);

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleResourceDao sysRoleResourceDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	private CustomRealm customRealm;

	
	public SysRole selectByPrimaryKey(Integer id){
	    return sysRoleDao.selectByPrimaryKey(id);
	}

    public void deleteByPrimaryKey(Integer id){
    	this.sysRoleDao.deleteByPrimaryKey(id); 
    }

    public void insert(SysRole model) {
    	model.setCreateTime(new Date());
    	model.setCreateUserId(ShiroUser.getUserId());
    	this.sysRoleDao.insert(model); 
    }
    
    public void insertSelective(SysRole model){
    	model.setCreateTime(new Date());
    	model.setCreateUserId(ShiroUser.getUserId());
    	this.sysRoleDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(SysRole model){
    	model.setModifyTime(new Date());
    	model.setModifyUserId(ShiroUser.getUserId());
    	this.sysRoleDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(SysRole model) {
    	model.setModifyTime(new Date());
    	model.setModifyUserId(ShiroUser.getUserId());
		this.sysRoleDao.updateByPrimaryKey(model);
    }
    
    public List<SysRole> selectList(SysRole sysRole){
    	return sysRoleDao.selectList(sysRole);
    }
    
    public List<SysRole> findAll() {
		return sysRoleDao.findAll();
    }

    public void deleteAll() {
		this.sysRoleDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				Integer roleId=Integer.valueOf(idArr[i]);
				Integer count = sysUserRoleDao.selectCountByRoleId(roleId);
				if (count<1) {
					idsList.add(roleId);
					//删除角色和资源的关系
					sysRoleResourceDao.deleteByRoleId(roleId);
				}
			}
			if (!PublicUtil.checkEmptyList(idsList)) {
				this.sysRoleDao.deleteByIds(idsList);
			}
		}else {
			Integer roleId=Integer.valueOf(ids);
			Integer count = sysUserRoleDao.selectCountByRoleId(roleId);
			if (count<1) {
				this.sysRoleDao.deleteByPrimaryKey(roleId);
			}
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,SysRoleVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<SysRoleVo>  list = this.sysRoleDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    }

	@Override
	public void setRoleResources(SysRoleResource[] resources) {
		if (null != resources && resources.length > 0) {
			Integer roleId = resources[0].getRoleId();
			sysRoleResourceDao.deleteByRoleId(roleId);
			for (SysRoleResource roleResource : resources) {
				sysRoleResourceDao.insertSelective(roleResource);
			}
			customRealm.clearCached();
		}
	}

	@Override
	public List<String> findRoleTypesByUserId(Integer userId) {
		return sysRoleDao.findRoleTypesByUserId(userId);
	}
}

