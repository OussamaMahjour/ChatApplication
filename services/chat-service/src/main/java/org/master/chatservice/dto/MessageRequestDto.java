package org.master.chatservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.master.chatservice.entity.MessageType;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class MessageRequestDto {
    @Nullable
    String body;
    String owner;
    MessageType type;
    @Nullable
    MultipartFile file;
}
