package com.springresttemplate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateBuilderConfig {

    //externalize root URL
    @Value("${rest.template.rootUrl}")
    String rootUrl;

    @Bean //return bean of RestTemplateBuilder
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer){

        assert rootUrl != null;
    //configure takes the new instance and then configures with the springboot defaults
        RestTemplateBuilder builder = configurer.configure(new RestTemplateBuilder());

        //
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(rootUrl);
        return builder.uriTemplateHandler(uriBuilderFactory);
    }
}

