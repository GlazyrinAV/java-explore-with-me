package ru.practicum.ewmservice.service.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.model.mapper.ParticipationRequestsMapper;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;

@Service
@RequiredArgsConstructor
public class ParticipationRequestsPrivateServiceImpl implements ParticipationRequestsPrivateService {

    private final ParticipationRequestsRepository repository;

    private final ParticipationRequestsMapper mapper;

}