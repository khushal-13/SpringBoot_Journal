package com.khushal.journalApp.controller;

import com.khushal.journalApp.entity.User;
import com.khushal.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/get-users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {

        try{
            return new ResponseEntity<>(userService.saveNewAdmin(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
