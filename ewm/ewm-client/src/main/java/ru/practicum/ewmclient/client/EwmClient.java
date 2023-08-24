package ru.practicum.ewmclient.client;

import org.springframework.web.client.RestTemplate;

public class EwmClient extends BaseClient{

    public EwmClient(RestTemplate rest) {
        super(rest);
    }

}