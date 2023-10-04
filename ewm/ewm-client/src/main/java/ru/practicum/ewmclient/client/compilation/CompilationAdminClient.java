package ru.practicum.ewmclient.client.compilation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmclient.model.NewCompilationDto;
import ru.practicum.ewmclient.model.UpdateCompilationRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CompilationAdminClient extends BaseClient {

    public CompilationAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(NewCompilationDto dto, HttpServletRequest request) {
        return post("", null, dto, request);
    }

    public ResponseEntity<Object> update(UpdateCompilationRequest dto, int compId, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "compId", compId
        );
        return patch("/{compId}", parameters, dto, request);
    }

    public ResponseEntity<Object> remove(int compId, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "compId", compId
        );
        return delete("/{compId}", parameters, request);
    }

}