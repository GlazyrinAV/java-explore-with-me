package ru.practicum.ewmclient.client.stats.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmclient.client.stats.StatsClient;

@Configuration
public class StatsClientConfiguration {

    @Value("${stats-client.url}")
    private String serverUrl;

    @Value("/hit")
    private String api;

    @Bean
    public StatsClient statsClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + api))
                .build();
        return new StatsClient(restTemplate);
    }

}