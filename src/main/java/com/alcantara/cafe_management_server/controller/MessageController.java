package com.alcantara.cafe_management_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alcantara.cafe_management_server.dto.MessageDto;
import com.alcantara.cafe_management_server.dto.MessageResponseDto;
import com.alcantara.cafe_management_server.entity.Message;
import com.alcantara.cafe_management_server.service.MessageService;

@RestController
@RequestMapping("/messages")
@CrossOrigin("*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDto messageDto) {
        Message message = messageService.sendMessage(messageDto);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/latest/{ipAddress}")
    public ResponseEntity<MessageResponseDto> getLatestMessage(@PathVariable String ipAddress) {
        MessageResponseDto message = messageService.getLatestMessageByIpAddress(ipAddress);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Message>> getAllMessages(@RequestParam String ipAddress) {
        List<Message> messages = messageService.getMessagesByIpAddress(ipAddress);
        return ResponseEntity.ok(messages);
    }

}
