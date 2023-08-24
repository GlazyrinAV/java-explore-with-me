package ru.practicum.ewmservice.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmcommondto.model.ParticipationRequestDto;
import ru.practicum.ewmservice.model.ParticipationRequest;

@Component
@RequiredArgsConstructor
public class ParticipationRequestsMapper {

    private final UserMapper userMapper;

    private final EventMapper eventMapper;

    public ParticipationRequest fromDto(ParticipationRequestDto dto) {
        return ParticipationRequest.builder()
                .event(eventMapper.fromDto(dto.getEvent()))
                .requester(userMapper.fromDto(dto.getRequester()))
                .build();
    }

    public ParticipationRequestDto toDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(eventMapper.toDto(request.getEvent()))
                .requester(userMapper.toDto(request.getRequester()))
                .created(request.getCreated())
                .status(request.getStatus().name())
                .build();
    }

}