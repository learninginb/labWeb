package com.simulation.dao.mybatis.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.sys.SysUserRole;
import com.simulation.vo.sys.SysUserRoleVo;

public interface SysUserRoleDao extends BaseMybatisDao<SysUserRole,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<SysUserRole> selectList(SysUserRole sysUserRole);
  
    List<SysUserRoleVo> findListByPage(@Param("vo") SysUserRoleVo vo,@Param("page")Pagination page);
    
    List<SysUserRoleVo> selectByUserId(Integer userId);
    
    /**
    * Description:根据角色id删除关系
    * @Title: deleteByRoleId  
    * @author Jalf
    * @since 2016年5月30日 下午5:14:14
    * @param roleId
    * Copyright  https://shop106571874.taobao.com All right reserved.
     */
    public void deleteByRoleId(Integer roleId);
    
    /**
     * 根据用户id删除角色
    * Description:    
    * @Title: deleteByUserId  
    * @author Jalf
    * @since 2016年5月30日 下午5:15:00
    * @param userId
    * Copyright  https://shop106571874.taobao.com All right reserved.
     */
    public void deleteByUserId(Integer userId);
    
    /**
     * 
    * Description:根据角色id查找绑定的总数    
    * @Title: selectCountByRoleId  
    * @author Jalf
    * @since 2016年6月2日 上午8:41:01
    * @param roleId
    * @return
    * Copyright  foxtail All right reserved.
     */
    public Integer selectCountByRoleId(Integer roleId);

}
