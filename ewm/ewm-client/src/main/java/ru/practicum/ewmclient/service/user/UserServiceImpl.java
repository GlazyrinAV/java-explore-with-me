package ru.practicum.ewmclient.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmclient.model.user.UserDtoAuth;
import ru.practicum.ewmclient.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public Optional<UserDtoAuth> findUser(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }

    @Override
    public void saveToken(String token, String email) {
        repository.saveToken(token, email);
    }

    @Override
    public String findToken(String email) {
        return repository.findToken(email);
    }

}