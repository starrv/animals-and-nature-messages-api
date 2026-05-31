package com.animalsandnature.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.web.server.authentication.ServerBearerTokenAuthenticationConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.http.HttpRequest;
import java.time.LocalTime;
import java.util.*;

@CrossOrigin
@RestController
public class MessagesController {

    @Autowired
    MessageRepository messageRepo;
    HashMap<Long,Message> messages=new HashMap<Long, Message>();


    @GetMapping(value = "/messages")
    @PreAuthorize("hasAuthority('SCOPE_read:messages')")
    public List<Message> messages(){
        System.out.println(LocalTime.now()+": Getting messages");
        return messageRepo.findMessagesByNotificationType("Received");
    }

    @GetMapping("/messages/{id}")
    @PreAuthorize("hasAuthority('SCOPE_read:messages')")
    public Message messageById(@PathVariable String id) throws RuntimeException{
        Message msg=messageRepo.findMessageByIdAndNotificationType(id,"Received");
        if(msg!=null){
            return msg;
        }
        throw new MessageNotFoundException("Message does not exist");
    }

    @PostMapping("/messages")
    @PreAuthorize("hasAuthority('SCOPE_write:messages')")
    @ResponseStatus(HttpStatus.CREATED)
    public Message addMessage(@RequestBody Message msg){
        System.out.println("Saving message");
        return messageRepo.save(msg);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ErrorMessage> errorHandleNotFound(Exception e){
        ResponseEntity<ErrorMessage> resp=new ResponseEntity<ErrorMessage>(new ErrorMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        return resp;
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ErrorMessage> errorHandleMessageNotFound(Exception e){
        ResponseEntity<ErrorMessage> resp=new ResponseEntity<ErrorMessage>(new ErrorMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        return resp;
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ErrorMessage> errorHandleUnauthorized(Exception e){
        ResponseEntity<ErrorMessage> resp=new ResponseEntity<ErrorMessage>(new ErrorMessage(e.getMessage()),HttpStatus.UNAUTHORIZED);
        return resp;
    }


}
