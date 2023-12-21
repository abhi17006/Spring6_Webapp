package com.Spring6webapp.service.i18n;

import com.Spring6webapp.service.GreetingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("ES")
@Service("i18NService")
public class SpanishService implements GreetingService {
    @Override
    public String sayGreetings() {
        return "Hola ! -ES";
    }
}
