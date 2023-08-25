package ru.practicum.ewmclient.client.requests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;

public class ParticipationRequestsPrivateClient extends BaseClient {

    public ParticipationRequestsPrivateClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(int usersId, int eventId) {
        return null;
    }

    public ResponseEntity<Object> findAll(int usersId) {
        return null;
    }

    public ResponseEntity<Object> cancel(int usersId, int requestId) {
        return null;
    }

}