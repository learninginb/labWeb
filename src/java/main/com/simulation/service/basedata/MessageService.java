package com.simulation.service.basedata;

import com.simulation.common.page.Pagination;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.vo.basedata.MessageVo;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月9日 下午4:31:32  
*/
public interface MessageService {
	/**
	 * 消息分页
	 * @param pageSize 页码大小
	 * @param pageNo 页数起始索引
	 * @param message 消息体
	 * @return 分页器
	 */
	public Pagination findListByPage(int pageSize,int pageNo,MessageVo message);

	/**
	 * 查消息的总数
	 * @return 响应参数类
	 */
	public ResponseParam findMessageCount();

	/**
	 * 根据用户id给用户发送消息
	 * @param userId
	 * @param msg
	 * @param type
	 * @param url
	 */
	public void sendMessageToUser(Integer userId,String msg,String type,String url);
	/**
	 * 更新message
	 * @Desc
	 * @param messageVo
	 * @return
	 * @author spxin
	 * @Date 2019年5月10日 ${time}
	 */
	public ResponseParam changeMessage(MessageVo messageVo);
}
