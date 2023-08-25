package ru.practicum.ewmclient.client.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmcommondto.model.EventDto;

public class EventAdminClient extends BaseClient {

    public EventAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> findAll() {
        return null;
    }

    public ResponseEntity<Object> update(EventDto dto, int id) {
        return null;
    }

}