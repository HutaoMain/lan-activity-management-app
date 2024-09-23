package com.alcantara.cafe_management_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import com.alcantara.cafe_management_client.entities.ClientInfo;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
@EnableScheduling
public class CafeManagementClientApplication implements CommandLineRunner {

	@Value("${server-ip}")
	String serverIp;

	private final String serverUrl = "http://" + serverIp + ":8080";
	private RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		SpringApplication.run(CafeManagementClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Register client when the application starts
		registerClient();
	}

	private void registerClient() {
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setIpAddress(getClientIpAddress());
		clientInfo.setHostname(getClientHostname());

		restTemplate.postForObject(serverUrl + "/register", clientInfo, String.class);
		System.out.println("Client registered with server.");
	}

	@Scheduled(fixedRate = 10000)
	public void sendHeartbeat() {
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setIpAddress(getClientIpAddress());
		clientInfo.setHostname(getClientHostname());

		restTemplate.postForObject(serverUrl + "/heartbeat", clientInfo, String.class);
		System.out.println("Heartbeat sent.");
	}

	// Logout the client when the application shuts down
	@PreDestroy
	public void logoutClient() {
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setIpAddress(getClientIpAddress());
		clientInfo.setHostname(getClientHostname());

		restTemplate.postForObject(serverUrl + "/logout", clientInfo, String.class);
		System.out.println("Client logged out.");
	}

	// Helper method to get the client's IP address
	private String getClientIpAddress() {
		// Simplified version, replace with actual logic to fetch IP address
		return "192.168.1.100";
	}

	// Helper method to get the client's hostname
	private String getClientHostname() {
		try {
			return java.net.InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			return "UnknownHost";
		}
	}

}
