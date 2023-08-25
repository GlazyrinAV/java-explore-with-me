package ru.practicum.ewmclient.client.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmcommondto.model.UserDto;

import java.util.Map;

public class UserAdminClient extends BaseClient {

    public UserAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(UserDto dto) {
        return post("", null, dto);
    }

    public ResponseEntity<Object> findAll() {
        return get("", null);
    }

    public ResponseEntity<Object> remove(int userId) {
        Map<String, Object> parameters = Map.of(
                "userId", userId
        );
        return delete("/{userId}", parameters);
    }

}