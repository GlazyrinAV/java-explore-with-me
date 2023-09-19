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
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository repository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void findByName() {
        User user = repository.findByName("USER5");
        Assertions.assertEquals(5, user.getId());
        Assertions.assertEquals("USER5", user.getName());
        Assertions.assertEquals("email5@mail.ru", user.getEmail());
    }

    @Test
    void findByNameNull() {
        User user = repository.findByName("USER99");
        Assertions.assertNull(user);
    }

    @Test
    void findAllAdminWithCriteria() {
        Pageable page = PageRequest.of(0, 10);
        Collection<User> users = repository.findAllAdminWithCriteria(page, List.of(1, 2, 99)).getContent();
        List<User> result = new ArrayList<>(users);

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals("USER1", result.get(0).getName());
        Assertions.assertEquals("email1@mail.ru", result.get(0).getEmail());

        Assertions.assertEquals(2, result.get(1).getId());
        Assertions.assertEquals("USER2", result.get(1).getName());
        Assertions.assertEquals("email2@mail.ru", result.get(1).getEmail());
    }

    @Test
    void findAllAdminWithCriteriaUrisNull() {
        Pageable page = PageRequest.of(0, 10);
        Collection<User> users = repository.findAllAdminWithCriteria(page, null).getContent();
        List<User> result = new ArrayList<>(users);

        Assertions.assertEquals(6, result.size());

        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals("USER1", result.get(0).getName());
        Assertions.assertEquals("email1@mail.ru", result.get(0).getEmail());

        Assertions.assertEquals(2, result.get(1).getId());
        Assertions.assertEquals("USER2", result.get(1).getName());
        Assertions.assertEquals("email2@mail.ru", result.get(1).getEmail());

        Assertions.assertEquals(3, result.get(2).getId());
        Assertions.assertEquals("USER3", result.get(2).getName());
        Assertions.assertEquals("email3@mail.ru", result.get(2).getEmail());

        Assertions.assertEquals(4, result.get(3).getId());
        Assertions.assertEquals("USER4", result.get(3).getName());
        Assertions.assertEquals("email4@mail.ru", result.get(3).getEmail());

        Assertions.assertEquals(5, result.get(4).getId());
        Assertions.assertEquals("USER5", result.get(4).getName());
        Assertions.assertEquals("email5@mail.ru", result.get(4).getEmail());

        Assertions.assertEquals(6, result.get(5).getId());
        Assertions.assertEquals("USER6", result.get(5).getName());
        Assertions.assertEquals("email6@mail.ru", result.get(5).getEmail());
    }

}