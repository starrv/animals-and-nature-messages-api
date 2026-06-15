package com.animalsandnature.messages;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@CrossOrigin
@RestController
public class MessagesController {

    private Log log = LogFactory.getLog(MessagesController.class);

    @Autowired
    MessageRepository messageRepo;

    @GetMapping(value = "/messages")
    @JsonView(WithoutMailView.class)
    @PreAuthorize("hasAuthority('SCOPE_read:messages')")
    public List<Message> messages(){
        return messageRepo.findMessagesByNotificationType("Received");
    }

    @GetMapping("/messages/{id}")
    @JsonView(WithMailView.class)
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
        Functions.print(log,Functions.INFO,"Saving message");
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
