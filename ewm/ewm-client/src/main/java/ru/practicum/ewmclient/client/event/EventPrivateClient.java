package ru.practicum.ewmclient.client.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmcommondto.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewmcommondto.model.NewEventDto;
import ru.practicum.ewmcommondto.model.UpdateEventUserRequest;

import java.util.Map;

public class EventPrivateClient extends BaseClient {

    public EventPrivateClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(NewEventDto dto, int userId) {
        Map<String, Object> parameters = Map.of(
                "userId", userId
        );
        return post("", parameters, dto);
    }

    public ResponseEntity<Object> findAllByUserId(int userId, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "from", from,
                "size", size
        );
        return get("?from={from}&size={size}", parameters);
    }

    public ResponseEntity<Object> findById(int userId, int eventId) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "eventId", eventId
        );
        return get("/{eventId}", parameters);
    }

    public ResponseEntity<Object> findRequests(int userId, int eventId) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "eventId", eventId
        );
        return get("/{eventId}/requests", parameters);
    }

    public ResponseEntity<Object> update(UpdateEventUserRequest dto, int userId, int eventId) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "eventId", eventId
        );
        return patch("/{eventId}", parameters, dto);
    }

    public ResponseEntity<Object> updateRequests(EventRequestStatusUpdateRequest dto, int userId, int eventId) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "eventId", eventId
        );
        return patch("/{eventId}/requests", parameters, dto);
    }

}