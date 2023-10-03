package ru.practicum.ewmclient.client.user.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmclient.client.user.UserAdminClient;

@Configuration
public class UserAdminClientConfiguration {

    @Value("${ewm-service.url}")
    private String serverUrl;

    @Value("/admin/users")
    private String api;

    @Bean
    public UserAdminClient userAdminClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + api))
                .basicAuthentication("admin", "admin")
                .build();
        return new UserAdminClient(restTemplate);
    }

}