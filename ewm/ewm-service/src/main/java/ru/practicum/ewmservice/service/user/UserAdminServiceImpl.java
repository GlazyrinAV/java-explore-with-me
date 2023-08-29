package ru.practicum.ewmservice.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmclient.model.UserDto;
import ru.practicum.ewmservice.exceptions.exceptions.UserNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.WrongParameter;
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

    @Override
    @Transactional
    public UserDto save(UserDto dto) {
        if (userRepository.findByName(dto.getName()) != null) {
            throw new WrongParameter("Пользователь с таким именем уже существует.");
        }
        return mapper.toDto(userRepository.save(mapper.fromDto(dto)));
    }

    @Override
    public Collection<UserDto> findAll(int from, int size, Collection<Integer> ids) {
        Pageable page = PageRequest.of(from == 0 ? 0 : from / size, size);
        return userRepository.findAllAdminWithCriteria(page, ids).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remove(int userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        userRepository.deleteById(userId);
    }

}