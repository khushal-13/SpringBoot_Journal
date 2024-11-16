package com.khushal.journalApp.service;


import com.khushal.journalApp.entity.User;
import com.khushal.journalApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        return userRepository.save(user);
    }

    public User findByUserName(String username) {

//        logger.trace("Trace"); // Special condition      lowest priority 1
//        logger.debug("Debug"); // Special condition                      2
//        logger.info("Info");   //                                        3
//        logger.warn("Warn");   //                                        4
//        logger.error("Error"); //                     highest priority   5

        return userRepository.findByUserName(username);
    }

    public boolean deleteUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getName() != null) {
            userRepository.deleteByUserName(authentication.getName());
            return true;
        }
        return false;
    }

    public User updateUser(User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User oldUser = userRepository.findByUserName(username);

        oldUser.setUserName(user.getUserName());
        oldUser.setPassword(user.getPassword());

        saveNewUser(oldUser);

        return oldUser;
    }

    public User saveNewAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        return userRepository.save(user);
    }
}
