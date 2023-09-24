package ru.practicum.ewmservice.integrational;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.ewmclient.model.NewCompilationDto;
import ru.practicum.ewmservice.service.compilation.CompilationAdminService;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = {"/schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class CompilationAdminTests {

    private final CompilationAdminService service;

    @Test
    void saveNormal() {
        NewCompilationDto newDto = NewCompilationDto.builder()
                .events(List.of())
                .pinned(true)
                .title("testCompilationTitle")
                .build();

        Assertions.assertEquals("CompilationDto(id=3, title=testCompilationTitle, pinned=true, events=[])",
                service.save(newDto).toString());
    }

}