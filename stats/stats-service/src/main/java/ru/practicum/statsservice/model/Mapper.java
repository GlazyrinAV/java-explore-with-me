package ru.practicum.statsservice.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Component
public class Mapper {

    private final DateTimeFormatter formatter;

    public Stats fromDto(JsonNode dto) {
        return Stats.builder()
                .timeStamp(LocalDateTime.now())
                .additionalProps(dto)
                .build();
    }

}