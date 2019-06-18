package com.simulation.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月9日 上午9:55:13  
*/	
public class InfoWebSocketHandler implements WebSocketHandler {

	private static final ArrayList<WebSocketSession> users;
	private static Logger log = Logger.getLogger(InfoWebSocketHandler.class);
	static{
		users = new ArrayList<WebSocketSession>();
	}
	
	public InfoWebSocketHandler() {
	
	}
	/**
	 * 关闭时触发
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1) throws Exception {
		log.debug("用户:"+session.getAttributes().get("WEBSOCKET_NICKNAME")+"断开了连接");
		users.remove(session);
		
	}
	/**
	 * 连接时触发
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("用户:"+session.getAttributes().get("WEBSOCKET_NICKNAME")+"连接成功");
		users.add(session);
		log.info("当前在线人数:"+users.size());
	}
	/**
	 * 接收客户端发送过来的消息
	 */
	@Override
	public void handleMessage(WebSocketSession arg0, WebSocketMessage<?> arg1) throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 出现错误时触发
	 */
	@Override
	public void handleTransportError(WebSocketSession arg0, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	/**
	 * 发送消息
	 * @Desc
	 * @param userName
	 * @param message
	 * @author spxin
	 * @Date 2019年5月9日 
	 */
	public void sendMessageToUser(int userId,TextMessage message){
		for(WebSocketSession user:users){
			if(((Integer)user.getAttributes().get("WEBSOCKET_NICKNAME")).intValue()==userId){
				try{
					if(user.isOpen()){
						user.sendMessage(message);
					}
				}catch(IOException e){
					e.printStackTrace();
				}
				break;
			}
			
		}
	}
	/**
	 * 给所有用户发消息
	 * @Desc
	 * @param message
	 * @author spxin
	 * @Date 2019年5月9日 ${time}
	 */
	public void sendMessageToUsers(TextMessage message){
		for(WebSocketSession user:users){
			try{
				if(user.isOpen()){
					user.sendMessage(message);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
