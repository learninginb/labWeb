package com.simulation.dao.mybatis.sys;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.sys.SysUser;
import com.simulation.vo.sys.SysUserVo;

public interface SysUserDao extends BaseMybatisDao<SysUser,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<SysUser> selectList(SysUser sysUser);
  
    List<SysUserVo> findListByPage(@Param("vo") SysUserVo vo,@Param("page")Pagination page);

    /**
    * Description:    
    * @Title: findSingleUser  根据账户，电子邮件，电话号码或者身份证号查找用户
    * @author Jalf
    * @since 2016年5月26日 下午5:11:43
    * @param account
    * @return
    * Copyright  https://shop106571874.taobao.com All right reserved.
     */
    public SysUser findSingleUser(String account);
    
    /**
     * Description:查询账户是否存在    
     * @Title: selectCountIsExist  
     * @author Jalf
     * @since 2016年6月6日 上午11:20:08
     * @param hashMap
     * @return
     * Copyright  https://shop106571874.taobao.com All right reserved.
      */
     public Integer selectCountIsExist(Map<String, String> map);
    public List<SysUser> findAllUserIdAndName(@Param("location")String location);
     
}
