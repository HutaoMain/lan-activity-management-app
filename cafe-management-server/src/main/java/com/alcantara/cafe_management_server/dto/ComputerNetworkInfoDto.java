package com.alcantara.cafe_management_server.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComputerNetworkInfoDto {

    private Long id;
    private String ipAddress;
    private String hostName;
    private Boolean ipStatus;
    private Instant createdOn;
    private Instant lastUpdatedOn;

    public ComputerNetworkInfoDto(){
    }

    public ComputerNetworkInfoDto(Long id, String ipAddress, String hostName, Boolean ipStatus, Instant createdOn, Instant lastUpdatedOn) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.hostName = hostName;
        this.ipStatus = ipStatus;
        this.createdOn = createdOn;
        this.lastUpdatedOn = lastUpdatedOn;
    }
}
