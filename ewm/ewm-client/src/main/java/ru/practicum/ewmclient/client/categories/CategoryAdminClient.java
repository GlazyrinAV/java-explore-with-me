package ru.practicum.ewmclient.client.categories;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmcommondto.model.CategoryDto;

public class CategoryAdminClient extends BaseClient {

    public CategoryAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(CategoryDto dto) {
        return null;
    }

    public ResponseEntity<Object> update(CategoryDto dto, int catId) {
        return null;
    }

    public ResponseEntity<Object> remove(int catId) {
        return null;
    }
}