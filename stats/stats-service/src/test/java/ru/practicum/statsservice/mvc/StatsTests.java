package ru.practicum.statsservice.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.statsservice.controller.StatsController;


@WebMvcTest(controllers = StatsController.class)
class StatsTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    StatsController controller;

    @Autowired
    private MockMvc mvc;

}