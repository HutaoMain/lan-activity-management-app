package com.alcantara.cafe_management_server.service;

import com.alcantara.cafe_management_server.entity.Activity;
import com.alcantara.cafe_management_server.entity.ComputerInfo;
import com.alcantara.cafe_management_server.repository.ActivityRepository;
import com.alcantara.cafe_management_server.repository.ComputerInfoRepository;
import com.alcantara.cafe_management_server.repository.User32;
import com.alcantara.cafe_management_server.utility.ComputerInfoUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.util.HashSet;
import java.util.List;

@Service
public class ActivityService {

    private final Logger logger = LoggerFactory.getLogger(ActivityService.class);
    HashSet<String> activeWindowsSet = new HashSet<>();

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ComputerInfoRepository computerInfoRepository;

    @PostConstruct
    public void execute() {
        new Thread(() -> {
            long lastExecutionTime = 0;
            long delay = 3000;

            while (true) {
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastExecutionTime >= delay) {
                    try {
                        lastExecutionTime = currentTime;
                        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
                        if (hwnd != null) {
                            char[] windowText = new char[512];
                            int textLength = User32.INSTANCE.GetWindowText(hwnd, windowText, 512);

                            if (textLength > 0) {
                                String activeWindow = new String(windowText, 0, textLength);
                                if (activeWindowsSet.add(activeWindow)) {
                                    logger.info("Added new active window: {}", activeWindow);
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Error occurred while monitoring active window: {}", e.getMessage());
                    }
                }
            }
        }).start();
    }

    @PreDestroy
    public void onShutdown() {
        logger.info("Application is shutting down. Saving active windows to database...");
        try {
            saveToDatabase();
        } finally {
            activeWindowsSet.clear();
            logger.info("HashSet cleaned up after saving to the database.");
        }
    }

    private void saveToDatabase() {
        String currentIpAddress = ComputerInfoUtils.getCurrentIpAddress();
        ComputerInfo computerInfo = computerInfoRepository.findByIpAddress(currentIpAddress);
        try {
            logger.info("Saving active windows to the database: {}", activeWindowsSet);
            for (String activeWindow : activeWindowsSet) {
                Activity activity = new Activity();
                activity.setActiveWindow(activeWindow);
                activity.setActiveDateTime(java.time.LocalDateTime.now());
                activity.setComputerInfo(computerInfo);

                activityRepository.save(activity);
            }
            logger.info("All active windows saved to the database.");
        } catch (Exception e) {
            logger.error("Error saving active windows to the database: {}", e.getMessage());
        }
    }

    public List<Activity> getAllActivitiesSortedByCreatedOn() {
        return activityRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

    public List<Activity> getActivitiesByComputerInfoId(Long computerInfoId) {
        return activityRepository.findByComputerInfoId(computerInfoId, Sort.by(Sort.Direction.DESC, "createdOn"));
    }

}
