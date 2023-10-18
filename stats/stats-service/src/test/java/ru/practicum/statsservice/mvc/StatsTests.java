package ru.practicum.statsservice.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.statsservice.controller.StatsController;
import ru.practicum.statsclient.dto.StatsDto;
import ru.practicum.statsservice.service.StatsService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StatsTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    StatsService service;

    @Autowired
    private MockMvc mvc;

    @Test
    void saveStatsNormal() throws Exception {
        String cred = "admin:admin";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
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
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findStatsNormal() throws Exception {
        String cred = "admin:admin";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/stats")
                        .param("start", "2011-11-11 11:11")
                        .param("end", "2012-11-11 11:11")
                        .param("uris", "/event/1")
                        .param("unique", "true")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findStatsNormalEmptyUnique() throws Exception {
        String cred = "admin:admin";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/stats")
                        .param("start", "2011-11-11 11:11")
                        .param("end", "2012-11-11 11:11")
                        .param("uris", "/event/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
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

}