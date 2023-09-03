package ru.practicum.ewmclient.client.marks;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;

import java.util.Map;

public class MarksClient extends BaseClient {

    public MarksClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(int userId, int eventId, int mark) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "eventId", eventId,
                "mark", mark
        );
        return post("?mark={mark}", parameters, null);
    }

    public ResponseEntity<Object> remove(int userId, int eventId) {
        Map<String, Object> parameters = Map.of(
                "userId", userId,
                "eventId", eventId
        );
        return delete("/", parameters);
    }

}