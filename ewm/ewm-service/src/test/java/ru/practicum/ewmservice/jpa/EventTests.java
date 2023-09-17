package ru.practicum.ewmservice.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.repository.EventRepository;

import javax.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
// @Sql(value = "/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    void findAllWithCriteria() {

    }

    @Test
    void findAllAdminWithCriteria() {

    }

    @Test
    void findAllPublicWithCriteria() {

    }

    @Test
    void findAllPublicWithCriteriaSortDate() {

    }

    @Test
    void findAllPublicWithCriteriaSortMark() {

    }

}