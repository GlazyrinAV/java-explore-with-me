package ru.practicum.ewmclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.practicum.ewmclient.model.UserDtoAuth;
import ru.practicum.ewmclient.repository.UserRepository;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private UserDtoAuth findUser(String name) {
        return repository.findByName(name);
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