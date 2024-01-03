package com.maoluvcode.library.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.maoluvcode.library.dao.MessageRepository;
import com.maoluvcode.library.entity.Message;

@Service
@Transactional
public class MessageService {
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

}
