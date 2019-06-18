package com.simulation.service.knowledge.impl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.common.page.Pagination;
import com.simulation.core.shiro.ShiroUser;
import com.simulation.dao.mybatis.knowledge.PaperDao;
import com.simulation.model.knowledge.Paper;
import com.simulation.service.knowledge.PaperService;
import com.simulation.vo.knowledge.PaperVo;
import com.simulation.vo.sys.SysUserActiveVo;

@Transactional
@Service("PaperServiceImpl")
public class PaperServiceImpl implements PaperService {
	public static String PAPER_PATH="jsp/paper/upload";
	
	@Autowired
	private PaperDao paperDao;
	
	private final static Logger log= Logger.getLogger(PaperServiceImpl.class);

	
	@Override
	public Paper selectByPrimaryKey(Integer paramPK) {
		// TODO Auto-generated method stub
		return paperDao.selectByPrimaryKey(paramPK);
	}

	@Override
	public void deleteByPrimaryKey(Integer paramPK) {
		// TODO Auto-generated method stub
		paperDao.deleteByPrimaryKey(paramPK);
		
	}

	@Override
	public void insert(Paper paramT) {
		// TODO Auto-generated method stub
		paperDao.insert(paramT);
	}

	@Override
	public void insertSelective(Paper paramT) {
		// TODO Auto-generated method stub
		paperDao.insertSelective(paramT);
		
	}

	@Override
	public void updateByPrimaryKeySelective(Paper paramT) {
		// TODO Auto-generated method stub
		paperDao.updateByPrimaryKeySelective(paramT);
	}

	@Override
	public void updateByPrimaryKey(Paper paramT) {
		// TODO Auto-generated method stub
		paperDao.updateByPrimaryKey(paramT);
	}

	@Override
	public List<Paper> findAll() {
		// TODO Auto-generated method stub
		return paperDao.findAll();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		paperDao.deleteAll();
	}

	@Override
	public void deleteIds(String ids) {
		String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				try{
				String filePath = this.paperDao.selectByPrimaryKey(Integer.valueOf(idArr[i])).getFile_url();
				idsList.add(Integer.valueOf(idArr[i]));
				
				File file = new File(System.getProperty("user.dir")+File.separator+PAPER_PATH+File.separator+filePath);
				if(file.exists()){
					file.delete();
				}
				
				}catch(Exception e){
					log.error("删除文献出错");
					log.error(e.getStackTrace().toString());
				}
			}
			this.paperDao.deleteByIds(idsList);
		}else {
			try{
				String filePath = this.paperDao.selectByPrimaryKey(Integer.valueOf(ids)).getFile_url();
				this.paperDao.deleteByPrimaryKey(Integer.valueOf(ids));
				File file = new File(System.getProperty("user.dir")+File.separator+PAPER_PATH+File.separator+filePath);
				if(file.exists()){
					file.delete();
				}
			}catch(Exception e){
				log.error("删除文献出错");
				log.error(e.getStackTrace().toString());
			}
		}
	}

	@Override
	public List<Paper> selectList(Paper po) {
		// TODO Auto-generated method stub
		return paperDao.selectList(po);
	}

	@Override
	public Pagination findListByPage(int rows, int page, PaperVo vo) {
		// TODO Auto-generated method stub
		Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
	    List<PaperVo>  list = this.paperDao.findListByPage(vo,user.getLocation(),pagination);
	    pagination.setList(list);
	    return pagination;
	}

	@Override
	public String save(Paper po) {
		String msg = validate(po);
		if(msg!=null){
			return msg;
		}
		if(po.getId()!=null){
			this.updateByPrimaryKeySelective(po);
		}else{
			this.insert(po);
		}
		return null;
	}
	
	public String validate(Paper po){
		if(po.getName()==null){
			return "名称不能为空";
		}
		if(po.getStatus()==null){
			return "状态不能为空";
		}
		if(po.getFile_url()==null){
			return "文件不能为空";
		}
		List<Paper> dateType = this.paperDao.selectObj(po.getName());
		if(dateType!=null&&dateType.size()>0){
			return "名称重复";
		}
		return null;
	}

	@Override
	public String deleteByIds(String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HSSFWorkbook export() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
