package com.Spring6webapp.depedencyInjection;

import org.springframework.stereotype.Component;

@Component
public class FootballCoach implements Coach{
    @Override
    public String getDailyWorkout() {
        return "Run for 30 mins everyday";
    }
}
