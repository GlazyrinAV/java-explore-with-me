package ru.practicum.ewmclient.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmclient.client.event.EventPublicClient;
import ru.practicum.ewmclient.controller.event.EventPublicController;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventPublicController.class)
class EventPublicTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EventPublicClient client;

    @Autowired
    private MockMvc mvc;

    @Test
    void findAllNormal() throws Exception {
        mvc.perform(get("/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("text", "test")
                        .param("categories", "1")
                        .param("paid", "true")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .param("onlyAvailable", "true")
                        .param("sort", "VIEWS")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutFrom() throws Exception {
        mvc.perform(get("/events")
                        .param("size", "2")
                        .param("text", "test")
                        .param("categories", "1")
                        .param("paid", "true")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .param("onlyAvailable", "true")
                        .param("sort", "VIEWS")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutSize() throws Exception {
        mvc.perform(get("/events")
                        .param("from", "1")
                        .param("text", "test")
                        .param("categories", "1")
                        .param("paid", "true")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .param("onlyAvailable", "true")
                        .param("sort", "VIEWS")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutText() throws Exception {
        mvc.perform(get("/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("categories", "1")
                        .param("paid", "true")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .param("onlyAvailable", "true")
                        .param("sort", "VIEWS")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutCategory() throws Exception {
        mvc.perform(get("/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("text", "test")
                        .param("paid", "true")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .param("onlyAvailable", "true")
                        .param("sort", "VIEWS")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutPaid() throws Exception {
        mvc.perform(get("/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("text", "test")
                        .param("categories", "1")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .param("onlyAvailable", "true")
                        .param("sort", "VIEWS")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutRangeStart() throws Exception {
        mvc.perform(get("/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("text", "test")
                        .param("categories", "1")
                        .param("paid", "true")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .param("onlyAvailable", "true")
                        .param("sort", "VIEWS")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutRangeEnd() throws Exception {
        mvc.perform(get("/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("text", "test")
                        .param("categories", "1")
                        .param("paid", "true")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("onlyAvailable", "true")
                        .param("sort", "VIEWS")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutAvailable() throws Exception {
        mvc.perform(get("/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("text", "test")
                        .param("categories", "1")
                        .param("paid", "true")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .param("sort", "VIEWS")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutSort() throws Exception {
        mvc.perform(get("/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("text", "test")
                        .param("categories", "1")
                        .param("paid", "true")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .param("onlyAvailable", "true")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutAll() throws Exception {
        mvc.perform(get("/events")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findByIdNormal() throws Exception {
        mvc.perform(get("/events/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findByIdEventIdNull() throws Exception {
        mvc.perform(get("/events/a")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}