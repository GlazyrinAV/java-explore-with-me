package ru.practicum.ewmclient.client.event.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmclient.client.event.EventAdminClient;

@Configuration
public class EventAdminClientConfiguration {

    @Value("${ewm-service.url}")
    private String serverUrl;

    @Value("/admin/events")
    private String api;

    @Bean
    public EventAdminClient eventAdminClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + api))
                .build();
        return new EventAdminClient(restTemplate);
    }

}