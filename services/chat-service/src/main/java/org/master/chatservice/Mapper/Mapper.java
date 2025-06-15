package org.master.chatservice.Mapper;


import org.master.chatservice.dto.MessageRequestDto;
import org.master.chatservice.dto.MessageResponseDto;
import org.master.chatservice.dto.UpdateMessageDto;
import org.master.chatservice.entity.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Mapper {
    public MessageResponseDto MessageToMessageResponseDTO(Message message) {
        return MessageResponseDto.builder()
                .id(message.getId())
                .type(message.getType())
                .owner(message.getOwner())
                .body(message.getBody())
                .sentAt(message.getCreatedAt())
                .seen(message.isSeen())
                .build();
    }

}
