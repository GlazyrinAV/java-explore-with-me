package ru.practicum.ewmclient.client.categories;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;

import java.util.Map;

public class CategoryPublicClient extends BaseClient {

    public CategoryPublicClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> findById(int catId) {
        Map<String, Object> parameters = Map.of(
                "catId", catId
        );
        return get("/{catId}", parameters);
    }

    public ResponseEntity<Object> findAll() {
        return get("", null);
    }

}