package com.springwebapp6.spring6restmvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    //spring secuity filter chanin

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        try {
            http.csrf(AbstractHttpConfigurer::disable);
            //Must be authenticated to all URL and disable csrf
            http.authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/**")
                            .authenticated()
                    ).httpBasic(Customizer.withDefaults());

            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
