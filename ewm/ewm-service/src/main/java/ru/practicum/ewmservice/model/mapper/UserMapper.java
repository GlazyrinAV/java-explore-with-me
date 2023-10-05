package ru.practicum.ewmservice.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmclient.model.user.Role;
import ru.practicum.ewmclient.model.user.UserDto;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.repository.MarkRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final MarkRepository markRepository;

    public User fromDto(UserDto dto) {
        String role = "user";
        if (dto.getRole() != null) {
            role = dto.getRole();
        }
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .role(Role.valueOf(role))
                .build();
    }

    public UserDto toDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
        Double mark = markRepository.findMarkForUser(user.getId());
        if (mark != null) {
            userDto.setMark(BigDecimal.valueOf(mark).setScale(1, RoundingMode.UP));
        }
        return userDto;
    }

    public UserDto toShortDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();

        Double mark = markRepository.findMarkForUser(user.getId());
        if (mark != null) {
            userDto.setMark(BigDecimal.valueOf(mark).setScale(1, RoundingMode.UP));
        }
        return userDto;
    }

}