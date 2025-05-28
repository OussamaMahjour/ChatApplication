package org.master.chatservice.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@Document(value="Chats")
@Builder
public class Chat {
    @Id
    String id;
    List<String> participants;
    ChatType type;
}

