package org.master.chatservice.controller;


import lombok.AllArgsConstructor;
import org.master.chatservice.entity.Chat;
import org.master.chatservice.entity.Message;
import org.master.chatservice.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

public class ChatController {
    private ChatService chatService;

    @MessageMapping("/{username}/chat")
    public void test(@DestinationVariable String username, Message message){
       chatService.save(username, message);
    }



}
