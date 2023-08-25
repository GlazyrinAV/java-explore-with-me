package ru.practicum.ewmclient.client.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmcommondto.model.UserDto;

public class UserAdminClient extends BaseClient {

    public UserAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(UserDto dto) {
        return null;
    }

    public ResponseEntity<Object> findAll() {
        return null;
    }

    public ResponseEntity<Object> remove(int userId) {
        return null;
    }

}