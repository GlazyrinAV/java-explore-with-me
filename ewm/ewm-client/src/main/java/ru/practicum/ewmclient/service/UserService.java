package ru.practicum.ewmclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmclient.model.UserDtoAuth;
import ru.practicum.ewmclient.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Optional<UserDtoAuth> findUser(String name) {
        return Optional.ofNullable(repository.findByName(name));
    }

}