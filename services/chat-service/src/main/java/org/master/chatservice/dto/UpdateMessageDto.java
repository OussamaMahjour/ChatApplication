package org.master.chatservice.dto;

import lombok.*;
import org.master.chatservice.entity.MessageType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessageDto {
    private String id;
    String body;
    Boolean seen;
}
