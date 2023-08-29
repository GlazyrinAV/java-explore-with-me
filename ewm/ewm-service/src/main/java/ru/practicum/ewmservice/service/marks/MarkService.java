package ru.practicum.ewmservice.service.marks;

import ru.practicum.ewmclient.model.MarkDto;

public interface MarkService {

    MarkDto save(int userId, int eventId, int mark);

    void remove(int userId, int eventId);

}