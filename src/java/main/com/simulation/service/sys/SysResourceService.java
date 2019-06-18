package com.simulation.service.sys;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.sys.SysResource;
import com.simulation.vo.sys.SysResourceVo;

public interface SysResourceService extends BaseMybatisService<SysResource,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<SysResource> selectList(SysResource sysResource);
    
    public Pagination findListByPage(int rows, int page,SysResourceVo vo);
    
    public List<SysResource> selectListByParentId(Integer parentId);
    
 	public void saveAndCreateRes(SysResource po,boolean createButton);
 	//生成按钮资源
 	public void createButtonRes (SysResource po);
 	
 	public SysResourceVo selectVoByPrimaryKey(Integer id);
 	
 	public List<SysResourceVo> findAuthorizationAll(Integer roleId);
 	
	public List<SysResource> findAllByUserId(Integer userId);
	
	
	public Integer selectResourceReference(Integer resourceId);
 	
 	
}

