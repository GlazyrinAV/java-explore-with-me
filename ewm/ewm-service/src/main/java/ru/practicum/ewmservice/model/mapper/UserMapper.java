package ru.practicum.ewmservice.model.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmcommondto.model.UserDto;
import ru.practicum.ewmservice.model.User;

@Component
public class UserMapper {

    public User fromDto(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserDto toShortDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

}