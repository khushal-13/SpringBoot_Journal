package com.khushal.journalApp.controller;

import com.khushal.journalApp.entity.User;
import com.khushal.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try{
            System.out.println(user);
            return new ResponseEntity<>(userService.saveNewUser(user), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error creating User : {}", user.getUserName());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
