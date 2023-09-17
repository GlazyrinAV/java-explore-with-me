package ru.practicum.ewmclient.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmclient.client.requests.ParticipationRequestsPrivateClient;
import ru.practicum.ewmclient.controller.requests.ParticipationRequestsPrivateController;

@WebMvcTest(controllers = ParticipationRequestsPrivateController.class)
class ParticipationRequestsPrivateTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ParticipationRequestsPrivateClient client;

    @Autowired
    private MockMvc mvc;

}