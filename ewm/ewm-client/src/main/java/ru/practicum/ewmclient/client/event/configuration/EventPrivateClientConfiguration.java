package ru.practicum.ewmclient.client.event.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmclient.client.event.EventPrivateClient;

@Configuration
public class EventPrivateClientConfiguration {

    @Value("${ewm-service.url}")
    private String serverUrl;

    @Value("/users/{userId}/events")
    private String api;

    @Bean
    public EventPrivateClient eventPrivateClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + api))
                .build();
        return new EventPrivateClient(restTemplate);
    }

}