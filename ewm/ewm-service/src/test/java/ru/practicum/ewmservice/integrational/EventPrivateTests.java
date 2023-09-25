package ru.practicum.ewmservice.integrational;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.service.event.EventPrivateService;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EventPrivateTests {

    private final EventPrivateService service;

    @Test
    void findAllByUserId() {
        Assertions.assertEquals("[EventDto(id=1, title=title1, annotation=annotation of event 1, " +
                        "initiator=UserDto(id=1, name=USER1, email=email1@mail.ru, mark=5.0), confirmedRequests=0, " +
                        "category=CategoryDto(id=1, name=category1), createdOn=2001-01-01 10:00:00, " +
                        "description=description of event1, eventDate=2001-10-01 10:00:00, " +
                        "location=LocationDto(id=0, lat=10.1, lon=20.2), paid=false, participantLimit=100, " +
                        "publishedOn=null, requestModeration=true, state=PENDING, stateAction=null, views=0, mark=null), " +
                        "EventDto(id=2, title=title2, annotation=annotation of event 2, " +
                        "initiator=UserDto(id=1, name=USER1, email=email1@mail.ru, mark=5.0), confirmedRequests=2, " +
                        "category=CategoryDto(id=1, name=category1), createdOn=2001-01-02 10:00:00, " +
                        "description=description of event2, eventDate=2001-10-02 10:00:00, " +
                        "location=LocationDto(id=0, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                        "publishedOn=2001-01-02 11:00:00, requestModeration=true, state=PUBLISHED, stateAction=null, " +
                        "views=0, mark=5.0)]",
                service.findAllByUserId(1, 0, 10).toString());
    }

}