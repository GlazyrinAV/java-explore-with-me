package ru.practicum.ewmclient.client.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;

import java.util.Map;

public class EventPublicClient extends BaseClient {

    public EventPublicClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> findAll() {
        return get("", null);
    }

    public ResponseEntity<Object> findById(@PathVariable int id) {
        Map<String, Object> parameters = Map.of(
                "id", id
        );
        return get("/{id}", parameters);
    }

}