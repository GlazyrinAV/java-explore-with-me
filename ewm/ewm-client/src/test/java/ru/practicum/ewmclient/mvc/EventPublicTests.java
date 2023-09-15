package ru.practicum.ewmclient.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmclient.controller.event.EventPublicController;

@WebMvcTest(controllers = EventPublicController.class)
class EventPublicTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EventPublicController controller;

    @Autowired
    private MockMvc mvc;

}