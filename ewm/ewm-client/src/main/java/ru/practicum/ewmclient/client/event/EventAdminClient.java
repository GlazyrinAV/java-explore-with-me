package ru.practicum.ewmclient.client.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmcommondto.model.EventDto;

import java.util.Map;

public class EventAdminClient extends BaseClient {

    public EventAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> findAll() {
        return get("", null);
    }

    public ResponseEntity<Object> update(EventDto dto, int id) {
        Map<String, Object> parameters = Map.of(
                "eventId", id
        );
        return patch("/{eventId}", parameters, dto);
    }

}