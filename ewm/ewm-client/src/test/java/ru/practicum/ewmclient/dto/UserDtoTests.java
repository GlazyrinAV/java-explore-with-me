package ru.practicum.ewmclient.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.UserDto;

import java.io.IOException;
import java.math.BigDecimal;

@JsonTest
class UserDtoTests {

    @Autowired
    private JacksonTester<UserDto> json;

    @Test
    void user() throws IOException {
        UserDto dto = UserDto.builder()
                .id(1)
                .name("testName")
                .email("test@email")
                .mark(BigDecimal.valueOf(5))
                .build();

        JsonContent<UserDto> result = json.write(dto);
        Assertions.assertEquals("{\"id\":1,\"mark\":5,\"name\":\"testName\",\"email\":\"test@email\"}",
                result.getJson(), "Ошибка в обработке UserDto.");
    }

}