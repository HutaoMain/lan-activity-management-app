package com.alcantara.cafe_management_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alcantara.cafe_management_server.dto.ActivityDto;
import com.alcantara.cafe_management_server.entity.Activity;
import com.alcantara.cafe_management_server.service.ActivityService;

@RestController
@RequestMapping("/activity")
@CrossOrigin("*")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @GetMapping("/list")
    public ResponseEntity<List<Activity>> getActivities(@RequestParam(required = false) Long computerInfoId) {
        List<Activity> activities;
        if (computerInfoId != null) {
            activities = activityService.getActivitiesByComputerInfoId(computerInfoId);
        } else {
            activities = activityService.getAllActivitiesSortedByCreatedOn();
        }
        return ResponseEntity.ok(activities);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveActivities(@RequestBody List<ActivityDto> activities) {
        activityService.saveActivities(activities);
        return ResponseEntity.ok().build();
    }

}
