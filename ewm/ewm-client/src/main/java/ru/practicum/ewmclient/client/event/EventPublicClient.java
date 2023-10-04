package ru.practicum.ewmclient.client.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmclient.client.stats.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventPublicClient extends BaseClient {

    public EventPublicClient(RestTemplate rest, StatsClient client) {
        super(rest);
        this.client = client;
    }

    private final StatsClient client;

    public ResponseEntity<Object> findAll(int from,
                                          int size,
                                          String text,
                                          Collection<Integer> categories,
                                          Boolean paid,
                                          String rangeStart,
                                          String rangeEnd,
                                          Boolean onlyAvailable,
                                          String sort,
                                          HttpServletRequest request) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("from", from);
        parameters.put("size", size);
        StringBuilder path = new StringBuilder("?from={from}&size={size}");

        if (text != null) {
            parameters.put("text", text);
            path.append("&text={text}");
        }
        if (categories != null) {
            for (Integer id : categories) {
                parameters.put("categories" + id, id);
                path.append("&categories={categories").append(id).append("}");
            }
        }
        if (paid != null) {
            parameters.put("paid", paid);
            path.append("&paid={paid}");
        }
        if (rangeStart != null) {
            parameters.put("rangeStart", rangeStart);
            path.append("&rangeStart={rangeStart}");
        }
        if (rangeEnd != null) {
            parameters.put("rangeEnd", rangeEnd);
            path.append("&rangeEnd={rangeEnd}");
        }
        if (onlyAvailable != null) {
            parameters.put("onlyAvailable", onlyAvailable);
            path.append("&onlyAvailable={onlyAvailable}");
        }
        if (sort != null) {
            parameters.put("sort", sort);
            path.append("&sort={sort}");
        }

        client.sendHit(request);
        return get(path.toString(), parameters, null);
    }

    public ResponseEntity<Object> findById(@PathVariable int id, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "id", id
        );
        client.sendHit(request);
        return get("/{id}", parameters, request);
    }

}