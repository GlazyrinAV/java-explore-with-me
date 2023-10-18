package ru.practicum.ewmclient.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmclient.model.UserDtoAuth;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserDtoAuth findByName(String name) {
        String sqlQuery = "SELECT u.name AS name, u.role AS role, u.password AS password FROM users AS u WHERE u.name = (?)";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUserDto, name);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private UserDtoAuth mapRowToUserDto(ResultSet resultSet, int rowNum) throws SQLException {
        if (resultSet != null) {
            return UserDtoAuth.builder()
                    .name(resultSet.getString("name"))
                    .role(resultSet.getString("role"))
                    .password(resultSet.getString("password"))
                    .build();
        }
        return null;
    }

}