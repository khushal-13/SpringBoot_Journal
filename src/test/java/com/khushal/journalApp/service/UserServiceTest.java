package com.khushal.journalApp.service;

import com.khushal.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest // So that the SpringContext also starts
public class UserServiceTest {


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Disabled
    @Test
    public void findUser() {

        assertNotNull(userRepository.findByUserName("ram"));
    }

    @ValueSource(strings = {
            "ram",
            "jogi"
    })
    @ParameterizedTest
    public void findByUserName(String name) {
        assertNotNull(userRepository.findByUserName(name));
    }

    @Test
    public void getUsers() {
        assertNotNull(userService.getAll());
    }

//    @BeforeAll  -> Before executing all the test
//    @BeforeEach -> Before executing each test
//
//    @AfterAll   -> After executing all the test
//    @AfterEach  -> After executing each the test

}
