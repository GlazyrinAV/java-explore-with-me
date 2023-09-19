package ru.practicum.statsservice.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.statsclient.dto.ViewStatsDto;
import ru.practicum.statsservice.repository.StatsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StatsTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private StatsRepository repository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void findAllStatsWithUris() {
        Collection<ViewStatsDto> viewStatsDto = repository.findAllStatsWithUris("2001-01-01 10:01:00", "2001-02-01 10:10:00", List.of("/events/1", "/events/2"));
        List<ViewStatsDto> result = new ArrayList<>(viewStatsDto);

        Assertions.assertEquals("/events/1", result.get(0).getUri());
        Assertions.assertEquals("ewm-main-service", result.get(0).getApp());
        Assertions.assertEquals(3, result.get(0).getHits());

        Assertions.assertEquals("/events/2", result.get(1).getUri());
        Assertions.assertEquals("ewm-main-service", result.get(1).getApp());
        Assertions.assertEquals(1, result.get(1).getHits());
    }

    @Test
    void findAllStatsWithoutUris() {
        Collection<ViewStatsDto> viewStatsDto = repository.findAllStatsWithoutUris("2001-01-01 10:01:00", "2001-02-01 10:10:00");
        List<ViewStatsDto> result = new ArrayList<>(viewStatsDto);

        Assertions.assertEquals("/events/1", result.get(0).getUri());
        Assertions.assertEquals("ewm-main-service", result.get(0).getApp());
        Assertions.assertEquals(3, result.get(0).getHits());

        Assertions.assertEquals("/events", result.get(1).getUri());
        Assertions.assertEquals("ewm-main-service", result.get(1).getApp());
        Assertions.assertEquals(2, result.get(1).getHits());

        Assertions.assertEquals("/events/2", result.get(2).getUri());
        Assertions.assertEquals("ewm-main-service", result.get(2).getApp());
        Assertions.assertEquals(1, result.get(2).getHits());
    }

    @Test
    void findAllUniqueStatsWithUris() {
        Collection<ViewStatsDto> viewStatsDto = repository.findAllUniqueStatsWithUris("2001-01-01 10:01:00", "2001-02-01 10:10:00", List.of("/events/1", "/events/2"));
        List<ViewStatsDto> result = new ArrayList<>(viewStatsDto);

        Assertions.assertEquals("/events/1", result.get(0).getUri());
        Assertions.assertEquals("ewm-main-service", result.get(0).getApp());
        Assertions.assertEquals(2, result.get(0).getHits());

        Assertions.assertEquals("/events/2", result.get(1).getUri());
        Assertions.assertEquals("ewm-main-service", result.get(1).getApp());
        Assertions.assertEquals(1, result.get(1).getHits());
    }

    @Test
    void findAllUniqueStatsWithoutUris() {
        Collection<ViewStatsDto> viewStatsDto = repository.findAllUniqueStatsWithoutUris("2001-01-01 10:01:00", "2001-02-01 10:10:00");
        List<ViewStatsDto> result = new ArrayList<>(viewStatsDto);

        Assertions.assertEquals("/events", result.get(0).getUri());
        Assertions.assertEquals("ewm-main-service", result.get(0).getApp());
        Assertions.assertEquals(2, result.get(0).getHits());

        Assertions.assertEquals("/events/1", result.get(1).getUri());
        Assertions.assertEquals("ewm-main-service", result.get(1).getApp());
        Assertions.assertEquals(2, result.get(1).getHits());

        Assertions.assertEquals("/events/2", result.get(2).getUri());
        Assertions.assertEquals("ewm-main-service", result.get(2).getApp());
        Assertions.assertEquals(1, result.get(2).getHits());
    }

    @Test
    void findStatsForEwmWithHits2() {
        Assertions.assertEquals(2, repository.findStatsForEwm("/events/1"));
    }

    @Test
    void findStatsForEwmWithHits0() {
        Assertions.assertEquals(0, repository.findStatsForEwm("/events/3"));
    }

}