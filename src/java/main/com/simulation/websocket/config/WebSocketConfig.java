package com.simulation.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.simulation.websocket.handler.InfoWebSocketHandler;
import com.simulation.websocket.handler.SystemWebSocketHandler;
import com.simulation.websocket.interceptor.InfoWesocketHandlerInterceptor;


@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		 registry.addHandler(systemWebSocketHandler(),"/webSocketServer.do");
		 registry.addHandler(infoWebSocketHandler(), "/infoWebSocketServer.do").addInterceptors(new InfoWesocketHandlerInterceptor());
		
	}
	
	@Bean
    public WebSocketHandler systemWebSocketHandler(){
        return new SystemWebSocketHandler();
    }
	
	@Bean
	public WebSocketHandler infoWebSocketHandler(){
		return new InfoWebSocketHandler();
	}
	

}
