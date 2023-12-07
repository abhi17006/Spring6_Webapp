package com.Spring6webapp;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempRestController {

    @GetMapping("/")
    public String sayHello(){
        return "hello World!";
    }

    @GetMapping("/workout")
    public String getWorkout(){
        return "Run a Hard 5k!";
    }

    //inject custom Application Properties
    @Value("${coach.name}")
    private String coachName;
    @Value("${team.name}")
    private String teamName;

    //generate new endpoints
    @GetMapping("/teaminfo")
    public String getTeamInfo(){
        return coachName + ", Team Name: "+ teamName;
    }

}
