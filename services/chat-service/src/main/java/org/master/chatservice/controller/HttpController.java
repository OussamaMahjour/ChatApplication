package org.master.chatservice.controller;

import lombok.AllArgsConstructor;
import org.master.chatservice.dto.ChatResponseDto;
import org.master.chatservice.dto.MessageRequestDto;
import org.master.chatservice.dto.UpdateMessageDto;
import org.master.chatservice.entity.Chat;
import org.master.chatservice.repository.MessageRepository;
import org.master.chatservice.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/chat")
public class HttpController {
    private final MessageRepository messageRepository;
    private ChatService chatService;


    @GetMapping("/{username}")
    public List<ChatResponseDto> getChats(@PathVariable String username){
        return chatService.findChats(username);
    }

    @PutMapping("/message")
    public void updateMessage(@RequestBody UpdateMessageDto updateDto){
        chatService.updateMessage(updateDto);
    }
}
