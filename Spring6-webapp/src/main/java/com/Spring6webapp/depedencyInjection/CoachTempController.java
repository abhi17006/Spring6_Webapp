package com.Spring6webapp.depedencyInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CoachTempController {

    //Qualifier :avoid ambiguity when Spring finds multiple beans of the same type(Coach).
    @Qualifier("coachServiceImpl")
    @Autowired
    private Coach coach;

    public String getDailyWorkout(){
        return coach.getDailyWorkout();
    }
}
