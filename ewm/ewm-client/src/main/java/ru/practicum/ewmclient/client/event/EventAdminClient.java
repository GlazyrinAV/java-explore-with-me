package ru.practicum.ewmclient.client.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmclient.client.BaseClient;
import ru.practicum.ewmclient.model.event.UpdateEventAdminRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventAdminClient extends BaseClient {

    public EventAdminClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> findAll(int from,
                                          int size,
                                          Collection<Integer> users,
                                          Collection<String> states,
                                          Collection<Integer> categories,
                                          String rangeStart,
                                          String rangeEnd,
                                          HttpServletRequest request) {
        HashMap<String, Object> parameters = new HashMap<>();
        StringBuilder path = new StringBuilder("?from={from}&size={size}");
        parameters.put("from", from);
        parameters.put("size", size);
        if (users != null) {
            for (Integer id : users) {
                path.append("&users={userId").append(id).append("}");
                parameters.put("userId" + id, id);
            }
        }
        if (states != null) {
            for (String state : states) {
                path.append("&states={states").append(state).append("}");
                parameters.put("states" + state, state);
            }
        }
        if (categories != null) {
            for (Integer id : categories) {
                path.append("&categories={categories").append(id).append("}");
                parameters.put("categories" + id, id);
            }
        }
        if (rangeStart != null) {
            String start = URLEncoder.encode(rangeStart, Charset.defaultCharset());
            path.append("&rangeStart={rangeStart}");
            parameters.put("rangeStart", start);
        }
        if (rangeEnd != null) {
            String end = URLEncoder.encode(rangeEnd, Charset.defaultCharset());
            path.append("&rangeEnd={rangeEnd}");
            parameters.put("rangeEnd", end);
        }
        return get(path.toString(), parameters, request);
    }

    public ResponseEntity<Object> update(UpdateEventAdminRequest dto, int id, HttpServletRequest request) {
        Map<String, Object> parameters = Map.of(
                "eventId", id
        );
        return patch("/{eventId}", parameters, dto, request);
    }

}