package com.simulation.dao.mybatis.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.sys.SysResource;
import com.simulation.vo.sys.SysResourceVo;

public interface SysResourceDao extends BaseMybatisDao<SysResource,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<SysResource> selectList(SysResource sysResource);
  
    List<SysResourceVo> findListByPage(@Param("vo") SysResourceVo vo,@Param("page")Pagination page);
    
    public List<SysResource> selectListByParentId(Integer parentId);
    
    public SysResourceVo selectVoByPrimaryKey(Integer id);
    
    public List<SysResourceVo> findAuthorizationAll(Integer roleId);
   
    public List<SysResource> findAllByUserId(Integer userId);
    
    public Integer selectCountByParentId(Integer parentId);
    
    
    public Integer selectResourceReference(Integer resourceId);


}
