package ru.practicum.ewmservice.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.model.Location;
import ru.practicum.ewmservice.repository.LocationRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LocationTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private LocationRepository repository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void findByLatAndLon() {
        Location location = repository.findByLatAndLon(20.20, 30.30);
        Assertions.assertEquals(2, location.getId());
        Assertions.assertEquals(20.20, location.getLat());
        Assertions.assertEquals(30.30, location.getLon());
    }

    @Test
    void findByLatAndLonNull() {
        Location location = repository.findByLatAndLon(90.20, 90.30);
        Assertions.assertNull(location);
    }

}