package com.alcantara.cafe_management_server.controllers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alcantara.cafe_management_server.entities.ClientInfo;
import com.alcantara.cafe_management_server.entities.ClientStatus;

@RestController
@CrossOrigin("*")
public class NetworkController {

    private final Map<String, ClientStatus> connectedClients = new ConcurrentHashMap<>();

    @PostMapping("/register")
    public void registerClient(@RequestBody ClientInfo clientInfo) {
        connectedClients.put(clientInfo.getIpAddress(), new ClientStatus(clientInfo.getHostname(), true));
        System.out.println("Registered client: " + clientInfo.getIpAddress());
    }

    @PostMapping("/heartbeat")
    public void heartbeat(@RequestBody ClientInfo clientInfo) {
        connectedClients.computeIfPresent(clientInfo.getIpAddress(), (k, v) -> {
            v.setLastHeartbeat(System.currentTimeMillis());
            return v;
        });
    }

    @GetMapping("/clients")
    public Map<String, ClientStatus> getConnectedClients() {
        return connectedClients;
    }

    @PostMapping("/logout")
    public void logoutClient(@RequestBody ClientInfo clientInfo) {
        connectedClients.remove(clientInfo.getIpAddress());
        System.out.println("Logged out client: " + clientInfo.getIpAddress());
    }

}
