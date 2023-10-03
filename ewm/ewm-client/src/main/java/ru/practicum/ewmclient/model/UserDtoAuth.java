package ru.practicum.ewmclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDtoAuth {

    private String name;

    private String role;

    private String password;

}