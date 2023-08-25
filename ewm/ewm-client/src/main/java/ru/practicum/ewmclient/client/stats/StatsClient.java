package ru.practicum.ewmclient.client.stats;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmclient.model.Hit;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class StatsClient extends BaseClient {

    public StatsClient(RestTemplate rest) {
        super(rest);
    }

    public void sendHit(HttpServletRequest request) {
        Hit hit = Hit.builder()
                .app("ewm-main")
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timeStamp(LocalDateTime.now())
                .build();
        post("", null, hit);
    }

}