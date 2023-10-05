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
import ru.practicum.ewmclient.client.categories.CategoryAdminClient;
import ru.practicum.ewmclient.controller.categories.CategoryAdminController;
import ru.practicum.ewmclient.model.category.CategoryDto;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryAdminController.class)
class CategoryAdminTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CategoryAdminClient client;

    @Autowired
    private MockMvc mvc;

    @Test
    void saveNewCategoryNormal() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .name("testCategory")
                .build();
        CategoryDto fromDto = CategoryDto.builder()
                .id(1)
                .name(dto.getName())
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(fromDto, HttpStatus.CREATED);
        when(client.save(dto, null))
                .thenReturn(response);
        mvc.perform(post("/admin/categories")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(fromDto.getId())))
                .andExpect(jsonPath("$.name", is(fromDto.getName())));
    }

    @Test
    void saveNewCategoryEmptyName() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .name("")
                .build();
        mvc.perform(post("/admin/categories")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveNewCategoryNameNull() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .build();
        mvc.perform(post("/admin/categories")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateCategoryNormal() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .name("updateCategory")
                .build();
        CategoryDto fromDto = CategoryDto.builder()
                .id(1)
                .name(dto.getName())
                .build();
        ResponseEntity<Object> response = new ResponseEntity<>(fromDto, HttpStatus.OK);
        when(client.update(any(), anyInt(), null))
                .thenReturn(response);
        mvc.perform(patch("/admin/categories/1")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(fromDto.getId())))
                .andExpect(jsonPath("$.name", is(fromDto.getName())));
    }

    @Test
    void updateCategoryEmptyName() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .name("")
                .build();
        mvc.perform(patch("/admin/categories/1")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateCategoryNameNull() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .build();
        mvc.perform(patch("/admin/categories/1")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateCategoryIdNull() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .name("updateCategory")
                .build();
        mvc.perform(patch("/admin/categories/")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void removeCategoryNormal() throws Exception {
        mvc.perform(delete("/admin/categories/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void removeCategoryNegativeNull() throws Exception {
        mvc.perform(delete("/admin/categories/")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}