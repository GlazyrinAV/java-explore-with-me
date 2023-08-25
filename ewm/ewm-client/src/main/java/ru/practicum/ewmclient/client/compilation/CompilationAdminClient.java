package ru.practicum.ewmclient.client.compilation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmcommondto.model.NewCompilationDto;

import java.util.Map;

public class CompilationAdminClient extends BaseClient {

    public CompilationAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(NewCompilationDto dto) {
        return post("", null, dto);
    }

    public ResponseEntity<Object> update(NewCompilationDto dto, int compId) {
        Map<String, Object> parameters = Map.of(
                "compId", compId
        );
        return patch("/{compId}", parameters, dto);
    }

    public ResponseEntity<Object> remove(int compId) {
        Map<String, Object> parameters = Map.of(
                "compId", compId
        );
        return delete("/{compId}", parameters);
    }

}