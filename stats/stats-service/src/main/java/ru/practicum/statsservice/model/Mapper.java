package ru.practicum.statsservice.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Component
public class Mapper {

    private final DateTimeFormatter formatter;

    public Stats fromDto(JsonNode dto) {

        Stats stats = Stats.builder()
                .app(dto.get("app").asText())
                .timeStamp(LocalDateTime.parse(dto.get("timestamp").asText(), formatter))
                .build();

        ObjectNode node = (ObjectNode ) dto;
        node.remove("app");
        node.remove("timestamp");

        stats.setAdditionalProps(node);

        return stats;
    }

}