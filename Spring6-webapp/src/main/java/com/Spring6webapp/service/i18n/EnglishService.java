package com.Spring6webapp.service.i18n;

import com.Spring6webapp.service.GreetingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
//
@Profile({"EN", "default"}) //make it default always available if there is no @ActiveProfiles available
@Service("i18NService")
public class EnglishService implements GreetingService {
    @Override
    public String sayGreetings() {
        return "Hello World - EN";
    }
}
