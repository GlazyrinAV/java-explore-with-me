package ru.practicum.ewmclient.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmclient.client.user.UserAdminClient;
import ru.practicum.ewmclient.model.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserAdminTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserAdminClient client;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "admin")
    void saveNormal() throws Exception {
        String cred = "admin:admin";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        UserDto dto = UserDto.builder()
                .name("testName")
                .email("email@test.ru")
                .build();
        UserDto fromDto = UserDto.builder()
                .id(1)
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(fromDto, HttpStatus.CREATED);
        when(client.save(any(), any()))
                .thenReturn(response);
        mvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(fromDto.getId())))
                .andExpect(jsonPath("$.name", is(fromDto.getName())))
                .andExpect(jsonPath("$.email", is(fromDto.getEmail())));
    }

    @Test
    void saveNameBlank() throws Exception {
        UserDto dto = UserDto.builder()
                .name("")
                .email("email@test.ru")
                .build();
        mvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveNameNull() throws Exception {
        UserDto dto = UserDto.builder()
                .email("email@test.ru")
                .build();
        mvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveEmailBlank() throws Exception {
        UserDto dto = UserDto.builder()
                .name("testName")
                .email("")
                .build();
        mvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveEmailNull() throws Exception {
        UserDto dto = UserDto.builder()
                .name("testName")
                .build();
        mvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveEmailWrongFormat() throws Exception {
        UserDto dto = UserDto.builder()
                .name("testName")
                .email("wrongFormat")
                .build();
        mvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "admin")
    void findAllNormal() throws Exception {
        String cred = "admin:admin";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/admin/users")
                        .param("from", "1")
                        .param("size", "2")
                        .param("ids", "3")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "admin")
    void findAllNormalWithoutFrom() throws Exception {
        String cred = "admin:admin";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/admin/users")
                        .param("size", "2")
                        .param("ids", "3")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "admin")
    void findAllNormalWithoutSize() throws Exception {
        String cred = "admin:admin";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/admin/users")
                        .param("from", "1")
                        .param("ids", "3")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "admin")
    void findAllNormalWithoutIds() throws Exception {
        String cred = "admin:admin";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/admin/users")
                        .param("from", "1")
                        .param("size", "2")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "admin")
    void findAllNormalWithoutAll() throws Exception {
        String cred = "admin:admin";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(get("/admin/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "admin")
    void removeNormal() throws Exception {
        String cred = "admin:admin";
        String auth = "Basic " + Base64.getEncoder().encodeToString(cred.getBytes());
        mvc.perform(delete("/admin/users/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", auth)
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