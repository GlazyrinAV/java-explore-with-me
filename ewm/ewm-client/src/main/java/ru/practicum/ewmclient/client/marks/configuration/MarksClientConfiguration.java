package ru.practicum.ewmclient.client.marks.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmclient.client.marks.MarksClient;

@Configuration
public class MarksClientConfiguration {

    @Value("${ewm-service.url}")
    private String serverUrl;

    @Value("/users/{userId}/events/{eventId}/like")
    private String api;

    @Bean
    public MarksClient marksClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + api))
                .build();
        return new MarksClient(restTemplate);
    }

}