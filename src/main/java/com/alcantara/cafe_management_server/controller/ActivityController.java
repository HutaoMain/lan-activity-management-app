package com.alcantara.cafe_management_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alcantara.cafe_management_server.entity.Activity;
import com.alcantara.cafe_management_server.service.ActivityService;

@RestController
@RequestMapping("/activity")
@CrossOrigin("*")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @GetMapping("/list")
    ResponseEntity<List<Activity>> getAllActivitiesSortedByCreatedOn() {
        List<Activity> activities = activityService.getAllActivitiesSortedByCreatedOn();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/list/{computerInfoId}")
    ResponseEntity<List<Activity>> getActivitiesByComputerInfoId(@PathVariable Long computerInfoId) {
        List<Activity> activities = activityService.getActivitiesByComputerInfoId(computerInfoId);
        return ResponseEntity.ok(activities);
    }

}
