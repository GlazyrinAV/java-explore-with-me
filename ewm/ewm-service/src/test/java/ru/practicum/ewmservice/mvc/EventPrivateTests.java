package ru.practicum.ewmservice.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmclient.model.*;
import ru.practicum.ewmservice.service.event.EventPrivateService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventPrivateTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EventPrivateService service;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "user")
    void saveNormal() throws Exception {
        String cred = "user:user";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        NewEventDto toDto = NewEventDto.builder()
                .eventDate("2011-11-11 11:11")
                .description("testDescription")
                .title("testTitle")
                .location(locationDto)
                .category(1)
                .participantLimit(100)
                .requestModeration(true)
                .paid(true)
                .annotation("testAnnotation")
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
                .annotation("testAnnotation")
                .category(categoryDto)
                .createdOn("2011-10-10 10:10")
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(100)
                .initiator(userDto)
                .location(locationDto)
                .requestModeration(true)
                .state("PENDING")
                .build();
        when(service.save(any(), anyInt()))
                .thenReturn(eventDto);
        mvc.perform(post("/users/1/events")
                        .content(mapper.writeValueAsString(toDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(eventDto.getId())))
                .andExpect(jsonPath("$.eventDate", is(eventDto.getEventDate())))
                .andExpect(jsonPath("$.annotation", is(eventDto.getAnnotation())))
                .andExpect(jsonPath("$.category.id", is(eventDto.getCategory().getId())))
                .andExpect(jsonPath("$.category.name", is(eventDto.getCategory().getName())))
                .andExpect(jsonPath("$.createdOn", is(eventDto.getCreatedOn())))
                .andExpect(jsonPath("$.paid", is(eventDto.isPaid())))
                .andExpect(jsonPath("$.title", is(eventDto.getTitle())))
                .andExpect(jsonPath("$.description", is(eventDto.getDescription())))
                .andExpect(jsonPath("$.participantLimit", is(eventDto.getParticipantLimit())))
                .andExpect(jsonPath("$.initiator.id", is(eventDto.getInitiator().getId())))
                .andExpect(jsonPath("$.location.id", is(eventDto.getLocation().getId())))
                .andExpect(jsonPath("$.location.lon", is(eventDto.getLocation().getLon())))
                .andExpect(jsonPath("$.location.lat", is(eventDto.getLocation().getLat())))
                .andExpect(jsonPath("$.requestModeration", is(eventDto.isRequestModeration())))
                .andExpect(jsonPath("$.state", is(eventDto.getState())));
    }

    @Test
    void saveUserIdNull() throws Exception {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        NewEventDto toDto = NewEventDto.builder()
                .eventDate("2011-11-11 11:11")
                .description("testDescription")
                .title("testTitle")
                .location(locationDto)
                .category(1)
                .participantLimit(100)
                .requestModeration(true)
                .paid(true)
                .annotation("testAnnotation")
                .build();
        mvc.perform(post("/users/ /events")
                        .content(mapper.writeValueAsString(toDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "user")
    void findAllByUserIdNormal() throws Exception {
        String cred = "user:user";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/users/1/events")
                        .param("from", "1")
                        .param("size", "2")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "user")
    void findAllByUserIdNormalWithoutFrom() throws Exception {
        String cred = "user:user";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/users/1/events")
                        .param("size", "2")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "user")
    void findAllByUserIdNormalWithoutSize() throws Exception {
        String cred = "user:user";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/users/1/events")
                        .param("from", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "user")
    void findAllByUserIdNormalWithoutAll() throws Exception {
        String cred = "user:user";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/users/1/events")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "user")
    void findByIdNormal() throws Exception {
        String cred = "user:user";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/users/1/events/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findByIdUserIdNull() throws Exception {
        mvc.perform(get("/users/ /events/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "user")
    void findRequestsNormal() throws Exception {
        String cred = "user:user";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/users/1/events/1/requests")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findRequestsUserIdNull() throws Exception {
        mvc.perform(get("/users/ /events/1/requests")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void findRequestsEventIdNull() throws Exception {
        mvc.perform(get("/users/1/events/ /requests")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "user")
    void updateNormal() throws Exception {
        String cred = "user:user";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventUserRequest request = UpdateEventUserRequest.builder()
                .eventDate("2011-11-11 11:11")
                .requestModeration(true)
                .annotation("updateAnnotation")
                .category(1)
                .description("updateDescription")
                .location(locationDto)
                .stateAction("PUBLISH_EVENT")
                .paid(true)
                .participantLimit(100)
                .title("updateTitle")
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
                .createdOn("2011-10-10 10:10")
                .paid(true)
                .title("updateTitle")
                .description("updateDescription")
                .participantLimit(100)
                .initiator(userDto)
                .location(locationDto)
                .requestModeration(true)
                .state("PENDING")
                .build();
        when(service.update(any(), anyInt(), anyInt()))
                .thenReturn(eventDto);
        mvc.perform(patch("/users/1/events/1")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(eventDto.getId())))
                .andExpect(jsonPath("$.eventDate", is(eventDto.getEventDate())))
                .andExpect(jsonPath("$.annotation", is(eventDto.getAnnotation())))
                .andExpect(jsonPath("$.category.id", is(eventDto.getCategory().getId())))
                .andExpect(jsonPath("$.category.name", is(eventDto.getCategory().getName())))
                .andExpect(jsonPath("$.createdOn", is(eventDto.getCreatedOn())))
                .andExpect(jsonPath("$.paid", is(eventDto.isPaid())))
                .andExpect(jsonPath("$.title", is(eventDto.getTitle())))
                .andExpect(jsonPath("$.description", is(eventDto.getDescription())))
                .andExpect(jsonPath("$.participantLimit", is(eventDto.getParticipantLimit())))
                .andExpect(jsonPath("$.initiator.id", is(eventDto.getInitiator().getId())))
                .andExpect(jsonPath("$.location.id", is(eventDto.getLocation().getId())))
                .andExpect(jsonPath("$.location.lon", is(eventDto.getLocation().getLon())))
                .andExpect(jsonPath("$.location.lat", is(eventDto.getLocation().getLat())))
                .andExpect(jsonPath("$.requestModeration", is(eventDto.isRequestModeration())))
                .andExpect(jsonPath("$.state", is(eventDto.getState())));
    }

    @Test
    void updateUserIdNull() throws Exception {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventUserRequest request = UpdateEventUserRequest.builder()
                .eventDate("2011-11-11 11:11")
                .requestModeration(true)
                .annotation("updateAnnotation")
                .category(1)
                .description("updateDescription")
                .location(locationDto)
                .stateAction("PUBLISH_EVENT")
                .paid(true)
                .participantLimit(100)
                .title("updateTitle")
                .build();
        mvc.perform(patch("/users/a/events/1")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateEventIdNull() throws Exception {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventUserRequest request = UpdateEventUserRequest.builder()
                .eventDate("2011-11-11 11:11")
                .requestModeration(true)
                .annotation("updateAnnotation")
                .category(1)
                .description("updateDescription")
                .location(locationDto)
                .stateAction("PUBLISH_EVENT")
                .paid(true)
                .participantLimit(100)
                .title("updateTitle")
                .build();
        mvc.perform(patch("/users/1/events/a")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "user")
    void updateRequestsNormal() throws Exception {
        String cred = "user:user";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        EventRequestStatusUpdateRequest request = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1, 2))
                .status("CONFIRMED")
                .build();
        ParticipationRequestDto dto = ParticipationRequestDto.builder()
                .requester(1)
                .event(2)
                .status("CONFIRMED")
                .id(1)
                .created("2011-11-11 11:11")
                .build();
        ParticipationRequestDto dto2 = ParticipationRequestDto.builder()
                .requester(1)
                .event(1)
                .status("REJECTED")
                .id(1)
                .created("2011-11-11 11:11")
                .build();
        EventRequestStatusUpdateResult result = EventRequestStatusUpdateResult.builder()
                .confirmedRequests(List.of(dto))
                .rejectedRequests(List.of(dto2))
                .build();
        when(service.updateRequests(any(), anyInt(), anyInt()))
                .thenReturn(result);
        mvc.perform(patch("/users/1/events/1/requests")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rejectedRequests.size()", is(result.getRejectedRequests().size())))
                .andExpect(jsonPath("$.confirmedRequests.size()", is(result.getConfirmedRequests().size())));
    }

    @Test
    void updateRequestsUserIdNull() throws Exception {
        EventRequestStatusUpdateRequest request = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1, 2))
                .status("CONFIRMED")
                .build();
        mvc.perform(patch("/users/a/events/1/requests")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateRequestsEventIdNull() throws Exception {
        EventRequestStatusUpdateRequest request = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1, 2))
                .status("CONFIRMED")
                .build();
        mvc.perform(patch("/users/1/events/d/requests")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}