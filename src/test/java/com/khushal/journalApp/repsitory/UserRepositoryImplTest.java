package com.khushal.journalApp.repsitory;

import com.khushal.journalApp.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Test
    public void test() {
        Assertions.assertNotNull(userRepositoryImpl.getUserForSA());
    }
}
