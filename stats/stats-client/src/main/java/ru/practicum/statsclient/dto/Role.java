package ru.practicum.statsclient.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    admin("admin"), user("user");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }

}