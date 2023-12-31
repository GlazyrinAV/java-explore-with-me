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
import ru.practicum.ewmclient.client.categories.CategoryPublicClient;
import ru.practicum.ewmclient.controller.categories.CategoryPublicController;
import ru.practicum.ewmclient.model.CategoryDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryPublicController.class)
class CategoryPublicTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CategoryPublicClient client;

    @Autowired
    private MockMvc mvc;

    @Test
    void findAllCategoryNormal() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(List.of(dto), HttpStatus.CREATED);
        when(client.findAll(anyInt(), anyInt()))
                .thenReturn(response);
        mvc.perform(get("/categories")
                        .param("from", "1")
                        .param("size", "2")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id", is(dto.getId())))
                .andExpect(jsonPath("$.[0].name", is(dto.getName())));
    }

    @Test
    void findAllCategoryNormalWithoutFrom() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(List.of(dto), HttpStatus.CREATED);
        when(client.findAll(anyInt(), anyInt()))
                .thenReturn(response);
        mvc.perform(get("/categories")
                        .param("size", "2")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id", is(dto.getId())))
                .andExpect(jsonPath("$.[0].name", is(dto.getName())));
    }

    @Test
    void findAllCategoryNormalWithoutSize() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(List.of(dto), HttpStatus.CREATED);
        when(client.findAll(anyInt(), anyInt()))
                .thenReturn(response);
        mvc.perform(get("/categories")
                        .param("from", "2")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id", is(dto.getId())))
                .andExpect(jsonPath("$.[0].name", is(dto.getName())));
    }

    @Test
    void findAllCategoryNormalWithoutFromAndSize() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(List.of(dto), HttpStatus.CREATED);
        when(client.findAll(anyInt(), anyInt()))
                .thenReturn(response);
        mvc.perform(get("/categories")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id", is(dto.getId())))
                .andExpect(jsonPath("$.[0].name", is(dto.getName())));
    }

    @Test
    void findByIdCategoryNormal() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(dto, HttpStatus.CREATED);
        when(client.findById(anyInt()))
                .thenReturn(response);
        mvc.perform(get("/categories/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(dto.getId())))
                .andExpect(jsonPath("$.name", is(dto.getName())));
    }

}