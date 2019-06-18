package com.simulation.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;
import com.simulation.service.sys.RedisService;


public class SystemWebSocketHandler implements WebSocketHandler {
	
    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;
    //存放用户对应的测试设备
    private static final Map<String,String> test_ids=new HashMap<String,String>();
    @Autowired
    private RedisService redis;
    private String key="";
    private String value="";
    /**
     * onopen
     * 
     * 
     * */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	System.out.println(session.getAttributes().get("WEBSOCKET_USERNAME")+"连接上服务器");
        users.add(session);
    }
    /***
     * onmessage
     * 
     * 
     * **/
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    	System.out.println(message.getPayload());
    	
//以下全是学长写的方法，是为了传键值对的  
         JSONObject data = JSONObject.parseObject(message.getPayload().toString());
         //如果key为0标记在线类型
         if(data.get("key").toString().equals("0")){
        	 test_ids.put(session.getId(), data.get("value").toString());
         }else if(data.get("key").toString().equals("1")){        	 
        	 double randomdata=Math.random();
        	 String str = ""+randomdata;
        	 TextMessage tx=new TextMessage(str,true);
        	 System.out.println(session.getId());
        	 sendMessageToUsers(session.getId(),tx);
         }else if(data.get("key").toString().equals("2")){
        	 //这里写的方法是：怎么把tag和value传给模型，tag对应的参数去t_configulation_element找        	 
        	 //这里传的是控制器的值
        	 data.get("tag").toString();
        	 data.get("value").toString();
         }else if(data.get("key").toString().equals("3")){
        	 data.get("tag").toString();
        	 data.get("value").toString();
		}
         else{
          key=data.get("key").toString()+UUID.randomUUID().toString().replaceAll("-", "");
          value=data.get("value").toString();
          System.out.println("key:"+key);
          System.out.println("value:"+value);
          this.redis.add(key, value);
         }
         //调用了发给用户的方法
//         TextMessage textMessage = new TextMessage("一个测试用的message", true);         
//         sendMessageToUsers(session.getId(),textMessage);
    }
    
    /**
     * onerror
     * 
     * 
     * */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        users.remove(session);
        test_ids.remove(session.getId());
    }
    
    /**
     * onclose
     * 
     * */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	System.out.println("断开服务器链接");
    	users.remove(session);
    }
 
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
 
    /**
     * 给指定用户发送指定信息
     *
     * @param message
     */
    public void sendMessageToUsers(String test_id,TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                	
                	if(user.getId()!=null
                			&&user.getId().equals(test_id))
                    {user.sendMessage(message);
                	System.out.println("websocket发了,值是："+message.getPayload());
                	}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
}