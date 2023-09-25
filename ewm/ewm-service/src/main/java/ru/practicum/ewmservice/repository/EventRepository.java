package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.Event;

import java.util.Collection;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Page<Event> findByInitiatorId(int userId, Pageable page);

    Collection<Event> findByCategoryId(int catId);

    @Query("SELECT E FROM Event AS E " +
            "LEFT JOIN E.marks AS M " +
            "WHERE E.id in (:events) " +
            "GROUP BY E.id, M.event.id, M.user.id " +
            "ORDER BY avg (M.mark) DESC ")
    Collection<Event> findAllWithCriteria(Collection<Integer> events);

    @Query("SELECT E FROM Event AS E " +
            "LEFT JOIN E.marks AS M " +
            "WHERE ((:users) is null or E.initiator.id in (:users)) " +
            "AND ((:states) is null or cast(E.state as string ) in (:states)) " +
            "AND ((:categories) is null or E.category.id in (:categories) ) " +
            "AND (:rangeStart is null or E.eventDate >= cast(:rangeStart as timestamp) ) " +
            "AND (:rangeEnd is null or E.eventDate <= cast( :rangeEnd as timestamp ) ) " +
            "GROUP BY E ORDER BY avg(M.mark) DESC NULLS LAST ")
    Page<Event> findAllAdminWithCriteria(Pageable page,
                                         Collection<Integer> users,
                                         Collection<String> states,
                                         Collection<Integer> categories,
                                         String rangeStart,
                                         String rangeEnd);

    @Query("SELECT E FROM Event AS E " +
            "LEFT JOIN E.marks AS M " +
            "LEFT JOIN E.requests AS PR " +
            "WHERE (:text is null or lower(E.annotation) like lower(CONCAT('%', :text, '%')) " +
            "or lower(E.description) like lower(CONCAT('%', :text, '%'))) " +
            "AND ((:categories) is null or E.category.id in (:categories)) " +
            "AND (:paid is null or E.paid = :paid) " +
            "AND (:rangeStart is null or E.eventDate >= cast(:rangeStart as timestamp )) " +
            "AND (:rangeEnd is null or E.eventDate <= cast(:rangeEnd as timestamp ) ) " +
            "AND (:onlyAvailable = FALSE or (E.participantLimit = 0 or E.requestModeration = FALSE " +
            "or E.participantLimit > (SELECT count (PR.id) FROM PR WHERE PR.status = 'CONFIRMED' AND PR.event.id = E.id))) " +
            "AND (E.state = 'PUBLISHED') " +
            "AND ((:rangeStart is not null or :rangeEnd is not null) or (E.eventDate >= now()) ) " +
            "GROUP BY E ORDER BY E.views DESC ")
    Page<Event> findAllPublicWithCriteria(Pageable page,
                                          String text,
                                          Collection<Integer> categories,
                                          Boolean paid,
                                          String rangeStart,
                                          String rangeEnd,
                                          Boolean onlyAvailable);

    @Query("SELECT E FROM Event AS E " +
            "LEFT JOIN E.marks AS M " +
            "LEFT JOIN E.requests AS PR " +
            "WHERE (:text is null or lower(E.annotation) like lower(CONCAT('%', :text, '%')) " +
            "or lower(E.description) like lower(CONCAT('%', :text, '%'))) " +
            "AND ((:categories) is null or E.category.id in (:categories)) " +
            "AND (:paid is null or E.paid in :paid) " +
            "AND (:rangeStart is null or E.eventDate >= cast(:rangeStart as timestamp )) " +
            "AND (:rangeEnd is null or E.eventDate <= cast(:rangeEnd as timestamp ) ) " +
            "AND (:onlyAvailable = FALSE or (E.participantLimit = 0 or E.requestModeration = FALSE " +
            "or E.participantLimit > (SELECT count (PR.id) FROM PR WHERE PR.status = 'CONFIRMED' AND PR.event.id = E.id))) " +
            "AND (E.state = 'PUBLISHED') " +
            "AND ((:rangeStart is not null or :rangeEnd is not null) or (E.eventDate >= now()) ) " +
            "GROUP BY E " +
            "ORDER BY E.eventDate DESC ")
    Page<Event> findAllPublicWithCriteriaSortDate(Pageable page,
                                          String text,
                                          Collection<Integer> categories,
                                          Boolean paid,
                                          String rangeStart,
                                          String rangeEnd,
                                          Boolean onlyAvailable);

    @Query("SELECT E FROM Event AS E " +
            "LEFT JOIN E.marks AS M " +
            "LEFT JOIN E.requests AS PR " +
            "WHERE (:text is null or lower(E.annotation) like lower(CONCAT('%', :text, '%')) " +
            "or lower(E.description) like lower(CONCAT('%', :text, '%'))) " +
            "AND ((:categories) is null or E.category.id in (:categories)) " +
            "AND (:paid is null or E.paid in :paid) " +
            "AND (:rangeStart is null or E.eventDate >= cast(:rangeStart as timestamp )) " +
            "AND (:rangeEnd is null or E.eventDate <= cast(:rangeEnd as timestamp ) ) " +
            "AND (:onlyAvailable = FALSE or (E.participantLimit = 0 or E.requestModeration = FALSE " +
            "or E.participantLimit > (SELECT count (PR.id) FROM PR WHERE PR.status = 'CONFIRMED' AND PR.event.id = E.id))) " +
            "AND (E.state = 'PUBLISHED') " +
            "AND ((:rangeStart is not null or :rangeEnd is not null) or (E.eventDate >= now()) ) " +
            "GROUP BY E " +
            "ORDER BY avg(M.mark) DESC NULLS LAST ")
    Page<Event> findAllPublicWithCriteriaSortMark(Pageable page,
                                                  String text,
                                                  Collection<Integer> categories,
                                                  Boolean paid,
                                                  String rangeStart,
                                                  String rangeEnd,
                                                  Boolean onlyAvailable);

    @Modifying
    @Query("UPDATE Event AS E SET E.views = :views WHERE E.id = :eventId")
    void updateViews(int eventId, int views);

}