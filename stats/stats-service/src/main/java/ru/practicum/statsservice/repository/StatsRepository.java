package ru.practicum.statsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statscommondto.ViewStatsDto;
import ru.practicum.statsservice.model.Stats;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsRepository extends JpaRepository<Stats, Integer> {

    @Query("SELECT S.app AS app, S.uri AS uri, COUNT (S.id) AS hits " +
            "FROM Stats AS S WHERE S.timeStamp BETWEEN :start AND :end " +
            "AND S.uri in :uris GROUP BY S.app, S.uri order by hits DESC ")
    Collection<ViewStatsDto> findAllStatsWithUris(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("SELECT S.app AS app, S.uri AS uri, COUNT (distinct S.ip) AS hits " +
            "FROM Stats AS S WHERE S.timeStamp BETWEEN :start AND :end " +
            "AND S.uri in :uris GROUP BY S.app, S.uri order by hits DESC ")
    Collection<ViewStatsDto> findUniqueStatsWithUris(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("SELECT S.app AS app, S.uri AS uri, COUNT (S.id) AS hits " +
            "FROM Stats AS S WHERE S.timeStamp BETWEEN :start AND :end " +
            "GROUP BY S.app, S.uri order by hits DESC ")
    Collection<ViewStatsDto> findAllStatsWithoutUris(LocalDateTime start, LocalDateTime end);

    @Query("SELECT S.app AS app, S.uri AS uri, COUNT (distinct S.ip) AS hits " +
            "FROM Stats AS S WHERE S.timeStamp BETWEEN :start AND :end " +
            "GROUP BY S.app, S.uri order by hits DESC ")
    Collection<ViewStatsDto> findUniqueStatsWithoutUris(LocalDateTime start, LocalDateTime end);

}