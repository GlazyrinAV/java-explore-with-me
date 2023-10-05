package ru.practicum.ewmclient.service.user;

import ru.practicum.ewmclient.model.user.UserDtoAuth;

import java.util.Optional;

public interface UserService {

    Optional<UserDtoAuth> findUser(String email);

    void saveToken(String token, String email);

    String findToken(String email);

}