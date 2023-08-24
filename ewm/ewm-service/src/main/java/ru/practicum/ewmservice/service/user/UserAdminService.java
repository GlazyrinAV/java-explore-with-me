package ru.practicum.ewmservice.service.user;

import ru.practicum.ewmcommondto.model.UserDto;

import java.util.Collection;

public interface UserAdminService {

    UserDto save(UserDto dto);

    Collection<UserDto> findAll();

    void remove(int UserId);

}