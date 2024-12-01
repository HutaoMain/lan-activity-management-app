package com.alcantara.cafe_management_server.service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alcantara.cafe_management_server.entities.ComputerInfo;
import com.alcantara.cafe_management_server.repository.ComputerInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alcantara.cafe_management_server.dto.NetworkResponseDto;

@Service
public class ComputerInfoService {

    @Autowired
    ComputerInfoRepository computerInfoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ComputerInfoService.class);
    private static final int TIMEOUT_MS = 1000;
    private static final int THREAD_POOL_SIZE = 10;
//    private static final int PROCESS_TIMEOUT_SECONDS = 30;

    public ComputerInfo createComputerInfo(ComputerInfo computerInfo){
        return computerInfoRepository.save(computerInfo);
    }

    public NetworkResponseDto checkIpAddresses() {
        Map<String, Boolean> ipStatus = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        List<ComputerInfo> computerInfoList = computerInfoRepository.findAll();
        ArrayList<String> ipAddresses = new ArrayList<>();
        for (ComputerInfo computerInfo : computerInfoList) {
            ipAddresses.add(computerInfo.getIpAddress());
        }

        for (String ip : ipAddresses) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    boolean isReachable = isValidAndReachable(ip);
                    ipStatus.put(ip, isReachable);
                    logger.info("IP {} is {}reachable", ip, isReachable ? "" : "not ");
                } catch (Exception e) {
                    logger.error("Error checking IP {}: {}", ip, e.getMessage());
                    ipStatus.put(ip, false);
                }
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();

        return new NetworkResponseDto(ipStatus,
                "Completed checking " + ipAddresses.size() + " IP addresses");
    }

    private boolean isValidAndReachable(String ip) {
        try {
            if (!isValidIpAddress(ip)) {
                logger.warn("Invalid IP address format: {}", ip);
                return false;
            }
            return InetAddress.getByName(ip).isReachable(TIMEOUT_MS);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidIpAddress(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }
        try {
            for (String part : parts) {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

//    public record ShutdownResult(boolean success, String message) {
//    }
//
//    public ShutdownResult shutdownComputer(String targetIp, String username, String password) {
//        // Get Windows System32 directory
//        String system32Path = System.getenv("WINDIR") + "\\System32";
//
//        // Validate System32 path exists
//        Path shutdownPath = Paths.get(system32Path, "shutdown.exe");
//        if (!shutdownPath.toFile().exists()) {
//            logger.error("shutdown.exe not found in expected location: {}", shutdownPath);
//            return new ShutdownResult(false, "shutdown.exe not found in System32 directory");
//        }
//
//        try {
//            // First establish network connection
//            ProcessBuilder netUseBuilder = new ProcessBuilder(
//                    Paths.get(system32Path, "net.exe").toString(),
//                    "use",
//                    "\\\\" + targetIp,
//                    "/user:" + username,
//                    password);
//
//            // Combine error and output streams
//            netUseBuilder.redirectErrorStream(true);
//
//            Process netUseProcess = netUseBuilder.start();
//            String netUseOutput = readProcessOutput(netUseProcess);
//
//            if (!netUseProcess.waitFor(PROCESS_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
//                netUseProcess.destroyForcibly();
//                logger.error("Network connection timed out for IP: {}", targetIp);
//                return new ShutdownResult(false, "Network connection timed out");
//            }
//
//            if (netUseProcess.exitValue() != 0) {
//                logger.error("Failed to establish network connection. Output: {}", netUseOutput);
//                return new ShutdownResult(false, "Failed to establish network connection: " + netUseOutput);
//            }
//
//            // Then execute shutdown command
//            List<String> shutdownCommand = new ArrayList<>();
//            shutdownCommand.add(shutdownPath.toString());
//            shutdownCommand.add("/s"); // shutdown
//            shutdownCommand.add("/f"); // force
//            shutdownCommand.add("/m");
//            shutdownCommand.add("\\\\" + targetIp);
//            shutdownCommand.add("/t");
//            shutdownCommand.add("0"); // immediate shutdown
//
//            ProcessBuilder shutdownBuilder = new ProcessBuilder(shutdownCommand);
//            shutdownBuilder.redirectErrorStream(true);
//
//            Process shutdownProcess = shutdownBuilder.start();
//            String shutdownOutput = readProcessOutput(shutdownProcess);
//
//            if (!shutdownProcess.waitFor(PROCESS_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
//                shutdownProcess.destroyForcibly();
//                logger.error("Shutdown command timed out for IP: {}", targetIp);
//                return new ShutdownResult(false, "Shutdown command timed out");
//            }
//
//            boolean success = shutdownProcess.exitValue() == 0;
//            if (success) {
//                logger.info("Successfully executed shutdown command for IP: {}", targetIp);
//                return new ShutdownResult(true, "Shutdown command executed successfully");
//            } else {
//                logger.error("Shutdown command failed. Output: {}", shutdownOutput);
//                return new ShutdownResult(false, "Shutdown command failed: " + shutdownOutput);
//            }
//
//        } catch (IOException e) {
//            logger.error("IO error during shutdown process", e);
//            return new ShutdownResult(false, "IO error: " + e.getMessage());
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            logger.error("Process interrupted during shutdown", e);
//            return new ShutdownResult(false, "Process interrupted: " + e.getMessage());
//        } finally {
//            // Clean up network connection
//            try {
//                ProcessBuilder cleanupBuilder = new ProcessBuilder(
//                        Paths.get(system32Path, "net.exe").toString(),
//                        "use",
//                        "\\\\" + targetIp,
//                        "/delete");
//                cleanupBuilder.start().waitFor(10, TimeUnit.SECONDS);
//            } catch (Exception e) {
//                logger.warn("Failed to cleanup network connection", e);
//            }
//        }
//    }
//
//    private String readProcessOutput(Process process) throws IOException {
//        StringBuilder output = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(
//                new InputStreamReader(process.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//        }
//        return output.toString().trim();
//    }
}
