package ru.practicum.ewmclient.model.jwt;

import lombok.Data;

@Data
public class RefreshJwtRequest {

    private String refreshToken;

}