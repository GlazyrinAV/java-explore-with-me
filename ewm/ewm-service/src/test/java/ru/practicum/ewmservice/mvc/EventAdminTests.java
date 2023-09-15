package ru.practicum.ewmservice.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmservice.controller.event.EventAdminController;

@WebMvcTest(controllers = EventAdminController.class)
class EventAdminTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EventAdminController controller;

    @Autowired
    private MockMvc mvc;

}