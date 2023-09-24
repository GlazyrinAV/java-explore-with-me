package ru.practicum.statsservice.unit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.statsclient.dto.StatsDto;
import ru.practicum.statsservice.exception.BadParameter;
import ru.practicum.statsservice.model.Mapper;
import ru.practicum.statsservice.model.Stats;
import ru.practicum.statsservice.repository.StatsRepository;
import ru.practicum.statsservice.service.StatsService;
import ru.practicum.statsservice.service.StatsServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class StatsTests {

    private final StatsRepository mockStatsRepository = Mockito.mock(StatsRepository.class);

    private final Mapper mockMapper = Mockito.mock(Mapper.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StatsService service = new StatsServiceImpl(mockStatsRepository, mockMapper, formatter);

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void saveStatsNormal() {
        StatsDto statsDto = StatsDto.builder()
                .uri("testUri")
                .ip("111.111.111.111.111")
                .app("testApp")
                .timeStamp("2011-11-11 11:11")
                .build();
        JsonNode node = mapper.valueToTree(statsDto);
        Stats stats = new Stats(0, "appTest", LocalDateTime.of(2011, 11, 11, 11, 11), node);
        when(mockMapper.fromDto(any()))
                .thenReturn(stats);
        service.saveStats(node);
        Mockito.verify(mockStatsRepository, times(1)).save(mockMapper.fromDto(node));
    }

    @Test
    void findStatsNormalWithUrisUnique() {
        String start = "2011-11-11 11:11:11";
        String end = "2011-12-12 12:12:11";
        service.findStats(start, end, List.of("/testUri"), true);
        Mockito.verify(mockStatsRepository,
                times(1)).findAllUniqueStatsWithUris(start, end, List.of("/testUri"));
    }

    @Test
    void findStatsNormalWithoutUrisUnique() {
        String start = "2011-11-11 11:11:11";
        String end = "2011-12-12 12:12:11";
        service.findStats(start, end, null, true);
        Mockito.verify(mockStatsRepository,
                times(1)).findAllUniqueStatsWithoutUris(start, end);
    }

    @Test
    void findStatsNormalWithUrisAll() {
        String start = "2011-11-11 11:11:11";
        String end = "2011-12-12 12:12:11";
        service.findStats(start, end, List.of("/testUri"), false);
        Mockito.verify(mockStatsRepository,
                times(1)).findAllStatsWithUris(start, end, List.of("/testUri"));
    }

    @Test
    void findStatsNormalWithoutUrisAll() {
        String start = "2011-11-11 11:11:11";
        String end = "2011-12-12 12:12:11";
        service.findStats(start, end, null, false);
        Mockito.verify(mockStatsRepository,
                times(1)).findAllStatsWithoutUris(start, end);
    }

    @Test
    void findStatsWrongPeriod() {
        String start = "2011-11-11 11:11:11";
        String end = "2011-12-12 12:12:11";
        BadParameter exception = Assertions.assertThrows(BadParameter.class,
                () -> service.findStats(end, start, null, false));
        Assertions.assertEquals("Дата начала не может быть позже конца.", exception.getMessage());
    }

    @Test
    void findStatsForEwm() {
        when(mockStatsRepository.findStatsForEwm(any()))
                .thenReturn(1);
        Assertions.assertEquals(1, service.findStatsForEwm(1));
    }

    @Test
    void findStatsForEwmNull() {
        when(mockStatsRepository.findStatsForEwm(any()))
                .thenReturn(null);
        Assertions.assertEquals(0, service.findStatsForEwm(1));
    }

}