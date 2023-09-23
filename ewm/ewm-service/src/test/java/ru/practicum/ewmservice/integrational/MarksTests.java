package ru.practicum.ewmservice.integrational;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.service.marks.MarkService;

import javax.transaction.Transactional;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class MarksTests {

    private final MarkService service;

    @Test
    void save() {
        Assertions.assertEquals("MarkDto(userId=6, eventId=2, mark=1)",
                service.save(6, 2, 1).toString());
    }

}