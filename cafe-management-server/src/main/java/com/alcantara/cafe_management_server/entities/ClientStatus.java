package com.alcantara.cafe_management_server.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientStatus {
    private String hostname;
    private boolean isLoggedIn;
    private long lastHeartbeat;

    public ClientStatus(String hostname, boolean isLoggedIn) {
        this.hostname = hostname;
        this.isLoggedIn = isLoggedIn;
        this.lastHeartbeat = System.currentTimeMillis();
    }
}
