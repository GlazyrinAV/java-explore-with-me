package ru.practicum.statsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statscommondto.ViewStatsDto;
import ru.practicum.statsservice.model.Stats;

import java.util.Collection;

public interface StatsRepository extends JpaRepository<Stats, Integer> {

    @Query("SELECT S.app AS app, S.uri AS uri, COUNT (S.id) AS hits " +
            "FROM Stats AS S WHERE (S.timeStamp BETWEEN cast(:start as timestamp ) AND cast(:end as timestamp )) " +
            "AND S.uri in (:uris) GROUP BY S.app, S.uri order by hits DESC ")
    Collection<ViewStatsDto> findAllStatsWithUris(String start, String end, Collection<String> uris);

    @Query("SELECT S.app AS app, S.uri AS uri, COUNT (distinct S.ip) AS hits " +
            "FROM Stats AS S WHERE (S.timeStamp BETWEEN cast(:start as timestamp) AND cast(:end as timestamp)) " +
            "AND S.uri = (:uris) GROUP BY S.app, S.uri order by hits DESC ")
    Collection<ViewStatsDto> findUniqueStatsWithUris(String start, String end, Collection<String> uris);

    @Query("SELECT S.app AS app, S.uri AS uri, COUNT (S.id) AS hits " +
            "FROM Stats AS S WHERE (S.timeStamp BETWEEN cast(:start as timestamp) AND cast(:end as timestamp)) " +
            "GROUP BY S.app, S.uri order by hits DESC ")
    Collection<ViewStatsDto> findAllStatsWithoutUris(String start, String end);

    @Query("SELECT S.app AS app, S.uri AS uri, COUNT (distinct S.ip) AS hits " +
            "FROM Stats AS S WHERE (S.timeStamp BETWEEN cast(:start as timestamp) AND cast(:end as timestamp)) " +
            "GROUP BY S.app, S.uri order by hits DESC ")
    Collection<ViewStatsDto> findUniqueStatsWithoutUris(String start, String end);

    @Query("SELECT COUNT (distinct S.ip) FROM Stats AS S WHERE S.uri in (:uri)")
    Integer findStatsForEwm(String uri);

}