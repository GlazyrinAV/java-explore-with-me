package ru.practicum.ewmservice.service.statsrequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StatsRequestServiceImpl implements StatsRequestService {

    @Value("${stats-client.url}")
    private String serverUrl;

    @Override
    public int getViews(int eventId) {

        String path = "/stats/" + eventId;

        HttpGet httpget = new HttpGet(serverUrl + path);

        ResponseHandler<String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String responseBody = httpclient.execute(httpget, responseHandler);
            if (responseBody.isBlank()) {
                return 0;
            } else {
                return Integer.parseInt(responseBody);
            }
        } catch (IOException e) {
            return 0;
        }
    }

}