package ru.practicum.ewmservice.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmcommondto.model.ParticipationRequestDto;
import ru.practicum.ewmservice.model.ParticipationRequest;

@Component
@RequiredArgsConstructor
public class ParticipationRequestsMapper {

    public ParticipationRequestDto toDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .created(request.getCreated())
                .status(request.getStatus().name())
                .build();
    }

}