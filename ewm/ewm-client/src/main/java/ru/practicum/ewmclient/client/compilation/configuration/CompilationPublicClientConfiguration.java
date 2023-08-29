package ru.practicum.ewmclient.client.compilation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmclient.client.compilation.CompilationPublicClient;

@Configuration
public class CompilationPublicClientConfiguration {

    @Value("${ewm-service.url}")
    private String serverUrl;

    @Value("/compilations")
    private String api;

    @Bean
    public CompilationPublicClient compilationPublicClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + api))
                .build();
        return new CompilationPublicClient(restTemplate);
    }

}