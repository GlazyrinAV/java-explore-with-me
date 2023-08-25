package ru.practicum.ewmclient.client.compilation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmcommondto.model.NewCompilationDto;

public class CompilationAdminClient extends BaseClient {

    public CompilationAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(NewCompilationDto dto) {
        return null;
    }

    public ResponseEntity<Object> update(NewCompilationDto dto, int compId) {
        return null;
    }

    public ResponseEntity<Object> remove(int compId) {
        return null;
    }

}