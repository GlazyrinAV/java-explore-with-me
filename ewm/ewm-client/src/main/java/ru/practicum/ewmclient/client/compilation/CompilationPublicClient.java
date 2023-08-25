package ru.practicum.ewmclient.client.compilation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;

import java.util.Map;

public class CompilationPublicClient extends BaseClient {

    public CompilationPublicClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> findAll(boolean pinned, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "pinned", pinned,
                "from", from,
                "size", size
        );
        return get("?pinned={pinned}&from={from}&size={size}", parameters);
    }

    public ResponseEntity<Object> findById(int compId) {
        Map<String, Object> parameters = Map.of(
                "compId", compId
        );
        return get("/{compId}", parameters);
    }

}