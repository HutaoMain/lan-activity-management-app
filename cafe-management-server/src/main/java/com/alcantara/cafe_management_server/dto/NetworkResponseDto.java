package com.alcantara.cafe_management_server.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetworkResponseDto {

    private Map<String, Boolean> ipStatus;
    private String message;

    public NetworkResponseDto(Map<String, Boolean> ipStatus, String message) {
        this.ipStatus = ipStatus;
        this.message = message;
    }
}
