package com.simulation.service.sys;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.sys.SysUserRole;
import com.simulation.vo.sys.SysUserRoleVo;

public interface SysUserRoleService extends BaseMybatisService<SysUserRole,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<SysUserRole> selectList(SysUserRole sysUserRole);
    
    public Pagination findListByPage(int rows, int page,SysUserRoleVo vo);
    
    public List<SysUserRoleVo> selectByUserId(Integer userId);
    
    /**
     * 
    * Description:根据用户id删除关系    
    * @Title: deleteByUserId  
    * @author Jalf
    * @since 2016年5月30日 下午5:15:35
    * @param userId
    * Copyright  https://shop106571874.taobao.com All right reserved.
     */
    public void deleteByUserId(Integer userId);
    
    /**
     * 
    * Description:根据角色id删除关系    
    * @Title: deleteByRoleId  
    * @author Jalf
    * @since 2016年5月30日 下午5:15:49
    * @param roleId
    * Copyright  https://shop106571874.taobao.com All right reserved.
     */
    public void deleteByRoleId(Integer roleId);
}

