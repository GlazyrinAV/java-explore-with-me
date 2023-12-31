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
import ru.practicum.ewmclient.client.compilation.CompilationAdminClient;
import ru.practicum.ewmclient.controller.compilation.CompilationAdminController;
import ru.practicum.ewmclient.model.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        UserDto userDto = UserDto.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        EventDto eventDto = EventDto.builder()
                .id(1)
                .eventDate("2011-11-11 11:11")
                .annotation("testAnnotation")
                .category(categoryDto)
                .confirmedRequests(10)
                .createdOn("2011-10-10 10:10")
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(userDto)
                .location(locationDto)
                .requestModeration(true)
                .state("PUBLISHED")
                .publishedOn("2011-10-11 11:11")
                .views(10)
                .build();
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1)
                .pinned(true)
                .events(List.of(eventDto))
                .title("testCompilationTitle")
                .build();
        NewCompilationDto dto = NewCompilationDto.builder()
                .events(List.of(1))
                .pinned(true)
                .title("test CompilationTitle")
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(compilationDto, HttpStatus.CREATED);
        when(client.save(dto))
                .thenReturn(response);
        mvc.perform(post("/admin/compilations")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(compilationDto.getId())))
                .andExpect(jsonPath("$.pinned", is(compilationDto.isPinned())))
                .andExpect(jsonPath("$.title", is(compilationDto.getTitle())))
                .andExpect(jsonPath("$.events.size()", is(compilationDto.getEvents().size())));
    }

    @Test
    void saveEventNull() throws Exception {
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1)
                .pinned(true)
                .title("testCompilationTitle")
                .build();
        NewCompilationDto dto = NewCompilationDto.builder()
                .pinned(true)
                .title("test CompilationTitle")
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(compilationDto, HttpStatus.CREATED);
        when(client.save(dto))
                .thenReturn(response);
        mvc.perform(post("/admin/compilations")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(compilationDto.getId())))
                .andExpect(jsonPath("$.pinned", is(compilationDto.isPinned())))
                .andExpect(jsonPath("$.title", is(compilationDto.getTitle())));
    }

    @Test
    void saveTitleNull() throws Exception {
        NewCompilationDto dto = NewCompilationDto.builder()
                .pinned(true)
                .build();
        mvc.perform(post("/admin/compilations")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveTitleBlank() throws Exception {
        NewCompilationDto dto = NewCompilationDto.builder()
                .title("")
                .pinned(true)
                .build();
        mvc.perform(post("/admin/compilations")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateNormal() throws Exception {
        UpdateCompilationRequest request = UpdateCompilationRequest.builder()
                .events(List.of(1))
                .pinned(true)
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
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        EventDto eventDto = EventDto.builder()
                .id(1)
                .eventDate("2011-11-11 11:11")
                .annotation("testAnnotation")
                .category(categoryDto)
                .confirmedRequests(10)
                .createdOn("2011-10-10 10:10")
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(userDto)
                .location(locationDto)
                .requestModeration(true)
                .state("PUBLISHED")
                .publishedOn("2011-10-11 11:11")
                .views(10)
                .build();
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1)
                .pinned(true)
                .events(List.of(eventDto))
                .title("updateTitle")
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(compilationDto, HttpStatus.CREATED);
        when(client.update(any(), anyInt()))
                .thenReturn(response);
        mvc.perform(patch("/admin/compilations/1")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(compilationDto.getId())))
                .andExpect(jsonPath("$.pinned", is(compilationDto.isPinned())))
                .andExpect(jsonPath("$.title", is(compilationDto.getTitle())))
                .andExpect(jsonPath("$.events.size()", is(compilationDto.getEvents().size())));
    }

    @Test
    void updateEventNull() throws Exception {
        UpdateCompilationRequest request = UpdateCompilationRequest.builder()
                .pinned(true)
                .title("updateTitle")
                .build();
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1)
                .pinned(true)
                .title("updateTitle")
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(compilationDto, HttpStatus.CREATED);
        when(client.update(any(), anyInt()))
                .thenReturn(response);
        mvc.perform(patch("/admin/compilations/1")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(compilationDto.getId())))
                .andExpect(jsonPath("$.pinned", is(compilationDto.isPinned())))
                .andExpect(jsonPath("$.title", is(compilationDto.getTitle())));
    }

    @Test
    void removeNormal() throws Exception {
        mvc.perform(delete("/admin/compilations/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void removeIdNull() throws Exception {
        mvc.perform(delete("/admin/compilations/")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}