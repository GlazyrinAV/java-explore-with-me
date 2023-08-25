package ru.practicum.ewmclient.client.compilation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;

public class CompilationPublicClient extends BaseClient {

    public CompilationPublicClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> findAll() {
        return null;
    }

    public ResponseEntity<Object> findById(int compId) {
        return null;
    }

}