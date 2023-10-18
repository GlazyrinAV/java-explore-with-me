package ru.practicum.ewmservice.integrational;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.service.event.EventPublicService;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EventPublicTests {

    private final EventPublicService service;

    @Test
    void findALl() {
        Assertions.assertEquals("[EventDto(id=3, title=title3, annotation=annotation of event 3, " +
                        "initiator=UserDto(id=3, name=USER2, email=email2@mail.ru, password=null, role=null, mark=null), " +
                        "confirmedRequests=0, category=CategoryDto(id=2, name=category2), createdOn=2001-01-03 10:00:00, " +
                        "description=description of event3, eventDate=2001-10-03 10:00:00, " +
                        "location=LocationDto(id=0, lat=20.2, lon=30.3), paid=false, participantLimit=0, " +
                        "publishedOn=2001-01-03 11:00:00, requestModeration=true, state=PUBLISHED, stateAction=null, " +
                        "views=0, mark=null)]",
                service.findAll(0, 10, "description", List.of(2, 3), false,
                        "2000-11-11 11:11:11", "2002-11-11 11:11:11", false, "VIEWS").toString());
    }

}