package ru.practicum.ewmservice.integrational;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.service.compilation.CompilationPublicService;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class CompilationPublicTests {

    private final CompilationPublicService service;

    @Test
    void findAll() {
        Assertions.assertEquals("[CompilationDto(id=2, title=Compilation title 2, pinned=false, events=[])]",
                service.findAll(false, 0, 10).toString());
    }

}