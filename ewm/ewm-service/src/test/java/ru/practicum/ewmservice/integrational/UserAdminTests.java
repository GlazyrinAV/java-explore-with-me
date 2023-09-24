package ru.practicum.ewmservice.integrational;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.service.user.UserAdminService;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserAdminTests {

    private final UserAdminService service;

    @Test
    void findAll() {
        Assertions.assertEquals("[UserDto(id=4, name=USER4, email=email4@mail.ru, mark=null), " +
                        "UserDto(id=5, name=USER5, email=email5@mail.ru, mark=null), " +
                        "UserDto(id=6, name=USER6, email=email6@mail.ru, mark=null)]",
                service.findAll(0, 10, List.of(4, 5, 6)).toString());
    }

}