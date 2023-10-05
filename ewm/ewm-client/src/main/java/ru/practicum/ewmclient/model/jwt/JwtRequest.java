package ru.practicum.ewmclient.model.jwt;

import lombok.Data;

@Data
public class JwtRequest {

    private String login;

    private String password;

}