package ru.practicum.ewmservice.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.model.UserDto;
import ru.practicum.ewmservice.model.mapper.UserMapper;
import ru.practicum.ewmservice.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    public UserDto save(UserDto dto) {
        return mapper.toDto(userRepository.save(mapper.fromDto(dto)));
    }

    public Collection<UserDto> findAll() {
        return userRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public void remove(int userId) {
        userRepository.deleteById(userId);
    }

}