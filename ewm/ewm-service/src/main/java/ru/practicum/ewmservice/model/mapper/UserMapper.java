package ru.practicum.ewmservice.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmclient.model.UserDto;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.repository.MarkRepository;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final MarkRepository markRepository;

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
                .mark(markRepository.findMarkForUser(user.getId()))
                .build();
    }

    public UserDto toShortDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

}