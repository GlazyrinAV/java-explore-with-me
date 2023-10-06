package ru.practicum.ewmclient.client.compilation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;

import java.util.HashMap;
import java.util.Map;

public class CompilationPublicClient extends BaseClient {

    public CompilationPublicClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> findAll(Boolean pinned, int from, int size) {
        String path = "?from={from}&size={size}";
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("from", from);
        parameters.put("size", size);
        if (pinned != null) {
            path = path + "&pinned={pinned}";
            parameters.put("pinned", pinned);
        }
        return get(path, parameters, null);
    }

    public ResponseEntity<Object> findById(int compId) {
        Map<String, Object> parameters = Map.of(
                "compId", compId
        );
        return get("/{compId}", parameters, null);
    }

}