package ru.practicum.statsclient.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.statsclient.dto.StatsDto;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class StatsClient extends BaseClient {

    public StatsClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> saveStats(StatsDto dto, HttpServletRequest request) {
        return post("/hit", dto, request);
    }

    public ResponseEntity<Object> findStats(String start, String end, String[] uris, boolean unique, HttpServletRequest request) {
        String path = "/stats?start={start}&end={end}&unique={unique}";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start);
        parameters.put("end", end);
        parameters.put("unique", unique);
        if (uris != null) {
            parameters.put("uris", uris);
            path = path + "&uris={uris}";
        }

        return get(path, parameters, request);
    }

}