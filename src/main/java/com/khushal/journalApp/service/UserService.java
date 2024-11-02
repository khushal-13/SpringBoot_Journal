package com.khushal.journalApp.service;


import com.khushal.journalApp.entity.JournalEntry;
import com.khushal.journalApp.entity.User;
import com.khushal.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public boolean deleteUser(String username) {
        if(userRepository.findByUserName(username)!=null) {
            userRepository.deleteByUserName(username);
            return true;
        }
        return false;
    }

    public User updateUser(String username, User user) {

        User oldUser = userRepository.findByUserName(username);

        oldUser.setUserName(user.getUserName());
        oldUser.setPassword(user.getPassword());

        userRepository.save(oldUser);

        return oldUser;
    }

}
