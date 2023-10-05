package ru.practicum.ewmservice.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmservice.controller.categories.CategoryAdminController;
import ru.practicum.ewmclient.model.category.CategoryDto;
import ru.practicum.ewmservice.service.categories.CategoryAdminService;

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
    CategoryAdminService service;

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
        when(service.save(dto))
                .thenReturn(fromDto);
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
    void updateCategoryNormal() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .name("updateCategory")
                .build();
        CategoryDto fromDto = CategoryDto.builder()
                .id(1)
                .name(dto.getName())
                .build();
        when(service.update(any(), anyInt()))
                .thenReturn(fromDto);
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