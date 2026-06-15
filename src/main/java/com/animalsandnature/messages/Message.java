package com.animalsandnature.messages;

import com.fasterxml.jackson.annotation.JsonView;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("message")
public class Message{

    @Id
    @JsonView(WithoutMailView.class)
    private String id;
    @JsonView(WithoutMailView.class)
    private String notificationType;

    @JsonView(WithMailView.class)
    private Mail mail;

    @JsonView(WithoutMailView.class)
    private String content;

    public Message(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    @Override
    public String toString(){
        return "Message: {id: "+id+" content: "+content+" notification type: "+notificationType+" mail: "+mail+"}";
    }

}
