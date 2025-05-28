package org.master.chatservice.dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChatResponseDto {
    private String username;
    private List<MessageResponseDto> messages;
}
