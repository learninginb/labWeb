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
import com.simulation.core.shiro.ShiroUser;
import com.simulation.dao.mybatis.sys.SysResourceDao;
import com.simulation.model.sys.SysResource;
import com.simulation.service.sys.SysResourceService;
import com.simulation.vo.sys.SysResourceVo;

@Transactional
@Service("sysResourceService")
public class SysResourceServiceImpl implements SysResourceService{

	private final static Logger log= Logger.getLogger(SysResourceServiceImpl.class);

	@Autowired
	private SysResourceDao sysResourceDao;

	
	public SysResource selectByPrimaryKey(Integer id){
	    return sysResourceDao.selectByPrimaryKey(id);
	}

    public void deleteByPrimaryKey(Integer id){
    	this.sysResourceDao.deleteByPrimaryKey(id); 
    }

    public void insert(SysResource model) {
    	model.setCreateTime(new Date());
    	model.setCreateUserId(ShiroUser.getUserId());
    	this.sysResourceDao.insert(model); 
    }
    
    public void insertSelective(SysResource model){
    	model.setCreateTime(new Date());
    	model.setCreateUserId(ShiroUser.getUserId());
    	this.sysResourceDao.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(SysResource model){
    	model.setModifyTime(new Date());
    	model.setModifyUserId(ShiroUser.getUserId());
    	this.sysResourceDao.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(SysResource model) {
    	model.setModifyTime(new Date());
    	model.setModifyUserId(ShiroUser.getUserId());
		this.sysResourceDao.updateByPrimaryKey(model);
    }
    
    public List<SysResource> selectList(SysResource sysResource){
    	return sysResourceDao.selectList(sysResource);
    }
    
    public List<SysResource> findAll() {
		return sysResourceDao.findAll();
    }

    public void deleteAll() {
		this.sysResourceDao.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				//检查是否有下级或者引用
				Integer resourceId = Integer.valueOf((idArr[i]));
				Integer count = sysResourceDao.selectResourceReference(resourceId);
				if (count<1) {
					idsList.add(count);
				}
			}
			if (!PublicUtil.checkEmptyList(idsList)) {
				this.sysResourceDao.deleteByIds(idsList);
			}
		}else {
			Integer resourceId = Integer.valueOf(ids);
			Integer count = sysResourceDao.selectResourceReference(resourceId);
			if (count<1) {
				this.sysResourceDao.deleteByPrimaryKey(resourceId);
			}
			
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,SysResourceVo vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<SysResourceVo>  list = this.sysResourceDao.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    } 
    
    @Override
	public List<SysResource> selectListByParentId(Integer parentId) {
		
		return this.sysResourceDao.selectListByParentId(parentId);
	}

	@Override
	public void saveAndCreateRes(SysResource po, boolean createButton) {
		po.setCreateTime(new Date());
		po.setCreateUserId(ShiroUser.getUserId());
    	this.sysResourceDao.insertSelective(po); 
    	if(createButton){
    		this.createButtonRes(po);
    	}
	}
	
	public SysResourceVo selectVoByPrimaryKey(Integer id) {
		return this.sysResourceDao.selectVoByPrimaryKey(id);
	}

	@Override
	public void createButtonRes(SysResource po) {
		String permissionStr = po.getPermissionStr();
		String resName = null;
		if(null != permissionStr && !"".equals(permissionStr.trim())){
			int len = permissionStr.indexOf(":");
			if(len != -1){
				resName = permissionStr.substring(0, len);
			}
		}
		if(null == resName ){
			return;
		}
		SysResource res = new SysResource();
		res.setParentId(po.getId());
		res.setResourcePath("/");
		res.setLevel(po.getLevel() + 1);
		res.setCreateTime(new Date());
		res.setCreateUserId(ShiroUser.getUserId());
		res.setIsEnable(1);
		//添加
		res.setResourceName(po.getResourceName() + "-添加");
		res.setOrderNum(1);
		res.setPermissionStr(resName + ":add");
		res.setResourceDesc(po.getResourceName() + "添加操作");
		res.setId(null);
		res.setCreateTime(new Date());
		res.setCreateUserId(ShiroUser.getUserId());
		this.insertSelective(res);
		//删除
		res.setResourceName(po.getResourceName() + "-删除");
		res.setOrderNum(2);
		res.setPermissionStr(resName + ":remove");
		res.setResourceDesc(po.getResourceName() + "删除操作");
		res.setId(null);
		res.setCreateTime(new Date());
		res.setCreateUserId(ShiroUser.getUserId());
		this.insertSelective(res);
		//编辑
		res.setResourceName(po.getResourceName() + "-编辑");
		res.setOrderNum(3);
		res.setPermissionStr(resName + ":edit");
		res.setResourceDesc(po.getResourceName() + "编辑操作");
		res.setCreateTime(new Date());
		res.setCreateUserId(ShiroUser.getUserId());
		res.setId(null);
		this.insertSelective(res);
		//查看
		res.setResourceName(po.getResourceName() + "-查看");
		res.setOrderNum(4);
		res.setPermissionStr(resName + ":info");
		res.setResourceDesc(po.getResourceName() + "查看操作");
		res.setId(null);
		res.setCreateTime(new Date());
		res.setCreateUserId(ShiroUser.getUserId());
		this.insertSelective(res);
	}

	@Override
	public List<SysResourceVo> findAuthorizationAll(Integer roleId) {
		return sysResourceDao.findAuthorizationAll(roleId);
	}

	@Override
	public List<SysResource> findAllByUserId(Integer userId) {
		return this.sysResourceDao.findAllByUserId(userId);
	}

	@Override
	public Integer selectResourceReference(Integer resourceId) {
		return this.sysResourceDao.selectResourceReference(resourceId);
	}
}

