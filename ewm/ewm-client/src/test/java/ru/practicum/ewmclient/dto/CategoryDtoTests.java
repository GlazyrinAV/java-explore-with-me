package ru.practicum.ewmclient.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.CategoryDto;

import java.io.IOException;

@JsonTest
class CategoryDtoTests {

    @Autowired
    private JacksonTester<CategoryDto> json;

    @Test
    void categoryDto() throws IOException {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();

        JsonContent<CategoryDto> result = json.write(categoryDto);
        Assertions.assertEquals("{\"id\":1,\"name\":\"testCategory\"}", result.getJson(),
                "Ошибка при обработке CategoryDto.");
    }

}