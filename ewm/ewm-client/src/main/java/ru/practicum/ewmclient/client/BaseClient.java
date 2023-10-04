package ru.practicum.ewmclient.client;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class BaseClient {

    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <T> ResponseEntity<Object> post(String path, @Nullable Map<String, Object> parameters, T body, HttpServletRequest request) {
        return makeAndSendRequest(HttpMethod.POST, path, parameters, body, request);
    }

    protected ResponseEntity<Object> get(String path, Map<String, Object> parameters, HttpServletRequest request) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null, request);
    }

    protected ResponseEntity<Object> delete(String path, @Nullable Map<String, Object> parameters, HttpServletRequest request) {
        return makeAndSendRequest(HttpMethod.DELETE, path, parameters, null, request);
    }

    protected <T> ResponseEntity<Object> patch(String path, @Nullable Map<String, Object> parameters, T body, HttpServletRequest request) {
        return makeAndSendRequest(HttpMethod.PATCH, path, parameters, body, request);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> parameters, @Nullable T body, HttpServletRequest request) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders(request));

        ResponseEntity<Object> statsServerResponse;
        try {
            if (parameters != null) {
                statsServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                statsServerResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareResponse(statsServerResponse);
    }

    private HttpHeaders defaultHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (request != null) {
            String authorization = request.getHeader("Authorization");
            headers.add("Authorization", authorization);
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static ResponseEntity<Object> prepareResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }

}