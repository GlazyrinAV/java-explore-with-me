package ru.practicum.ewmservice.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmcommondto.model.ParticipationRequestDto;
import ru.practicum.ewmservice.model.ParticipationRequest;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ParticipationRequestsMapper {

    public ParticipationRequestDto toDto(ParticipationRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .created(request.getCreated().format(formatter))
                .status(request.getStatus().name())
                .build();
    }

}