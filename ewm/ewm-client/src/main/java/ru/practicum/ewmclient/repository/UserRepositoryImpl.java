package ru.practicum.ewmclient.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmclient.model.user.UserDtoAuth;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserDtoAuth findByEmail(String email) {
        String sqlQuery = "SELECT u.email AS email, u.role AS role, u.password AS password " +
                          "FROM users AS u WHERE u.email = (?)";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUserDto, email);
    }

    @Override
    public void saveToken(String token, String email) {
        String sqlQuery = "UPDATE users SET jwt = (?) WHERE users.email = (?)";
        jdbcTemplate.update(sqlQuery, token, email);
    }

    @Override
    public String findToken(String email) {
        String sqlQuery = "SELECT u.jwt FROM users AS u WHERE u.email = (?)";
        return jdbcTemplate.queryForObject(sqlQuery, String.class, email);
    }

    private UserDtoAuth mapRowToUserDto(ResultSet resultSet, int rowNum) throws SQLException {
        return UserDtoAuth.builder()
                .email(resultSet.getString("email"))
                .role(resultSet.getString("role"))
                .password(resultSet.getString("password"))
                .build();
    }

}