package com.simulation.websocket.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.simulation.vo.sys.SysUserActiveVo;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月9日 上午10:34:39  
*/
public class InfoWesocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		// TODO Auto-generated method stub
		super.afterHandshake(request, response, wsHandler, ex);
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
			Map<String, Object> attributes) throws Exception {
		System.out.println("Before Handshake");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                //使用userName区分WebSocketHandler，以便定向发送消息
                SysUserActiveVo user = (SysUserActiveVo) SecurityUtils.getSubject().getPrincipal();
                Integer userId = user.getId();
                if (userId==null) {
                	userId=0;
                }
                attributes.put("WEBSOCKET_NICKNAME",userId);
            }
        }
		return super.beforeHandshake(request, response, handler, attributes);
	}
	
	
}
