package ru.practicum.ewmclient.client.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmcommondto.model.EventRequestStatusUpdateRequest;

public class EventPrivateClient extends BaseClient {

    public EventPrivateClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(EventDto dto, int userId) {
        return null;
    }

    public ResponseEntity<Object> findAllByUserId(int userId) {
        return null;
    }

    public ResponseEntity<Object> findById(int userId, int eventId) {
        return null;
    }

    public ResponseEntity<Object> findRequests(int userId, int eventId) {
        return null;
    }

    public ResponseEntity<Object> update(EventDto dto, int userId, int eventId) {
        return null;
    }

    public ResponseEntity<Object> updateRequests(EventRequestStatusUpdateRequest dto, int userId, int eventId) {
        return null;
    }

}