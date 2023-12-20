package com.Spring6webapp.depedencyInjection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoachTempControllerTest {

    @Autowired
    CoachTempController coachTempController;

    @Test
    void getWorkout(){
        System.out.println(coachTempController.getDailyWorkout());
    }
}