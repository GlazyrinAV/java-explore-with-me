package ru.practicum.ewmclient.service.authentication;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmclient.jwt.JwtProvider;
import ru.practicum.ewmclient.model.jwt.JwtRequest;
import ru.practicum.ewmclient.model.jwt.JwtResponse;
import ru.practicum.ewmclient.model.user.UserDtoAuth;
import ru.practicum.ewmclient.service.user.UserService;

import javax.security.auth.message.AuthException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final UserDtoAuth user = userService.findUser(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));

        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            userService.saveToken(refreshToken, user.getEmail());
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = userService.findToken(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDtoAuth user = userService.findUser(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = userService.findToken(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDtoAuth user = userService.findUser(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                userService.saveToken(refreshToken, user.getEmail());
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

}