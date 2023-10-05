package ru.practicum.ewmservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.category.CategoryDto;
import ru.practicum.ewmclient.model.compilation.CompilationDto;
import ru.practicum.ewmclient.model.event.EventDto;
import ru.practicum.ewmclient.model.location.LocationDto;
import ru.practicum.ewmclient.model.user.UserDto;

import java.io.IOException;
import java.util.List;

@JsonTest
class CompilationDtoTests {

    @Autowired
    private JacksonTester<CompilationDto> json;

    @Test
    void compilationDto() throws IOException {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        UserDto userDto = UserDto.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        EventDto eventDto = EventDto.builder()
                .id(1)
                .eventDate("2011-11-11 11:11")
                .annotation("testAnnotation")
                .category(categoryDto)
                .confirmedRequests(10)
                .createdOn("2011-10-10 10:10")
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(userDto)
                .location(locationDto)
                .requestModeration(true)
                .state("PUBLISHED")
                .publishedOn("2011-10-11 11:11")
                .views(10)
                .build();
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1)
                .pinned(true)
                .events(List.of(eventDto))
                .title("testCompilationTitle")
                .build();

        JsonContent<CompilationDto> result = json.write(compilationDto);
        Assertions.assertEquals("{\"id\":1,\"title\":\"testCompilationTitle\",\"pinned\":true," +
                        "\"events\":[{\"id\":1,\"title\":\"testTitle\",\"annotation\":\"testAnnotation\"," +
                        "\"initiator\":{\"id\":1,\"name\":\"testUser\",\"email\":\"test@email.ru\"},\"confirmedRequests\":10," +
                        "\"category\":{\"id\":1,\"name\":\"testCategory\"},\"createdOn\":\"2011-10-10 10:10\"," +
                        "\"description\":\"testDescription\",\"eventDate\":\"2011-11-11 11:11\"," +
                        "\"location\":{\"id\":1,\"lat\":11.11,\"lon\":22.22},\"paid\":true,\"participantLimit\":20," +
                        "\"publishedOn\":\"2011-10-11 11:11\",\"requestModeration\":true,\"state\":\"PUBLISHED\"," +
                        "\"views\":10}]}", result.getJson(),
                "Ошибка при обработке CompilationDto.");
    }

}