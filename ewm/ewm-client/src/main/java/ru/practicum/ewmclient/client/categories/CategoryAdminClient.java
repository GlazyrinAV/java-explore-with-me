package ru.practicum.ewmclient.client.categories;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmclient.model.CategoryDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CategoryAdminClient extends BaseClient {

    public CategoryAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(CategoryDto dto, HttpServletRequest request) {
        return post("", null, dto, request);
    }

    public ResponseEntity<Object> update(CategoryDto dto, int catId, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "catId", catId
        );
        return patch("/{catId}", parameters, dto, request);
    }

    public ResponseEntity<Object> remove(int catId, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "catId", catId
        );
        return delete("/{catId}", parameters, request);
    }

}