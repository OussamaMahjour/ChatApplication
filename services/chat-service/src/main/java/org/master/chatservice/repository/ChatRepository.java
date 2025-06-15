package org.master.chatservice.repository;

import org.master.chatservice.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat,String> {

    public Chat findByParticipants(List<String> participants);
    public List<Chat> findByParticipantsContaining(String participant);
}
