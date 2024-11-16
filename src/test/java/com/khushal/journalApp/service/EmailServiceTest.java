package com.khushal.journalApp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Test
    public void sendEmail() {
        Assertions.assertTrue(emailService.sendEmail( "i9229653@gmail.com",
                "Test",
                "Hello.!! \nDevelopers :)"));

    }
}
