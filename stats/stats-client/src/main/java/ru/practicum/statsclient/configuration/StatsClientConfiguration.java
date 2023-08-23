package ru.practicum.statsclient.configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statsclient.client.StatsClient;

import java.time.format.DateTimeFormatter;

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

    @Value("yyyy-MM-dd HH:mm:ss")
    private String dateTimeFormat;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateTimeFormat);
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
            builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }

}