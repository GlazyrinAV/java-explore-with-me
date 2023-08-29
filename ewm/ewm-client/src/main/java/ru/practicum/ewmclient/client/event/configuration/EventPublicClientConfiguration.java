package ru.practicum.ewmclient.client.event.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmclient.client.event.EventPublicClient;
import ru.practicum.ewmclient.client.stats.StatsClient;

@Configuration
public class EventPublicClientConfiguration {

    @Value("${ewm-service.url}")
    private String serverUrl;

    @Value("/events")
    private String api;

    @Bean
    public EventPublicClient eventPublicClient(RestTemplateBuilder builder, StatsClient client) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + api))
                .build();
        return new EventPublicClient(restTemplate, client);
    }

}