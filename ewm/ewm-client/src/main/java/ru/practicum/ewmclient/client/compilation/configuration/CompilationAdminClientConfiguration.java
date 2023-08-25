package ru.practicum.ewmclient.client.compilation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmclient.client.compilation.CompilationAdminClient;

@Configuration
public class CompilationAdminClientConfiguration {

    @Value("${ewm-service.url}")
    private String serverUrl;

    @Value("/admin/compilations")
    private String api;

    @Bean
    public CompilationAdminClient compilationAdminClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + api))
                .build();
        return new CompilationAdminClient(restTemplate);
    }

}