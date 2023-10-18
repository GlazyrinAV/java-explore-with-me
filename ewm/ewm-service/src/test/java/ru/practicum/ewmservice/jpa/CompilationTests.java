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
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.repository.CompilationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CompilationTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CompilationRepository repository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void findAllByPinnedTrue() {
        Pageable page = PageRequest.of(0, 10);
        Collection<Compilation> compilations = repository.findAllByPinned(true, page).getContent();
        List<Compilation> result = new ArrayList<>(compilations);

        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals("Compilation title 1", result.get(0).getTitle());
        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals("[Event{id=2, title='title2', annotation='annotation of event 2', " +
                        "initiator=User(id=1, name=admin, email=admin@admin.ru, role=admin, " +
                        "password=$2y$10$df2iys6d9EKkj6UneWI34egpQdvKYI8G77OvZE6FeKC07fYsG/pmC), " +
                        "category=Category(id=1, name=category1), createdOn=2001-01-02T10:00, " +
                        "description='description of event2', eventDate=2001-10-02T10:00, " +
                        "location=Location(id=1, lat=10.1, lon=20.2), paid=false, participantLimit=50, " +
                        "publishedOn=2001-01-02T11:00, requestModeration=true, state=PUBLISHED, views=0}]",
                result.get(0).getEvents().toString());
        Assertions.assertTrue(result.get(0).isPinned());
    }

    @Test
    void findAllByPinnedFalse() {
        Pageable page = PageRequest.of(0, 10);
        Collection<Compilation> compilations = repository.findAllByPinned(false, page).getContent();
        List<Compilation> result = new ArrayList<>(compilations);

        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals("Compilation title 2", result.get(0).getTitle());
        Assertions.assertEquals(2, result.get(0).getId());
        Assertions.assertEquals("[]", result.get(0).getEvents().toString());
        Assertions.assertFalse(result.get(0).isPinned());
    }

}