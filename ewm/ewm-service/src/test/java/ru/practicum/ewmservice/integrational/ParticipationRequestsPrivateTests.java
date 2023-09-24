package ru.practicum.ewmservice.integrational;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.service.requests.ParticipationRequestsPrivateService;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ParticipationRequestsPrivateTests {

    private final ParticipationRequestsPrivateService service;

    @Test
    void findALl() {
        Assertions.assertEquals("[ParticipationRequestDto(id=1, event=2, " +
                        "created=2001-01-04 10:00:00, requester=2, status=PENDING)]",
                service.findAll(2).toString());
    }

}