package ru.practicum.ewmclient.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmclient.client.compilation.CompilationAdminClient;
import ru.practicum.ewmclient.controller.compilation.CompilationAdminController;

@WebMvcTest(controllers = CompilationAdminController.class)
class CompilationPublicTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CompilationAdminClient client;

    @Autowired
    private MockMvc mvc;

}