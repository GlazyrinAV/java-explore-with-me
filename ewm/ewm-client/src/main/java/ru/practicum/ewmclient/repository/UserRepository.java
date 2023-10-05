package ru.practicum.ewmclient.repository;

import ru.practicum.ewmclient.model.user.UserDtoAuth;

public interface UserRepository {

    UserDtoAuth findByEmail(String email);

    void saveToken(String token, String email);

    String findToken(String email);

}