package ru.practicum.ewmservice.service.event;

import ru.practicum.ewmclient.model.EventDto;
import ru.practicum.ewmclient.model.UpdateEventAdminRequest;

import java.util.Collection;

public interface EventAdminService {

    Collection<EventDto> findAll(int from,
                                 int size,
                                 Collection<Integer> users,
                                 Collection<String> states,
                                 Collection<Integer> categories,
                                 String rangeStart,
                                 String rangeEnd);

    EventDto update(UpdateEventAdminRequest dto, int id);

}