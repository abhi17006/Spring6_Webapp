package com.Spring6webapp;


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


}
