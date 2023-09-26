package ru.practicum.statsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statsclient.dto.ViewDataDto;
import ru.practicum.statsclient.dto.ViewStatsDto;
import ru.practicum.statsservice.model.Stats;

import java.util.Collection;

public interface StatsRepository extends JpaRepository<Stats, Integer> {

    @Query("SELECT FUNCTION('jsonb_extract_path_text', S.additionalProps, 'app') AS app, " +
            "FUNCTION('jsonb_extract_path_text', S.additionalProps, 'uri') AS uri, " +
            "COUNT (S.id) AS hits " +
            "FROM Stats AS S " +
            "WHERE (S.timeStamp BETWEEN cast(:start as timestamp ) AND cast(:end as timestamp )) " +
            "AND FUNCTION('jsonb_extract_path_text', S.additionalProps, 'uri') in (:uris) " +
            "GROUP BY app, uri " +
            "ORDER BY hits DESC ")
    Collection<ViewStatsDto> findAllStatsWithUris(String start, String end, Collection<String> uris);

    @Query("SELECT FUNCTION('jsonb_extract_path_text', S.additionalProps, 'app') AS app, " +
            "FUNCTION('jsonb_extract_path_text', S.additionalProps, 'uri') AS uri, " +
            "COUNT (S.id) AS hits " +
            "FROM Stats AS S " +
            "WHERE (S.timeStamp BETWEEN cast(:start as timestamp) AND cast(:end as timestamp)) " +
            "GROUP BY app, uri " +
            "ORDER BY hits DESC ")
    Collection<ViewStatsDto> findAllStatsWithoutUris(String start, String end);

    @Query("SELECT FUNCTION('jsonb_extract_path_text', S.additionalProps, 'app') AS app, " +
            "FUNCTION('jsonb_extract_path_text', S.additionalProps, 'uri') AS uri, " +
            "COUNT (distinct (FUNCTION('jsonb_extract_path_text', S.additionalProps, 'ip'))) AS hits " +
            "FROM Stats AS S " +
            "WHERE (S.timeStamp BETWEEN cast(:start as timestamp ) AND cast(:end as timestamp )) " +
            "AND FUNCTION('jsonb_extract_path_text', S.additionalProps, 'uri') in (:uris) " +
            "GROUP BY app, uri " +
            "ORDER BY COUNT (distinct (FUNCTION('jsonb_extract_path_text', S.additionalProps, 'ip'))) DESC ")
    Collection<ViewStatsDto> findAllUniqueStatsWithUris(String start, String end, Collection<String> uris);

    @Query("SELECT FUNCTION('jsonb_extract_path_text', S.additionalProps, 'app') AS app, " +
            "FUNCTION('jsonb_extract_path_text', S.additionalProps, 'uri') AS uri, " +
            "COUNT (distinct (FUNCTION('jsonb_extract_path_text', S.additionalProps, 'ip'))) AS hits " +
            "FROM Stats AS S " +
            "WHERE (S.timeStamp BETWEEN cast(:start as timestamp) AND cast(:end as timestamp)) " +
            "GROUP BY app, uri " +
            "ORDER BY COUNT (distinct (FUNCTION('jsonb_extract_path_text', S.additionalProps, 'ip'))) DESC ")
    Collection<ViewStatsDto> findAllUniqueStatsWithoutUris(String start, String end);

    @Query("SELECT COUNT (distinct (FUNCTION('jsonb_extract_path_text', S.additionalProps, 'ip'))) " +
            "FROM Stats AS S " +
            "WHERE FUNCTION('jsonb_extract_path_text', S.additionalProps, 'uri') in (:uri) ")
    Integer findStatsForEwm(String uri);

    @Query("SELECT REPLACE (FUNCTION('jsonb_extract_path_text', S.additionalProps, 'uri'), '/events/', '' ) AS uri, " +
            "COUNT (distinct (FUNCTION('jsonb_extract_path_text', S.additionalProps, 'ip'))) AS hits " +
            "FROM Stats AS S " +
            "WHERE (FUNCTION('jsonb_extract_path_text', S.additionalProps, 'uri') like ('/events/%')) " +
            "GROUP BY uri")
    Collection<ViewDataDto> updateViews();

}