package ru.practicum.ewmservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.Hit;

import java.io.IOException;

@JsonTest
class HitTests {

    @Autowired
    private JacksonTester<Hit> json;

    @Test
    void hit() throws IOException {
        Hit hit = Hit.builder()
                .app("testApp")
                .ip("1.1.1.1.1")
                .uri("testsUri")
                .timeStamp("2011-11-11 11:11")
                .build();

        JsonContent<Hit> result = json.write(hit);
        Assertions.assertEquals("{\"app\":\"testApp\",\"uri\":\"testsUri\"," +
                        "\"ip\":\"1.1.1.1.1\",\"timestamp\":\"2011-11-11 11:11\"}", result.getJson(),
                "Ошибка обработки Hit.");
    }

}