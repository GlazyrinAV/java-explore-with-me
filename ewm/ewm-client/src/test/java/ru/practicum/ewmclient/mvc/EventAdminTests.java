package ru.practicum.ewmclient.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmclient.client.event.EventAdminClient;
import ru.practicum.ewmclient.controller.event.EventAdminController;

@WebMvcTest(controllers = EventAdminController.class)
class EventAdminTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EventAdminClient client;

    @Autowired
    private MockMvc mvc;

}