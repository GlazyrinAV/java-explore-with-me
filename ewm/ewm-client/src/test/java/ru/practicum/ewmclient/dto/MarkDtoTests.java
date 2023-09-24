package ru.practicum.ewmclient.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.MarkDto;

import java.io.IOException;

@JsonTest
class MarkDtoTests {

    @Autowired
    private JacksonTester<MarkDto> json;

    @Test
    void markDto() throws IOException {
        MarkDto dto = MarkDto.builder()
                .mark(5)
                .userId(1)
                .eventId(2)
                .build();

        JsonContent<MarkDto> result = json.write(dto);
        Assertions.assertEquals("{\"userId\":1,\"eventId\":2,\"mark\":5}", result.getJson(),
                "Ошибка обработки MarkDto.");
    }

}