package ru.practicum.ewmservice.service.user;

import ru.practicum.ewmclient.model.user.UserDto;

import java.util.Collection;

public interface UserAdminService {

    UserDto save(UserDto dto);

    Collection<UserDto> findAll(int from, int size, Collection<Integer> ids);

    void remove(int userId);

}