package com.simulation.service.basedata.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simulation.common.page.Pagination;
import com.simulation.dao.mybatis.basedata.NoticeInfoDao;
import com.simulation.model.basedata.NoticeInfo;
import com.simulation.service.basedata.NoticeInfoService;
import com.simulation.vo.basedata.NoticeInfoVo;

@Transactional
@Service("noticeInfoService")
public class NoticeInfoServiceImpl implements NoticeInfoService {

	private final static Logger log = Logger.getLogger(NoticeInfoServiceImpl.class);

	@Autowired
	private NoticeInfoDao noticeInfoDao;
	
	
	public NoticeInfo selectByPrimaryKey(Integer id) {
		return this.noticeInfoDao.selectByPrimaryKey(id);

	}

	public void deleteByPrimaryKey(Integer id) {
		this.noticeInfoDao.deleteByPrimaryKey(id);
	}

	public void insert(NoticeInfo model) {
		this.noticeInfoDao.insert(model);
	}

	public void insertSelective(NoticeInfo model) {
		this.noticeInfoDao.insertSelective(model);
	}

	public void updateByPrimaryKeySelective(NoticeInfo model) {
		this.noticeInfoDao.updateByPrimaryKeySelective(model);
	}

	public void updateByPrimaryKey(NoticeInfo model) {
		this.noticeInfoDao.updateByPrimaryKey(model);
	}

	public List<NoticeInfo> selectList(NoticeInfo knowledgeType) {
		return this.noticeInfoDao.selectList(knowledgeType);
	}

	public List<NoticeInfo> findAll() {
		return this.noticeInfoDao.findAll();
	}

	public void deleteAll() {
		this.noticeInfoDao.deleteAll();
	}

	@Override
	public void deleteIds(String ids) {
		String[] idArr = ids.split(",");
		if (idArr.length > 1) {
			List<Integer> idsList = new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.noticeInfoDao.deleteByIds(idsList);
		} else {
				this.noticeInfoDao.deleteByPrimaryKey(Integer.valueOf(ids));
			
		}
	}

	@Override
	public Pagination findListByPage(int rows, int page, NoticeInfoVo vo) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(page); // 当前页码
		pagination.setPageSize(rows); // 每页显示多少行
		List<NoticeInfoVo> list = this.noticeInfoDao.findListByPage(vo, pagination);
		pagination.setList(list);
		return pagination;
	}

	@Override
	public String save(NoticeInfo po) {
		String msg = validate(po);
		if (msg != null) {
			return msg;
		}
		if (po.getId() != null) {
			this.updateByPrimaryKeySelective(po);
		} else {
			this.insert(po);
		}
		return null;
	}

	public String validate(NoticeInfo po) {

		if (po.getContent() == null) {
			return "内容不能为空";
		}
		if (po.getStatus() == null) {
			return "状态不能为空";
		}
		if (po.getReceive_user_ids() == null) {
			return "接受人不能为空";
		}
		return null;
	}

	@Override
	public String deleteByIds(String ids) {
		if(ids==null){
			return "请选择删除项目";
		}else{
			
			return "请先删除工厂下其他信息";
			
		}
	}

	
}
