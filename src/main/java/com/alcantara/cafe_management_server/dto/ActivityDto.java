package com.alcantara.cafe_management_server.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityDto {
    private String activeWindow;
    private LocalDateTime activeDateTime;
    private String ipAddress;
}
