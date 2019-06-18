package com.simulation.controller.admin.basedata;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simulation.common.page.Pagination;
import com.simulation.common.web.DataGrid;
import com.simulation.core.util.PageUtils;
import com.simulation.model.basedata.ResponseParam;

import com.simulation.service.basedata.MessageService;
import com.simulation.vo.basedata.MessageVo;
import com.simulation.vo.sys.SysUserActiveVo;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月9日 下午4:19:45  
*/
@Controller
@RequestMapping("/MessageController")
public class MessageController {
	@Autowired
	private MessageService messageService;
	
	@RequestMapping("/toList")
	public String toList(){
		return "base_data/message/message_list";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid managerList(MessageVo message){
		int pageNo = PageUtils.getPage();
		int pageSize = PageUtils.getRows();
		DataGrid dataGrid = new DataGrid();
		SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
		message.setReceiverId(user.getId());
		Pagination pagination = messageService.findListByPage(pageSize, pageNo, message);
		dataGrid.setTotal(pagination.getTotalCount());
		dataGrid.setRows(pagination.getList());
		return dataGrid;
	}
	
	@RequestMapping(value="/messageCount",method=RequestMethod.GET)
	@ResponseBody
	public ResponseParam getMessageCount(){
		return messageService.findMessageCount();
	}
	
	/**
	 * 更改mesage的状态
	 * @Desc
	 * @param messageId
	 * @return
	 * @author spxin
	 * @Date 2019年5月10日 ${time}
	 */
	@RequestMapping(value ="/message/{messageId}",method=RequestMethod.POST)
	@ResponseBody
	public ResponseParam changeIsRead(@PathVariable("messageId") int messageId){
		MessageVo messageVo = new MessageVo();
		messageVo.setId(messageId);
		messageVo.setIsRead(1);
		return messageService.changeMessage(messageVo);
		
	}
}
