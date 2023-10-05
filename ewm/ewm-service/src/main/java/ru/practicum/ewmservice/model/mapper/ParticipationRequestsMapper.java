package ru.practicum.ewmservice.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmclient.model.request.ParticipationRequestDto;
import ru.practicum.ewmservice.model.ParticipationRequest;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ParticipationRequestsMapper {

    private final DateTimeFormatter formatter;

    public ParticipationRequestDto toDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .created(request.getCreated().format(formatter))
                .status(request.getStatus().name())
                .build();
    }

}