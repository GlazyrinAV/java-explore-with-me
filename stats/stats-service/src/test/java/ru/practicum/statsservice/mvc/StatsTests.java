package ru.practicum.statsservice.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.statsservice.controller.StatsController;
import ru.practicum.statsclient.dto.StatsDto;
import ru.practicum.statsservice.service.StatsService;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
class StatsTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    StatsService service;

    @Autowired
    private MockMvc mvc;

    @Test
    void saveStatsNormal() throws Exception {
        StatsDto dto = StatsDto.builder()
                .app("testApp")
                .ip("1.1.1.1")
                .uri("test.ru")
                .timeStamp("2011-11-11 11:11")
                .build();
        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findStatsNormal() throws Exception {
        mvc.perform(get("/stats")
                        .param("start", "2011-11-11 11:11")
                        .param("end", "2012-11-11 11:11")
                        .param("uris", "/event/1")
                        .param("unique", "true")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findStatsNormalEmptyUnique() throws Exception {
        mvc.perform(get("/stats")
                        .param("start", "2011-11-11 11:11")
                        .param("end", "2012-11-11 11:11")
                        .param("uris", "/event/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findStatsEmptyStart() throws Exception {
        mvc.perform(get("/stats")
                        .param("end", "2012-11-11 11:11")
                        .param("uris", "/event/1")
                        .param("unique", "true")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void findStatsEmptyEnd() throws Exception {
        mvc.perform(get("/stats")
                        .param("start", "2011-11-11 11:11")
                        .param("uris", "/event/1")
                        .param("unique", "true")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void findStatsForEwmNormal() throws Exception {
        mvc.perform(get("/stats/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findStatsForEwmIdNull() throws Exception {
        mvc.perform(get("/stats/")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}