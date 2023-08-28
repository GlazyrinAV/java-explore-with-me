package ru.practicum.ewmservice.service.stats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
        Integer hits = 0;
        try {
            URL url = new URL(serverUrl + path);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();

            int BUFFER_SIZE = 1024;
            char[] buffer = new char[BUFFER_SIZE];
            int charsRead = 0;
            while ((charsRead = rd.read(buffer, 0, BUFFER_SIZE)) != -1) {
                sb.append(buffer, 0, charsRead);
            }

            ObjectMapper mapper = new ObjectMapper();
            hits = mapper.readValue(sb.toString(), Integer.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (hits != null) {
            return hits;
        } else {
            return 0;
        }
    }

}