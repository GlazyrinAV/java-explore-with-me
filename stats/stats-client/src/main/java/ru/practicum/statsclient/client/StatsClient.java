package ru.practicum.statsclient.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.statscommondto.StatsDto;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class StatsClient extends BaseClient {

    public StatsClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> saveStats(StatsDto dto) {
        return post("/hit", dto);
    }

    public ResponseEntity<Object> findStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        String path = "/stats?start={start}&end={end}&unique={unique}";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", URLEncoder.encode(start.toString(), Charset.defaultCharset()));
        parameters.put("end", URLEncoder.encode(end.toString(), Charset.defaultCharset()));
        parameters.put("unique", unique);
        if (uris != null) {
            parameters.put("uris", uris);
            path = path + "&uris={uris}";
        }

        return get(path, parameters);
    }

}