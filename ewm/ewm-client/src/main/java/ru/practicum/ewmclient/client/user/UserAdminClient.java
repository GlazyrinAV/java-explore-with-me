package ru.practicum.ewmclient.client.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmclient.model.UserDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserAdminClient extends BaseClient {

    public UserAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> save(UserDto dto) {
        return post("", null, dto);
    }

    public ResponseEntity<Object> findAll(int from, int size, Collection<Integer> ids) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("from", from);
        parameters.put("size", size);
        StringBuilder path = new StringBuilder("?from={from}&size={size}");
        if (ids != null) {
            for (Integer id : ids) {
                parameters.put("ids" + id, id);
                path.append("&ids={ids").append(id).append("}");
            }
        }
        return get(path.toString(), parameters);
    }

    public ResponseEntity<Object> remove(int userId) {
        Map<String, Object> parameters = Map.of(
                "userId", userId
        );
        return delete("/{userId}", parameters);
    }

}