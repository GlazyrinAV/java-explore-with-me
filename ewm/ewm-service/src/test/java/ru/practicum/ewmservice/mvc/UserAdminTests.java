package ru.practicum.ewmservice.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmservice.controller.user.UserAdminController;
import ru.practicum.ewmclient.model.UserDto;
import ru.practicum.ewmservice.service.user.UserAdminService;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserAdminController.class)
class UserAdminTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserAdminService service;

    @Autowired
    private MockMvc mvc;

    @Test
    void saveNormal() throws Exception {
        UserDto dto = UserDto.builder()
                .name("testName")
                .email("email@test.ru")
                .build();
        UserDto fromDto = UserDto.builder()
                .id(1)
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
        when(service.save(any()))
                .thenReturn(fromDto);
        mvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(fromDto.getId())))
                .andExpect(jsonPath("$.name", is(fromDto.getName())))
                .andExpect(jsonPath("$.email", is(fromDto.getEmail())));
    }

    @Test
    void findAllNormal() throws Exception {
        mvc.perform(get("/admin/users")
                        .param("from", "1")
                        .param("size", "2")
                        .param("ids", "3")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutFrom() throws Exception {
        mvc.perform(get("/admin/users")
                        .param("size", "2")
                        .param("ids", "3")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutSize() throws Exception {
        mvc.perform(get("/admin/users")
                        .param("from", "1")
                        .param("ids", "3")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutIds() throws Exception {
        mvc.perform(get("/admin/users")
                        .param("from", "1")
                        .param("size", "2")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findAllNormalWithoutAll() throws Exception {
        mvc.perform(get("/admin/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void removeNormal() throws Exception {
        mvc.perform(delete("/admin/users/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void removeUserIdNull() throws Exception {
        mvc.perform(delete("/admin/users/a")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}