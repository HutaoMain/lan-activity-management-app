package com.alcantara.cafe_management_server.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {

    @NotNull(message = "ComputerInfo cannot be null")
    @NotEmpty(message = "Content cannot be empty")
    private String content;

    @NotNull(message = "ComputerInfo cannot be null")
    @NotEmpty(message = "IP address cannot be empty")
    private String ipAddress;
}
