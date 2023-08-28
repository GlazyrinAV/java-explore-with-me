package ru.practicum.statsservice.model;

import lombok.Builder;
import org.springframework.stereotype.Component;
import ru.practicum.statsclient.dto.StatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Component
public class Mapper {

    public Stats fromDto(StatsDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Stats.builder()
                .app(dto.getApp())
                .ip(dto.getIp())
                .uri(dto.getUri())
                .timeStamp(LocalDateTime.parse(dto.getTimeStamp(), formatter))
                .build();
    }

}