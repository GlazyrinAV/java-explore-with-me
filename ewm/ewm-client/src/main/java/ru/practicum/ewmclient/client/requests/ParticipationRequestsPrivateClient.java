package ru.practicum.ewmclient.client.requests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;

import java.util.Map;

public class ParticipationRequestsPrivateClient extends BaseClient {

    public ParticipationRequestsPrivateClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(int usersId, int eventId) {
        Map<String, Object> parameters = Map.of(
                "userId", usersId,
                "eventId", eventId
        );
        return post("/?eventId={eventId}", parameters, null);
    }

    public ResponseEntity<Object> findAll(int userId) {
        Map<String, Object> parameters = Map.of(
                "userId", userId
        );
        return get("", parameters);
    }

    public ResponseEntity<Object> cancel(int usersId, int requestId) {
        Map<String, Object> parameters = Map.of(
                "userId", usersId,
                "requestId", requestId
        );
        return patch("/{requestId}/cancel", parameters, null);
    }

}