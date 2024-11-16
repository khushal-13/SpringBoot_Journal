package com.khushal.journalApp.controller;

import com.khushal.journalApp.api.response.WeatherResponse;
import com.khushal.journalApp.entity.User;
import com.khushal.journalApp.service.UserService;
import com.khushal.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getByUserName(@PathVariable String username) {
        try{
            return new ResponseEntity<>(userService.findByUserName(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<String> greetings() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse response = weatherService.getWeather("Mumbai");
        String greeting = "";
        if(response != null) {
            greeting = "Weather feels like " + response.getCurrent().getFeelslikeC();
        }

        return new ResponseEntity<>("Hello " + authentication.getName() + "\n" + greeting, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {

        try {
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        if(userService.deleteUser()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
