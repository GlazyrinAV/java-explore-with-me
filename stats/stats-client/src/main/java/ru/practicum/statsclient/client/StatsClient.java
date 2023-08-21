package ru.practicum.statsclient.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.statscommondto.model.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.Map;

public class StatsClient extends BaseClient {

    public StatsClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> saveStats(StatsDto dto) {
        return post("/hit", dto);
    }

    public ResponseEntity<Object> findStats(LocalDateTime start, LocalDateTime end, String uri, Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uri", uri,
                "unique", unique
        );
        return get("/stats", parameters);
    }

}