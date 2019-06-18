package com.simulation.service.sys;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.sys.SysUser;
import com.simulation.model.sys.SysUserRole;
import com.simulation.vo.sys.SysUserVo;

public interface SysUserService extends BaseMybatisService<SysUser,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<SysUser> selectList(SysUser sysUser);
    
    public Pagination findListByPage(int rows, int page,SysUserVo vo);
    /**
     * Description:
     * @Title: findSingleUser  根据账户，电子邮件，电话号码或者身份证号查找用户
     * @param account
     * @return
      */
     public SysUser findSingleUser(String account);
     
     public void setUserRole(SysUserRole[] sysUserRoles);
     
     /**
      * 检查是否重复
     * Description:    
     * @Title: findExist
     * @param name
     * @param type
      */
     public boolean findIsExist(String name,String type);
     
     
     public String updatePassword(SysUser po);
     
     public List<SysUser> findAllUserIdAndName();
   
}

