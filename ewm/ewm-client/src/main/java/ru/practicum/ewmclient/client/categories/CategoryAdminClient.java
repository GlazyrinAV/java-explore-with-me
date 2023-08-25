package ru.practicum.ewmclient.client.categories;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmcommondto.model.CategoryDto;

import java.util.Map;

public class CategoryAdminClient extends BaseClient {

    public CategoryAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(CategoryDto dto) {
        return post("", null, dto);
    }

    public ResponseEntity<Object> update(CategoryDto dto, int catId) {
        Map<String, Object> parameters = Map.of(
                "catId", catId
        );
        return patch("/{catId}", parameters, dto);
    }

    public ResponseEntity<Object> remove(int catId) {
        Map<String, Object> parameters = Map.of(
                "catId", catId
        );
        return delete("/{catId}", parameters);
    }

}