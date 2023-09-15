package ru.practicum.ewmclient.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.NewCompilationDto;

import java.io.IOException;
import java.util.List;

@JsonTest
public class NewCompilationDtoTests {

    @Autowired
    private JacksonTester<NewCompilationDto> json;

    @Test
    void newCompilationDto() throws IOException {
        NewCompilationDto dto = NewCompilationDto.builder()
                .events(List.of(1, 2, 3))
                .pinned(true)
                .title("testCompilationTitle")
                .build();

        JsonContent<NewCompilationDto> result = json.write(dto);
        Assertions.assertEquals("{\"title\":\"testCompilationTitle\",\"pinned\":true,\"events\":[1,2,3]}",
                result.getJson(),"Ошибка в обработке NewCompilationDto.");
    }

}