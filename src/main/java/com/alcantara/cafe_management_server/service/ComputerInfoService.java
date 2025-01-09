package com.alcantara.cafe_management_server.service;

import com.alcantara.cafe_management_server.entity.ComputerInfo;
import com.alcantara.cafe_management_server.repository.ComputerInfoRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.alcantara.cafe_management_server.utility.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alcantara.cafe_management_server.dto.ComputerNetworkInfoDto;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ComputerInfoService {

    @Autowired
    ComputerInfoRepository computerInfoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ComputerInfoService.class);
    private static final int TIMEOUT_MS = 1000;

    public ComputerInfo createComputerInfo(ComputerInfo computerInfo) {
        return computerInfoRepository.save(computerInfo);
    }

    public List<ComputerNetworkInfoDto> checkIpAddresses() {
        List<ComputerInfo> computerInfoList = computerInfoRepository.findAll();
        ArrayList<ComputerNetworkInfoDto> computerNetworkInfoDtoArrayList = new ArrayList<>();

        for (ComputerInfo computerInfo : computerInfoList) {
            ComputerNetworkInfoDto computerNetworkInfoDto = new ComputerNetworkInfoDto();
            try {
                boolean isReachable = isValidAndReachable(computerInfo.getIpAddress());
                computerNetworkInfoDto.setId(computerInfo.getId());
                computerNetworkInfoDto.setHostName(computerInfo.getHostName());
                computerNetworkInfoDto.setIpAddress(computerInfo.getIpAddress());
                computerNetworkInfoDto.setCreatedOn(computerInfo.getCreatedOn());
                computerNetworkInfoDto.setLastUpdatedOn(computerInfo.getLastUpdatedOn());
                computerNetworkInfoDto.setIpStatus(isReachable);
                computerNetworkInfoDtoArrayList.add(computerNetworkInfoDto);
            } catch (Exception e) {
                logger.error("Error checking IP {}: {}", computerInfo.getIpAddress(), e.getMessage());
                computerNetworkInfoDto.setIpStatus(false);
            }
        }

        return computerNetworkInfoDtoArrayList;
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

    public void deleteComputerInfo(Long id) {
        logger.info("delete id: {}", id);
        computerInfoRepository.deleteById(id);
    }

    public ComputerInfo updateComputerInfo(Long id, ComputerInfo computerInfo) {
        ComputerInfo info = computerInfoRepository.findById(id).orElse(null);

        if (info == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Computer info is null");
        }

        if (StringUtils.isNotBlank(computerInfo.getIpAddress())) {
            info.setIpAddress(computerInfo.getIpAddress());
        }

        if (StringUtils.isNotBlank(computerInfo.getHostName())) {
            info.setHostName(computerInfo.getHostName());
        }

        logger.info("updateComputerInfo: {}", Utility.convertListToJson(info));
        return computerInfoRepository.save(info);
    }

    public String logoutWindowsUser() {
        try {
            // Prepare the command to log off
            String[] command = { "cmd.exe", "/c", "logoff" };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Read the output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                    logger.info("Logout command output: {}", line);
                }

                int exitValue = process.waitFor();
                logger.info("Logout process exit value: {}", exitValue);

                if (exitValue == 0) {
                    logger.info("Successfully logged out user");
                    return "Successfully logged out user.";
                } else {
                    logger.error("Failed to log out user. Output: {}", output.toString());
                    return "Failed to log out user. Check logs for details.";
                }
            }
        } catch (Exception e) {
            logger.error("Error logging out user: {}", e.getMessage(), e);
            return "Error logging out user: " + e.getMessage();
        }
    }

    public List<ComputerInfo> getAllComputerInfo() {
        return computerInfoRepository.findAll();
    }
}
