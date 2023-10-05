package ru.practicum.ewmservice.model.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmclient.model.mark.MarkDto;
import ru.practicum.ewmservice.model.Mark;

@Component
public class MarkMapper {

    public MarkDto toDto(Mark mark) {
        return MarkDto.builder()
                .userId(mark.getUser().getId())
                .eventId(mark.getEvent().getId())
                .mark(mark.getMark())
                .build();
    }

}