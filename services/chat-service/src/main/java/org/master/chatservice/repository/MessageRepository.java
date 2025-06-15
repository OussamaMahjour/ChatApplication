package org.master.chatservice.repository;

import org.master.chatservice.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    public List<Message> findByConversationId(String id);
}
