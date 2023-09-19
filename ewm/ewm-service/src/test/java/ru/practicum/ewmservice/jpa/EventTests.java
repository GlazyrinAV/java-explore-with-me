package ru.practicum.ewmservice.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EventTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private EventRepository repository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void findByInitiatorId() {
        Pageable page = PageRequest.of(0, 10);
        List<Event> result = new ArrayList<>(repository.findByInitiatorId(1, page).getContent());

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("Event{id=1, title='title1', annotation='annotation of event 1', " +
                "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                "createdOn=2001-01-01T10:00, description='description of event1', eventDate=2001-10-01T10:00, " +
                "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=100, publishedOn=null, " +
                "requestModeration=true, state=PENDING}", result.get(0).toString());

        Assertions.assertEquals("Event{id=2, title='title2', annotation='annotation of event 2', " +
                "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                "createdOn=2001-01-02T10:00, description='description of event2', eventDate=2001-10-02T10:00, " +
                "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED}", result.get(1).toString());
    }

    @Test
    void findByCategoryId() {
        List<Event> result = new ArrayList<>(repository.findByCategoryId(2));

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("Event{id=3, title='title3', annotation='annotation of event 3', " +
                "initiator=User(id=3, name=USER3, email=email3@mail.ru), category=Category(id=2, name=category2), " +
                "createdOn=2001-01-03T10:00, description='description of event3', eventDate=2001-10-03T10:00, " +
                "location=Location(id=2, lat=20.2, lon=30.3), paid=false, participantLimit=0, " +
                "publishedOn=2001-01-03T11:00, requestModeration=true, state=PUBLISHED}", result.get(0).toString());

        Assertions.assertEquals("Event{id=4, title='title4', annotation='annotation of event 4', " +
                "initiator=User(id=4, name=USER4, email=email4@mail.ru), category=Category(id=2, name=category2), " +
                "createdOn=2001-01-04T10:00, description='description of event4', eventDate=2024-10-04T10:00, " +
                "location=Location(id=2, lat=20.2, lon=30.3), paid=true, participantLimit=10, " +
                "publishedOn=2001-01-04T11:00, requestModeration=false, state=PUBLISHED}", result.get(1).toString());
    }

    @Test
    void findAllWithCriteria() {
        List<Event> result = new ArrayList<>(repository.findAllWithCriteria(List.of(1, 3, 99)));

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("Event{id=1, title='title1', annotation='annotation of event 1', " +
                "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                "createdOn=2001-01-01T10:00, description='description of event1', eventDate=2001-10-01T10:00, " +
                "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=100, publishedOn=null, " +
                "requestModeration=true, state=PENDING}", result.get(0).toString());

        Assertions.assertEquals("Event{id=3, title='title3', annotation='annotation of event 3', " +
                "initiator=User(id=3, name=USER3, email=email3@mail.ru), category=Category(id=2, name=category2), " +
                "createdOn=2001-01-03T10:00, description='description of event3', eventDate=2001-10-03T10:00, " +
                "location=Location(id=2, lat=20.2, lon=30.3), paid=false, participantLimit=0, " +
                "publishedOn=2001-01-03T11:00, requestModeration=true, state=PUBLISHED}", result.get(1).toString());
    }

    @Test
    void findAllAdminWithCriteria() {
        Pageable page = PageRequest.of(0, 10);
        List<Event> result = repository.findAllAdminWithCriteria(page, List.of(1, 2, 99), List.of("PUBLISHED"),
                List.of(1, 2, 3, 99), "2001-01-01 00:00", "2001-12-01 00:00").getContent();

        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals("Event{id=2, title='title2', annotation='annotation of event 2', " +
                "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                "createdOn=2001-01-02T10:00, description='description of event2', eventDate=2001-10-02T10:00, " +
                "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED}", result.get(0).toString());
    }

    @Test
    void findAllPublicWithCriteria() {
        Pageable page = PageRequest.of(0, 10);
        List<Event> result = repository.findAllPublicWithCriteria(page, "description", List.of(1, 2, 99), false,
                "2000-01-01 00:00", "2001-12-01 00:00", false).getContent();

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("Event{id=2, title='title2', annotation='annotation of event 2', " +
                "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                "createdOn=2001-01-02T10:00, description='description of event2', eventDate=2001-10-02T10:00, " +
                "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED}", result.get(0).toString());

        Assertions.assertEquals("Event{id=3, title='title3', annotation='annotation of event 3', " +
                "initiator=User(id=3, name=USER3, email=email3@mail.ru), category=Category(id=2, name=category2), " +
                "createdOn=2001-01-03T10:00, description='description of event3', eventDate=2001-10-03T10:00, " +
                "location=Location(id=2, lat=20.2, lon=30.3), paid=false, participantLimit=0, " +
                "publishedOn=2001-01-03T11:00, requestModeration=true, state=PUBLISHED}", result.get(1).toString());
    }

    @Test
    void findAllPublicWithCriteriaSortDate() {
        Pageable page = PageRequest.of(0, 10);
        List<Event> result = repository.findAllPublicWithCriteriaSortDate(page, "description", List.of(1, 2, 99),
                false, "2000-01-01 00:00", "2001-12-01 00:00", false).getContent();

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("Event{id=3, title='title3', annotation='annotation of event 3', " +
                "initiator=User(id=3, name=USER3, email=email3@mail.ru), category=Category(id=2, name=category2), " +
                "createdOn=2001-01-03T10:00, description='description of event3', eventDate=2001-10-03T10:00, " +
                "location=Location(id=2, lat=20.2, lon=30.3), paid=false, participantLimit=0, " +
                "publishedOn=2001-01-03T11:00, requestModeration=true, state=PUBLISHED}", result.get(0).toString());

        Assertions.assertEquals("Event{id=2, title='title2', annotation='annotation of event 2', " +
                "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                "createdOn=2001-01-02T10:00, description='description of event2', eventDate=2001-10-02T10:00, " +
                "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED}", result.get(1).toString());
    }

    @Test
    void findAllPublicWithCriteriaSortMark() {
        Pageable page = PageRequest.of(0, 10);
        List<Event> result = repository.findAllPublicWithCriteriaSortMark(page, "description", List.of(1, 2, 99),
                false, "2000-01-01 00:00", "2001-12-01 00:00", false).getContent();

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("Event{id=2, title='title2', annotation='annotation of event 2', " +
                "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                "createdOn=2001-01-02T10:00, description='description of event2', eventDate=2001-10-02T10:00, " +
                "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED}", result.get(0).toString());

        Assertions.assertEquals("Event{id=3, title='title3', annotation='annotation of event 3', " +
                "initiator=User(id=3, name=USER3, email=email3@mail.ru), category=Category(id=2, name=category2), " +
                "createdOn=2001-01-03T10:00, description='description of event3', eventDate=2001-10-03T10:00, " +
                "location=Location(id=2, lat=20.2, lon=30.3), paid=false, participantLimit=0, " +
                "publishedOn=2001-01-03T11:00, requestModeration=true, state=PUBLISHED}", result.get(1).toString());
    }

}