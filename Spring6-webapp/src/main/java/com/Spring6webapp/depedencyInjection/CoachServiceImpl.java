package com.Spring6webapp.depedencyInjection;

import org.springframework.stereotype.Service;

@Service("coachServiceImpl")
public class CoachServiceImpl implements Coach{
    @Override
    public String getDailyWorkout() {
        return "Workout hard no matters what!";
    }
}
