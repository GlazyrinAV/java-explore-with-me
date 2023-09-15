package ru.practicum.statsservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.statsclient.dto.StatsDto;

import java.io.IOException;

@JsonTest
public class StatsDtoTests {

    @Autowired
    private JacksonTester<StatsDto> json;

    @Test
    void StatsDto() throws IOException {
        StatsDto statsDto = StatsDto.builder()
                .uri("testUri")
                .ip("111.111.111.111.111")
                .app("testApp")
                .timeStamp("2011-11-11 11:11")
                .build();

        JsonContent<StatsDto> result = json.write(statsDto);
        Assertions.assertEquals("{\"app\":\"testApp\",\"uri\":\"testUri\",\"ip\":\"111.111.111.111.111\"," +
                        "\"timestamp\":\"2011-11-11 11:11\"}",
                result.getJson(), "Ошибка при обработке StatsDto.");
    }

}