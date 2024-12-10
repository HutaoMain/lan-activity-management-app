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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComputerInfo(@PathVariable Long id){
        computerInfoService.deleteComputerInfo(id);
        return ResponseEntity.ok("Successfully deleted" + id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ComputerInfo> updateComputerInfo(@PathVariable Long id, @RequestBody ComputerInfo computerInfo){
        ComputerInfo info = computerInfoService.updateComputerInfo(id, computerInfo);
        return ResponseEntity.ok(info);
    }

    @PostMapping("/logout-windows")
    public ResponseEntity<String> logoutWindows() {
        String result = computerInfoService.logoutWindowsUser();
        return ResponseEntity.ok(result);
    }
}
