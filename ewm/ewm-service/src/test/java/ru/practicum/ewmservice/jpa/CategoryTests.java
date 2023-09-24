package ru.practicum.ewmservice.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.repository.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CategoryTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CategoryRepository repository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void findByName() {
        Category category = repository.findByName("category2");
        Assertions.assertEquals("category2", category.getName());
        Assertions.assertEquals(2, category.getId());
    }

    @Test
    void findByNameNull() {
        Category category = repository.findByName("category4");
        Assertions.assertNull(category);
    }

    @Test
    void findDuplicateTrue() {
        Category category = repository.findDuplicate("category1", 2);
        Assertions.assertEquals("category1", category.getName());
        Assertions.assertEquals(1, category.getId());
    }

    @Test
    void findDuplicateFalse() {
        Category category = repository.findDuplicate("category5", 2);
        Assertions.assertNull(category);
    }

}