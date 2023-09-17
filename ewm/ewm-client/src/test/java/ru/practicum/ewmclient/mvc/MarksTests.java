package ru.practicum.ewmclient.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmclient.client.marks.MarksClient;
import ru.practicum.ewmclient.controller.marks.MarksController;

@WebMvcTest(controllers = MarksController.class)
class MarksTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    MarksClient client;

    @Autowired
    private MockMvc mvc;

}