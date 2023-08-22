package ru.practicum.statsservice.model;

import lombok.Builder;
import org.springframework.stereotype.Component;
import ru.practicum.statscommondto.StatsDto;

@Builder
@Component
public class Mapper {

    public Stats fromDto(StatsDto dto) {
        return Stats.builder()
                .app(dto.getApp())
                .ip(dto.getIp())
                .uri(dto.getUri())
                .timeStamp(dto.getTimeStamp())
                .build();
    }

}