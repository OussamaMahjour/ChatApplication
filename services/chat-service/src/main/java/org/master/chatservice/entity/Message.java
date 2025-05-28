package org.master.chatservice.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document(value="Messages")
@Builder
public class Message {
    @Id
    private String id;
    String body;
    String owner;
    boolean seen;
    MessageType type;
    String conversationId;
    @CreatedDate
    LocalDateTime createdAt;

}

