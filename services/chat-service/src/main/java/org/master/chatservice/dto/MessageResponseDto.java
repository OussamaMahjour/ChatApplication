package org.master.chatservice.dto;

import lombok.*;
import org.master.chatservice.entity.MessageType;

import java.time.LocalDateTime;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class MessageResponseDto {
    private String id;
    private String body;
    private String owner;
    private boolean seen;
    private MessageType type;
    private LocalDateTime sentAt;
}
