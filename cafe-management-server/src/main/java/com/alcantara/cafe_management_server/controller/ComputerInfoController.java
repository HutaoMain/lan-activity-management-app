package com.alcantara.cafe_management_server.controller;

import com.alcantara.cafe_management_server.entity.ComputerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alcantara.cafe_management_server.dto.ComputerNetworkInfoDto;
import com.alcantara.cafe_management_server.service.ComputerInfoService;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class ComputerInfoController {

    @Autowired
    ComputerInfoService computerInfoService;

    @PostMapping("/create")
    private ResponseEntity<ComputerInfo> createComputerInfo(@RequestBody ComputerInfo computerInfo){
        return ResponseEntity.ok(computerInfoService.createComputerInfo(computerInfo));
    }

    @GetMapping("/check")
    public ResponseEntity<List<ComputerNetworkInfoDto>> checkIpAddresses() {
        List<ComputerNetworkInfoDto> response = computerInfoService.checkIpAddresses();
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/shutdown/{ip}")
//    public ResponseEntity<?> shutdownComputer(
//            @PathVariable String ip,
//            @RequestParam String username,
//            @RequestParam(required = false) String password) {
//
//        ComputerInfoService.ShutdownResult result = computerInfoService.shutdownComputer(ip, username, password);
//
//        if (result.success()) {
//            return ResponseEntity.ok(result.message());
//        } else {
//            return ResponseEntity.internalServerError().body(result.message());
//        }
//    }
}
