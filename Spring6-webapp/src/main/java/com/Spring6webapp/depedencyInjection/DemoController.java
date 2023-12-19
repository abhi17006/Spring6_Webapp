package com.Spring6webapp.depedencyInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    //define private field for dependecy
    private Coach mycCoach;

    //constructor dependency injection

    @Autowired
    public DemoController(Coach thCoach){
        mycCoach = thCoach;
    }

    @GetMapping("dailyworkout")
    public String getDailyWorkout(){
        return mycCoach.getDailyWorkout();
    }
}
