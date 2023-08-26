package ru.practicum.ewmservice.service.stats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {

    public StatsServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Value("${stats-client.url}")
    private String serverUrl;

    private final RestTemplate restTemplate;

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }

    HttpEntity<String> entity = new HttpEntity<>(null, defaultHeaders());

    @Override
    public int getViews(int eventId) {
        String path = "/stats/" + eventId;
        ResponseEntity<String> response = restTemplate.exchange(serverUrl + path, HttpMethod.GET, entity, String.class);
        Integer hits = 0;
        try {
            ObjectMapper mapper = new ObjectMapper();
            hits = mapper.readValue(response.getBody(), Integer.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getLocalizedMessage());
        }
        if (hits != null) {
            return hits;
        } else {
            return 0;
        }
    }

}