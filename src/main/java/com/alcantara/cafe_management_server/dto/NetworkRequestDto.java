package com.alcantara.cafe_management_server.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetworkRequestDto {
    private List<String> ipAddresses;
}
