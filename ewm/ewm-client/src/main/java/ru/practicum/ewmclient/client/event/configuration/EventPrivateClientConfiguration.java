package ru.practicum.ewmclient.client.event.configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmclient.client.event.EventPrivateClient;

import java.time.format.DateTimeFormatter;

@Configuration
public class EventPrivateClientConfiguration {

    @Value("${ewm-service.url}")
    private String serverUrl;

    @Value("/users/{userId}/events")
    private String api;

    @Bean
    public EventPrivateClient eventPrivateClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + api))
                .build();
        return new EventPrivateClient(restTemplate);
    }

}