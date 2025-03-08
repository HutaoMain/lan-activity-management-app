package com.alcantara.cafe_management_server.service;

import com.alcantara.cafe_management_server.dto.ActivityDto;
import com.alcantara.cafe_management_server.entity.Activity;
import com.alcantara.cafe_management_server.entity.ComputerInfo;
import com.alcantara.cafe_management_server.repository.ActivityRepository;
import com.alcantara.cafe_management_server.repository.ComputerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ActivityService {

    HashSet<String> activeWindowsSet = new HashSet<>();

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ComputerInfoRepository computerInfoRepository;

    public List<ActivityDto> getAllActivitiesSortedByCreatedOn() {
        List<Activity> activities = activityRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));

        List<ActivityDto> activityDtos = new ArrayList<>();

        for (Activity activity : activities) {
            ActivityDto activityDto = new ActivityDto();
            activityDto.setId(activity.getId());
            activityDto.setActiveDateTime(activity.getActiveDateTime());
            activityDto.setActiveWindow(activity.getActiveWindow());
            activityDto.setIpAddress(activity.getComputerInfo().getIpAddress());
            activityDto.setCreatedOn(activity.getCreatedOn());
            activityDtos.add(activityDto);
        }

        return activityDtos;
    }

    public List<ActivityDto> getActivitiesByComputerInfoId(Long computerInfoId) {
        List<Activity> activities = activityRepository.findByComputerInfoId(computerInfoId,
                Sort.by(Sort.Direction.DESC, "createdOn"));

        List<ActivityDto> activityDtos = new ArrayList<>();

        for (Activity activity : activities) {
            ActivityDto activityDto = new ActivityDto();
            activityDto.setId(activity.getId());
            activityDto.setActiveDateTime(activity.getActiveDateTime());
            activityDto.setActiveWindow(activity.getActiveWindow());
            activityDto.setIpAddress(activity.getComputerInfo().getIpAddress());
            activityDto.setCreatedOn(activity.getCreatedOn());
            activityDtos.add(activityDto);
        }

        return activityDtos;
    }

    public void saveActivities(List<ActivityDto> activities) {
        for (ActivityDto activityDto : activities) {
            ComputerInfo computerInfo = computerInfoRepository.findByIpAddress(activityDto.getIpAddress());
            if (computerInfo == null) {
                computerInfo = new ComputerInfo();
                computerInfo.setIpAddress(activityDto.getIpAddress());
                computerInfoRepository.save(computerInfo);
            }

            Activity activity = new Activity();
            activity.setActiveWindow(activityDto.getActiveWindow());
            activity.setActiveDateTime(activityDto.getActiveDateTime());
            activity.setComputerInfo(computerInfo);

            activityRepository.save(activity);
        }
    }
}
