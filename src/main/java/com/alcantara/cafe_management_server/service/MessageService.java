package com.alcantara.cafe_management_server.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alcantara.cafe_management_server.dto.MessageDto;
import com.alcantara.cafe_management_server.dto.MessageResponseDto;
import com.alcantara.cafe_management_server.entity.Message;
import com.alcantara.cafe_management_server.repository.ComputerInfoRepository;
import com.alcantara.cafe_management_server.repository.MessageRepository;
import com.alcantara.cafe_management_server.utility.Utility;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ComputerInfoRepository computerInfoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ComputerInfoService.class);

    public Message sendMessage(MessageDto messageDto) {
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setComputerInfo(computerInfoRepository.findByIpAddress(messageDto.getIpAddress()));
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByIpAddress(String ipAddress) {
        return messageRepository.findByComputerInfoIpAddressOrderByCreatedOnDesc(ipAddress);
    }

    public MessageResponseDto getLatestMessageByIpAddress(String ipAddress) {
        Message message = messageRepository.findTopByComputerInfoIpAddressOrderByCreatedOnDesc(ipAddress);
        if (message == null) {
            return null;
        }

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setId(message.getId());
        messageResponseDto.setIpAddress(message.getComputerInfo().getIpAddress());
        messageResponseDto.setCreatedOn(message.getCreatedOn());
        messageResponseDto.setLastUpdatedOn(message.getLastUpdatedOn());

        LocalDateTime messageCreatedOnPastThreeMinutes = message.getCreatedOn().plusMinutes(3);
        LocalDateTime currentTime = LocalDateTime.now();

        logger.info("Current time plus 3 minutes: {}", messageCreatedOnPastThreeMinutes);
        logger.info("message created on time: {}", message.getCreatedOn());

        if (currentTime.isAfter(messageCreatedOnPastThreeMinutes)) {
            messageResponseDto.setContent(" ");
        } else {
            messageResponseDto.setContent(message.getContent());
        }

        logger.info("Latest message: {}", Utility.convertListToJson(messageResponseDto));
        return messageResponseDto;
    }

}
