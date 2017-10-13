package com.niit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.niit.model.Chat;

@Controller
public class SockController {
	
	private SimpMessagingTemplate messageTemplate;
	private  List<String> users= new ArrayList<String>();
	
	@Autowired
	public SockController(SimpMessagingTemplate messageTemplate) {
		super();
		this.messageTemplate = messageTemplate;
	}
	
	@SubscribeMapping("/join/{username}")
	public List<String> join(@DestinationVariable("username") String username){
		if(!users.contains(username)){
			users.add(username);
		}
		messageTemplate.convertAndSend("/topic/join",username);
		return users;
	}
	
	@MessageMapping(value="/chat")
	public void chatsReceived(Chat chat){
		if(chat.getTo().equals("all")){
			messageTemplate.convertAndSend("/queue/chats",chat);
		}
		else{
			messageTemplate.convertAndSend("/queue/chats/"+chat.getFrom(),chat);
			messageTemplate.convertAndSend("/queue/chats/"+chat.getTo(),chat);
		}
	}
}
