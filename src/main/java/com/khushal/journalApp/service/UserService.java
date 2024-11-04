package com.khushal.journalApp.service;


import com.khushal.journalApp.entity.User;
import com.khushal.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.List;

@Service
public class UserService {

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

}
