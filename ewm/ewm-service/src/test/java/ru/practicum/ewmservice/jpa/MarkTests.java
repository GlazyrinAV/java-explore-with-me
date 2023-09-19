package ru.practicum.ewmservice.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.repository.MarkRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class MarkTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private MarkRepository repository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void findMarkForEvent() {
        Double mark = repository.findMarkForEvent(2);
        Assertions.assertEquals(5, mark);
    }

    @Test
    void findMarkForEventNull() {
        Double mark = repository.findMarkForEvent(1);
        Assertions.assertNull(mark);
    }

    @Test
    void findMarkForUser() {
        Double mark = repository.findMarkForUser(1);
        Assertions.assertEquals(5, mark);
    }

    @Test
    void findMarkForUserNull() {
        Double mark = repository.findMarkForUser(2);
        Assertions.assertNull(mark);
    }

}