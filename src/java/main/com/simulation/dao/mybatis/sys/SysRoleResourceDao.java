package com.simulation.dao.mybatis.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.sys.SysRoleResource;
import com.simulation.vo.sys.SysRoleResourceVo;

public interface SysRoleResourceDao extends BaseMybatisDao<SysRoleResource,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<SysRoleResource> selectList(SysRoleResource sysRoleResource);
  
    List<SysRoleResourceVo> findListByPage(@Param("vo") SysRoleResourceVo vo,@Param("page")Pagination page);
    
    public void deleteByRoleId(Integer roleId);

}
