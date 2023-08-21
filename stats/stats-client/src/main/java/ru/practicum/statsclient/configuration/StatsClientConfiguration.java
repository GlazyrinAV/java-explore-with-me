package ru.practicum.statsclient.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statsclient.client.StatsClient;

@Configuration
public class StatsClientConfiguration {

    @Value("${stats-server.url}")
    private String serverUrl;

    @Bean
    public StatsClient statsClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .build();
        return new StatsClient(restTemplate);
    }

}