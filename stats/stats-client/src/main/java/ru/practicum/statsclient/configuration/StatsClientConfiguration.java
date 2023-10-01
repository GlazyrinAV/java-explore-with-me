package ru.practicum.statsclient.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statsclient.client.StatsClient;

@Configuration
public class StatsClientConfiguration {

    @Value("${stats-service.url}")
    private String serverUrl;

    @Bean
    public StatsClient statsClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .build();
        return new StatsClient(restTemplate);
    }

}