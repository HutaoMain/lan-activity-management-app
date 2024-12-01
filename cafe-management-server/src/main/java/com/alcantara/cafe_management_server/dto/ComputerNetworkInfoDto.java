package com.alcantara.cafe_management_server.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComputerNetworkInfoDto {

    private String hostName;
    private String ipAddress;
    private Boolean ipStatus;
    private Instant createdOn;
    private Instant lastUpdatedOn;

    public ComputerNetworkInfoDto(){
    }

    public ComputerNetworkInfoDto(String hostName, String ipAddress, Boolean ipStatus, Instant createdOn, Instant lastUpdatedOn) {
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        this.ipStatus = ipStatus;
        this.createdOn = createdOn;
        this.lastUpdatedOn = lastUpdatedOn;
    }
}
