package ru.practicum.ewmclient.client.categories;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;

public class CategoryPublicClient extends BaseClient {

    public CategoryPublicClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> findById(int catId) {
        return null;
    }

    public ResponseEntity<Object> findAll() {
        return null;
    }

}