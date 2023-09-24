package ru.practicum.ewmservice.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.model.ParticipationRequest;
import ru.practicum.ewmservice.model.RequestStatus;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ParticipationRequestsTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ParticipationRequestsRepository repository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void findByEventId() {
        Collection<ParticipationRequest> requests = repository.findByEventId(2);
        List<ParticipationRequest> result = new ArrayList<>(requests);

        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals("User(id=2, name=USER2, email=email2@mail.ru)", result.get(0).getRequester().toString());
        Assertions.assertEquals("Event{id=2, title='title2', annotation='annotation of event 2', " +
                "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                "createdOn=2001-01-02T10:00, description='description of event2', eventDate=2001-10-02T10:00, " +
                "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED}",
                result.get(0).getEvent().toString());
        Assertions.assertEquals("2001-01-04T10:00", result.get(0).getCreated().toString());
        Assertions.assertEquals(RequestStatus.PENDING, result.get(0).getStatus());

        Assertions.assertEquals(2, result.get(1).getId());
        Assertions.assertEquals("User(id=3, name=USER3, email=email3@mail.ru)", result.get(1).getRequester().toString());
        Assertions.assertEquals("Event{id=2, title='title2', annotation='annotation of event 2', " +
                        "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                        "createdOn=2001-01-02T10:00, description='description of event2', eventDate=2001-10-02T10:00, " +
                        "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                        "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED}",
                result.get(1).getEvent().toString());
        Assertions.assertEquals("2001-01-04T10:00", result.get(1).getCreated().toString());
        Assertions.assertEquals(RequestStatus.CONFIRMED, result.get(1).getStatus());

        Assertions.assertEquals(3, result.get(2).getId());
        Assertions.assertEquals("User(id=6, name=USER6, email=email6@mail.ru)", result.get(2).getRequester().toString());
        Assertions.assertEquals("Event{id=2, title='title2', annotation='annotation of event 2', " +
                        "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                        "createdOn=2001-01-02T10:00, description='description of event2', eventDate=2001-10-02T10:00, " +
                        "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                        "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED}",
                result.get(2).getEvent().toString());
        Assertions.assertEquals("2001-01-04T10:00", result.get(2).getCreated().toString());
        Assertions.assertEquals(RequestStatus.CONFIRMED, result.get(2).getStatus());
    }

    @Test
    void findConfirmedRequests() {
        int result = repository.findConfirmedRequests(2);
        Assertions.assertEquals(2, result);
    }

    @Test
    void findByRequesterId() {
        Collection<ParticipationRequest> requests = repository.findByRequesterId(2);
        List<ParticipationRequest> result = new ArrayList<>(requests);

        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals("User(id=2, name=USER2, email=email2@mail.ru)", result.get(0).getRequester().toString());
        Assertions.assertEquals("Event{id=2, title='title2', annotation='annotation of event 2', " +
                        "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                        "createdOn=2001-01-02T10:00, description='description of event2', eventDate=2001-10-02T10:00, " +
                        "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                        "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED}",
                result.get(0).getEvent().toString());
        Assertions.assertEquals("2001-01-04T10:00", result.get(0).getCreated().toString());
        Assertions.assertEquals(RequestStatus.PENDING, result.get(0).getStatus());
    }

    @Test
    void existsByRequesterIdAndEventIdTrue() {
        boolean result = repository.existsByRequesterIdAndEventId(2, 2);
        Assertions.assertTrue(result);
    }

    @Test
    void existsByRequesterIdAndEventIdFalse() {
        boolean result = repository.existsByRequesterIdAndEventId(1, 2);
        Assertions.assertFalse(result);
    }

    @Test
    void findRequestByUserForMarks() {
        ParticipationRequest result = repository.findRequestByUserForMarks(3, 2);

        Assertions.assertEquals(2, result.getId());
        Assertions.assertEquals("User(id=3, name=USER3, email=email3@mail.ru)", result.getRequester().toString());
        Assertions.assertEquals("Event{id=2, title='title2', annotation='annotation of event 2', " +
                        "initiator=User(id=1, name=USER1, email=email1@mail.ru), category=Category(id=1, name=category1), " +
                        "createdOn=2001-01-02T10:00, description='description of event2', eventDate=2001-10-02T10:00, " +
                        "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                        "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED}",
                result.getEvent().toString());
        Assertions.assertEquals("2001-01-04T10:00", result.getCreated().toString());
        Assertions.assertEquals(RequestStatus.CONFIRMED, result.getStatus());
    }

}