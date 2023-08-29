package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.Event;

import java.util.Collection;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Modifying
    @Query("UPDATE  Event AS E SET E.confirmedRequests = E.confirmedRequests + 1 WHERE E.id = :eventId")
    void increaseConfirmedRequests(int eventId);

    @Modifying
    @Query("UPDATE  Event AS E SET E.confirmedRequests = E.confirmedRequests - 1 WHERE E.id = :eventId")
    void decreaseConfirmedRequests(int eventId);

    Page<Event> findByInitiatorId(int userId, Pageable page);

    Collection<Event> findByCategoryId(int catId);

    @Query("FROM Event AS E WHERE E.id in :events")
    Collection<Event> findAllWithCriteria(Collection<Integer> events);

    @Query("FROM Event AS E WHERE (:users is null or E.initiator.id in (:users)) " +
            "AND (:states is null or cast(E.state as string ) in (:states)) " +
            "AND (:categories is null or E.category.id in (:categories) ) " +
            "AND (:rangeStart is null or E.eventDate >= cast(:rangeStart as timestamp) ) " +
            "AND (:rangeEnd is null or E.eventDate <= cast( :rangeEnd as timestamp ) )")
    Page<Event> findAllAdminWithCriteria(Pageable page,
                                         Collection<Integer> users,
                                         Collection<String> states,
                                         Collection<Integer> categories,
                                         String rangeStart,
                                         String rangeEnd);

    @Query("FROM Event AS E WHERE (:text is null or lower(E.annotation) like lower(CONCAT('%', :text, '%')) or lower(E.description) like lower(CONCAT('%', :text, '%'))) " +
            "AND (:categories is null or E.category.id in (:categories)) " +
            "AND (:paid is null or E.paid in :paid) " +
            "AND (:rangeStart is null or E.eventDate >= cast(:rangeStart as timestamp )) " +
            "AND (:rangeEnd is null or E.eventDate <= cast(:rangeEnd as timestamp ) ) " +
            "AND (:onlyAvailable = FALSE or (E.participantLimit = 0 or E.requestModeration = FALSE or E.participantLimit > E.confirmedRequests)) " +
            "AND (E.state = 'PUBLISHED') " +
            "AND ((:rangeStart is null AND :rangeEnd is null) or (E.eventDate >= now()) )")
    Page<Event> findAllPublicWithCriteria(Pageable page,
                                          String text,
                                          Collection<Integer> categories,
                                          Boolean paid,
                                          String rangeStart,
                                          String rangeEnd,
                                          Boolean onlyAvailable);

}