package ru.practicum.ewmclient.repository;

import ru.practicum.ewmclient.model.UserDtoAuth;

public interface UserRepository {

    UserDtoAuth findByName(String name);

}