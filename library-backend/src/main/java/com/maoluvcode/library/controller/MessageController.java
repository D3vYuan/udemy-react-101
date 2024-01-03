package com.maoluvcode.library.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maoluvcode.library.entity.Message;
import com.maoluvcode.library.service.MessageService;
import com.maoluvcode.library.utils.ExtractJWT;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private MessageService messageService;
    private static final String SUB_FIELD = "\"sub\"";

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader("Authorization") String token, @RequestBody Message messageRequest) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, SUB_FIELD);
        messageService.postMessage(messageRequest, userEmail);
    }

}
