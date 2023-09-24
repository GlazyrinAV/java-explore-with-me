package ru.practicum.ewmservice.integrational;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmservice.service.categories.CategoryPublicService;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CategoryPublicTests {

    private final CategoryPublicService service;

    @Test
    void findALL() {
        Assertions.assertEquals("[CategoryDto(id=1, name=category1), CategoryDto(id=2, name=category2), CategoryDto(id=3, name=category3)]",
                service.findAll(0, 10).toString());
    }

    @Test
    void findById() {
        Assertions.assertEquals("CategoryDto(id=2, name=category2)", service.findById(2).toString());
    }

}