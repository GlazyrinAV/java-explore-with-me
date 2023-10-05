package ru.practicum.ewmservice.integrational;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmclient.model.category.CategoryDto;
import ru.practicum.ewmservice.exceptions.exceptions.WrongParameter;
import ru.practicum.ewmservice.service.categories.CategoryAdminService;

import javax.transaction.Transactional;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class CategoryAdminTests {

    private final CategoryAdminService service;

    @Test
    void saveNormal() {
        CategoryDto toDto = CategoryDto.builder()
                .name("testCategory")
                .build();
        Assertions.assertEquals("CategoryDto(id=4, name=testCategory)", service.save(toDto).toString());
    }

    @Test
    void saveWrongName() {
        CategoryDto toDto = CategoryDto.builder()
                .name("category1")
                .build();
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class,
                () -> service.save(toDto));

        Assertions.assertEquals("Категория с таким именем уже существует.", exception.getMessage());
    }

}