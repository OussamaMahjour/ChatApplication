package org.master.chatservice.service;


import lombok.AllArgsConstructor;
import org.master.chatservice.Mapper.Mapper;
import org.master.chatservice.dto.ChatResponseDto;
import org.master.chatservice.dto.MessageRequestDto;
import org.master.chatservice.dto.MessageResponseDto;
import org.master.chatservice.dto.UpdateMessageDto;
import org.master.chatservice.entity.Chat;
import org.master.chatservice.entity.ChatType;
import org.master.chatservice.entity.Message;
import org.master.chatservice.repository.ChatRepository;
import org.master.chatservice.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatService {
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;
    private Mapper mapper;

    public void save(String username, Message message) {
        List<String> participants =new ArrayList<>();
        participants.add(username);
        participants.add(message.getOwner());
        Chat chat = chatRepository.findByParticipants(participants);
        if (chat == null) {
            chat = Chat.builder()
                    .id(UUID.randomUUID().toString())
                    .participants(participants)
                    .type(ChatType.PRIVATE)
                    .build();
            chatRepository.save(chat);
        }
        message.setConversationId(chat.getId());
        message.setId(UUID.randomUUID().toString());
        message.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message);
    }

    public List<ChatResponseDto> findChats(String username) {
        List<Chat> chats = chatRepository.findByParticipantsContaining(username);
        List<ChatResponseDto> chatResponseDtos = new ArrayList<>();
        for (Chat chat : chats) {
            List<MessageResponseDto> messages = messageRepository.findByConversationId(chat.getId())
                    .stream()
                    .map(mapper::MessageToMessageResponseDTO)
                    .toList();

            chatResponseDtos.add(ChatResponseDto.builder()
                            .username(chat.getParticipants().stream().filter(item -> !item.equals(username)).findFirst().get())
                            .messages(messages)
                    .build());
        }
        return chatResponseDtos;
    }
    public void updateMessage(UpdateMessageDto messageUpdate) {
        messageRepository.findById(messageUpdate.getId())
                .ifPresent(message -> messageRepository.save(Message.builder()
                            .id(message.getId())
                            .owner(message.getOwner())
                            .conversationId(message.getConversationId())
                            .body(messageUpdate.getBody())
                            .createdAt(message.getCreatedAt())
                            .seen(messageUpdate.getSeen())
                            .type(message.getType())
                            .build()));

    }
}
