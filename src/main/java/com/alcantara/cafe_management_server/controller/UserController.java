package com.alcantara.cafe_management_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alcantara.cafe_management_server.entity.User;
import com.alcantara.cafe_management_server.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) throws Exception {
        return userService.loginUser(user.getUsername(), user.getPassword());
    }

    @GetMapping("/list")
    public List<User> getListUserController() {
        return userService.getListUser();
    }

    @GetMapping("/{username}")
    private User getUserByEmail(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("/changePassword/{email}")
    public ResponseEntity<String> updatePassword(@PathVariable("username") String username, @RequestBody User user) {
        userService.updatePassword(username, user);
        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
    }
}
