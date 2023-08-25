package ru.practicum.ewmclient.client.requests.configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmclient.client.requests.ParticipationRequestsPrivateClient;

import java.time.format.DateTimeFormatter;

@Configuration
public class ParticipationRequestsPrivateClientConfiguration {

    @Value("${ewm-service.url}")
    private String serverUrl;

    @Value("/users/{usersId}/requests")
    private String api;

    @Bean
    public ParticipationRequestsPrivateClient participationRequestsPrivateClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + api))
                .build();
        return new ParticipationRequestsPrivateClient(restTemplate);
    }

}