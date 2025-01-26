package com.alcantara.cafe_management_server.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponseDto {

    private Long id;

    private String ipAddress;

    private String content;

    private LocalDateTime createdOn;

    private LocalDateTime lastUpdatedOn;

}
