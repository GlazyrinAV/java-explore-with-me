package ru.practicum.ewmclient.client.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;

public class EventPublicClient extends BaseClient {

    public EventPublicClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> findAll() {
        return null;
    }

    public ResponseEntity<Object> findById(@PathVariable int id) {
        return null;
    }

}