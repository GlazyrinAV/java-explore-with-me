package ru.practicum.ewmclient.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmclient.client.compilation.CompilationAdminClient;
import ru.practicum.ewmclient.controller.compilation.CompilationAdminController;
import ru.practicum.ewmclient.model.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CompilationAdminController.class)
class CompilationAdminTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CompilationAdminClient client;

    @Autowired
    private MockMvc mvc;

    @Test
    void saveNormal() throws Exception {
        NewCompilationDto dto = NewCompilationDto.builder()
                .events(List.of(1))
                .pinned(true)
                .title("test CompilationTitle")
                .build();
        mvc.perform(post("/admin/compilations")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateNormal() {

    }

    @Test
    void removeNormal() {

    }

}