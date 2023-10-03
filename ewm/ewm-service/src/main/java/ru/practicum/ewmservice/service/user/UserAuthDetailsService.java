package ru.practicum.ewmservice.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.practicum.ewmclient.model.UserDtoAuth;
import ru.practicum.ewmservice.model.mapper.UserMapper;
import ru.practicum.ewmservice.repository.UserRepository;

import java.util.Collection;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    private UserDtoAuth findUser(String name) {
        return mapper.toDtoAuth(userRepository.findByName(name));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDtoAuth user = findUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("Данный пользователь не найден.");
        }
        return new User(user.getName(), user.getPassword(), authorities(user));
    }

    private Collection<? extends GrantedAuthority> authorities(UserDtoAuth user) {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

}