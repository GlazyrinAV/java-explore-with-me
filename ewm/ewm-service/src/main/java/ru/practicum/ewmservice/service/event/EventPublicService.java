package ru.practicum.ewmservice.service.event;

import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewmcommondto.model.EventDto;

import java.util.Collection;

public interface EventPublicService {

    Collection<EventDto> findAll();

    EventDto findById(int id);

}