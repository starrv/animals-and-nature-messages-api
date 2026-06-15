package com.animalsandnature.messages;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    Message findMessageByIdAndNotificationType(String id,String notificationType);

    Message save(Message msg);

    List<Message> findMessagesByNotificationType(String notificationType);

    long count();

    Message findMessageById(ObjectId id);

    List<Message> findAll();
}
