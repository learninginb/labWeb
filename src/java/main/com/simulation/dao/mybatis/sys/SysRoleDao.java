package com.simulation.dao.mybatis.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.sys.SysRole;
import com.simulation.vo.sys.SysRoleVo;

public interface SysRoleDao extends BaseMybatisDao<SysRole,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<SysRole> selectList(SysRole sysRole);
  
    List<SysRoleVo> findListByPage(@Param("vo") SysRoleVo vo,@Param("page")Pagination page);

    List<String> findRoleTypesByUserId(Integer userId); 
}
