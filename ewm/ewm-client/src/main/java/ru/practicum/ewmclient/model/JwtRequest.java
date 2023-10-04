package ru.practicum.ewmclient.model;

import lombok.Data;

@Data
public class JwtRequest {

    private String login;

    private String password;

}