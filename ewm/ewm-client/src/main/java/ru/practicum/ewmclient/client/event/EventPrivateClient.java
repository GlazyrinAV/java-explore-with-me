package ru.practicum.ewmclient.client.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmclient.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewmclient.model.NewEventDto;
import ru.practicum.ewmclient.model.UpdateEventUserRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EventPrivateClient extends BaseClient {

    public EventPrivateClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(NewEventDto dto, int userId, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "userId", userId
        );
        return post("", parameters, dto, request);
    }

    public ResponseEntity<Object> findAllByUserId(int userId, int from, int size, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "from", from,
                "size", size
        );
        return get("?from={from}&size={size}", parameters, request);
    }

    public ResponseEntity<Object> findById(int userId, int eventId, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "eventId", eventId
        );
        return get("/{eventId}", parameters, request);
    }

    public ResponseEntity<Object> findRequests(int userId, int eventId, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "eventId", eventId
        );
        return get("/{eventId}/requests", parameters, request);
    }

    public ResponseEntity<Object> update(UpdateEventUserRequest dto, int userId, int eventId, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "eventId", eventId
        );
        return patch("/{eventId}", parameters, dto, request);
    }

    public ResponseEntity<Object> updateRequests(EventRequestStatusUpdateRequest dto, int userId, int eventId, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "eventId", eventId
        );
        return patch("/{eventId}/requests", parameters, dto, request);
    }

}