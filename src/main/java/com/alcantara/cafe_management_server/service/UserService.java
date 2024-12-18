package com.alcantara.cafe_management_server.service;

import com.alcantara.cafe_management_server.entity.User;
import com.alcantara.cafe_management_server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public User registerUser(User user) {
        User getUserByUsername = userRepository.findByUsername(user.getUsername());

        if(getUserByUsername != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.info("User not found");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User not found");
        }
        if (!encoder.matches(password, user.getPassword())) {
            logger.info("Invalid password");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid password");
        }
        return user;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getListUser() {
        return userRepository.findAll();
    }

    public void updatePassword(String username, User user) {
        User setUser = userRepository.findByUsername(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String encodedPassword = encoder.encode(user.getPassword());
        setUser.setPassword(encodedPassword);
        userRepository.save(setUser);
    }
}
