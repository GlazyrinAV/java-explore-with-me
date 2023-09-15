package ru.practicum.ewmservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.UpdateCompilationRequest;

import java.io.IOException;
import java.util.List;

@JsonTest
public class UpdateCompilationRequestTests {

    @Autowired
    private JacksonTester<UpdateCompilationRequest> json;

    @Test
    void updateCompilationRequest() throws IOException {
        UpdateCompilationRequest dto = UpdateCompilationRequest.builder()
                .events(List.of(1, 2, 3))
                .pinned(true)
                .title("updateTitle")
                .build();

        JsonContent<UpdateCompilationRequest> result = json.write(dto);
        Assertions.assertEquals("{\"events\":[1,2,3],\"pinned\":true,\"title\":\"updateTitle\"}",
                result.getJson(), "Ошибка в обработке UpdateCompilationRequest.");
    }

}