package com.animalsandnature.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
public class MessagesController {

    @Autowired
    MessageRepository messageRepo;


    @GetMapping(value = "/messages")
    public List<Message> messages(){
        System.out.println("Getting messages: "+messageRepo.findById("661eccf123a9045576583542"));
        return messageRepo.findMessagesByNotificationType("Received");
    }

    @GetMapping("/messages/{id}")
    public Message messageById(@PathVariable String id) throws RuntimeException{
        Message msg=messageRepo.findMessageByIdAndNotificationType(id,"Received");
        if(msg!=null){
            return msg;
        }
        throw new MessageNotFoundException("Message does not exist");
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public Message addMessage(@RequestBody Message msg){
        System.out.println("Saving message");
        return messageRepo.save(msg);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> errorHandleNotFound(Exception e){
        ResponseEntity<ErrorMessage> resp=new ResponseEntity<ErrorMessage>(new ErrorMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        return resp;
    }



}
