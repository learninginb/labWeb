package com.simulation.service.sys;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.sys.SysRoleResource;
import com.simulation.vo.sys.SysRoleResourceVo;

public interface SysRoleResourceService extends BaseMybatisService<SysRoleResource,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<SysRoleResource> selectList(SysRoleResource sysRoleResource);
    
    public Pagination findListByPage(int rows, int page,SysRoleResourceVo vo);
    
    public void deleteByRoleId(Integer roleId);
   
}

