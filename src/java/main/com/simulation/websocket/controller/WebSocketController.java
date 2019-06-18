package com.simulation.websocket.controller;



import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simulation.websocket.handler.SystemWebSocketHandler;

@Controller
@RequestMapping("/websocket")
public class WebSocketController {
	
	@Bean
    public SystemWebSocketHandler systemWebSocketHandler(){
        return new SystemWebSocketHandler();
    }
	
	@RequestMapping("toClient")
	public String toClient(){
		return "client" ;
	}
	

	
	
	@RequestMapping("toRun")
	public void toRun(){
		
	}
}
