package com.phoenix.data.repository;

import com.phoenix.data.models.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AppRepositoryTest {

    @Autowired
    AppRepository appRepository;

    @BeforeEach
    void setUp() {
    }


    @Test
    @DisplayName("create a new user with cart test")
    void whenNewUserIsCreated_thenCreateCartTest(){
        //creating a user object
        // creating a cart object

        AppUser appUser = new AppUser();

        appUser.setFirstName("John");
        appUser.setLastName("Badmus");
        appUser.setEmail("joh@gmail.com");
        appUser.setAddress("sabo, yabo");

        appRepository.save(appUser);

        assertThat(appUser.getId()).isNotNull();
        assertThat(appUser.getMyCart()).isNotNull();
        log.info("app user is created :: {}..", appUser);
    }
}