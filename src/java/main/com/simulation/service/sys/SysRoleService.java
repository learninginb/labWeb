package com.simulation.service.sys;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.sys.SysRole;
import com.simulation.model.sys.SysRoleResource;
import com.simulation.vo.sys.SysRoleVo;

public interface SysRoleService extends BaseMybatisService<SysRole,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<SysRole> selectList(SysRole sysRole);
    
    public Pagination findListByPage(int rows, int page,SysRoleVo vo);
   
    /**
     * 授权
    * Description:    
    * @Title: setRoleResources
    * @param resources
     */
    public void setRoleResources(SysRoleResource[] resources);
    
    /**
     * 
    * Description:根据用户id查找角色    
    * @Title: findRoleTypesByUserId
    * @param userId
    * @return
     */
    public List<String> findRoleTypesByUserId(Integer userId);
}

