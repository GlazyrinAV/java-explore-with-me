package ru.practicum.ewmclient.model;

import lombok.Data;

@Data
public class RefreshJwtRequest {

    private String refreshToken;

}