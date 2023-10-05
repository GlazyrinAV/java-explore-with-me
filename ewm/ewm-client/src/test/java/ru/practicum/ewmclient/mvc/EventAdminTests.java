package ru.practicum.ewmclient.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmclient.client.event.EventAdminClient;
import ru.practicum.ewmclient.controller.event.EventAdminController;
import ru.practicum.ewmclient.model.category.CategoryDto;
import ru.practicum.ewmclient.model.event.EventDto;
import ru.practicum.ewmclient.model.event.UpdateEventAdminRequest;
import ru.practicum.ewmclient.model.location.LocationDto;
import ru.practicum.ewmclient.model.user.UserDto;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventAdminController.class)
class EventAdminTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EventAdminClient client;

    @Autowired
    private MockMvc mvc;

    @Test
    void findAllNormal() throws Exception {
        mvc.perform(get("/admin/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("users", "1")
                        .param("states", "PENDING")
                        .param("categories", "1")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutFrom() throws Exception {
        mvc.perform(get("/admin/events")
                        .param("size", "2")
                        .param("users", "1")
                        .param("states", "PENDING")
                        .param("categories", "1")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutSize() throws Exception {
        mvc.perform(get("/admin/events")
                        .param("from", "1")
                        .param("users", "1")
                        .param("states", "PENDING")
                        .param("categories", "1")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutUsers() throws Exception {
        mvc.perform(get("/admin/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("states", "PENDING")
                        .param("categories", "1")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutStates() throws Exception {
        mvc.perform(get("/admin/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("users", "1")
                        .param("categories", "1")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutCategories() throws Exception {
        mvc.perform(get("/admin/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("users", "1")
                        .param("states", "PENDING")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutRangeStart() throws Exception {
        mvc.perform(get("/admin/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("users", "1")
                        .param("states", "PENDING")
                        .param("categories", "1")
                        .param("rangeStart", "2011-11-11 11:11")
                        .param("rangeEnd", "2012-12-12 12:12")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutRangeEnd() throws Exception {
        mvc.perform(get("/admin/events")
                        .param("from", "1")
                        .param("size", "2")
                        .param("users", "1")
                        .param("states", "PENDING")
                        .param("categories", "1")
                        .param("rangeStart", "2011-11-11 11:11")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutAll() throws Exception {
        mvc.perform(get("/admin/events")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateNormal() throws Exception {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        UserDto userDto = UserDto.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        EventDto eventDto = EventDto.builder()
                .id(1)
                .eventDate("2011-11-11 11:11")
                .annotation("updateAnnotation")
                .category(categoryDto)
                .confirmedRequests(10)
                .createdOn("2011-10-10 10:10")
                .paid(true)
                .title("updateTitle")
                .description("updateDescription")
                .participantLimit(20)
                .initiator(userDto)
                .location(locationDto)
                .requestModeration(true)
                .state("PUBLISHED")
                .publishedOn("2011-10-11 11:11")
                .views(10)
                .build();
        UpdateEventAdminRequest request = UpdateEventAdminRequest.builder()
                .title("updateTitle")
                .annotation("updateAnnotation")
                .category(1)
                .eventDate("2011-11-11 11:11")
                .description("updateDescription")
                .requestModeration(true)
                .location(locationDto)
                .participantLimit(100)
                .paid(true)
                .stateAction("PUBLISH_EVENT")
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(eventDto, HttpStatus.OK);
        when(client.update(any(), anyInt(), null))
                .thenReturn(response);
        mvc.perform(patch("/admin/events/1")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title", is(eventDto.getTitle())))
                .andExpect(jsonPath("$.annotation", is(eventDto.getAnnotation())))
                .andExpect(jsonPath("$.category.id", is(eventDto.getCategory().getId())))
                .andExpect(jsonPath("$.category.name", is(eventDto.getCategory().getName())))
                .andExpect(jsonPath("$.eventDate", is(eventDto.getEventDate())))
                .andExpect(jsonPath("$.id", is(eventDto.getId())))
                .andExpect(jsonPath("$.description", is(eventDto.getDescription())))
                .andExpect(jsonPath("$.requestModeration", is(eventDto.isRequestModeration())))
                .andExpect(jsonPath("$.location.id", is(eventDto.getLocation().getId())))
                .andExpect(jsonPath("$.location.lat", is(eventDto.getLocation().getLat())))
                .andExpect(jsonPath("$.location.lon", is(eventDto.getLocation().getLon())))
                .andExpect(jsonPath("$.paid", is(eventDto.isPaid())))
                .andExpect(jsonPath("$.views", is(eventDto.getViews())))
                .andExpect(jsonPath("$.state", is(eventDto.getState())));
    }

    @Test
    void updateIdNull() throws Exception {
        UpdateEventAdminRequest request = UpdateEventAdminRequest.builder()
                .title("updateTitle")
                .annotation("updateAnnotation")
                .category(1)
                .eventDate("2011-11-11 11:11")
                .description("updateDescription")
                .requestModeration(true)
                .participantLimit(100)
                .paid(true)
                .stateAction("PUBLISH_EVENT")
                .build();
        mvc.perform(patch("/admin/events/a")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}