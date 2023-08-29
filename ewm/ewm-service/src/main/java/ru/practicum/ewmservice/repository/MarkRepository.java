package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.Mark;

public interface MarkRepository extends JpaRepository<Mark, Integer> {

    Mark findByUser_IdAndEvent_Id(int userId, int eventId);

    void removeByUser_IdAndEvent_Id(int userId, int eventId);

    @Query("SELECT avg(M.mark) FROM Mark AS M WHERE M.event.id in :eventId")
    int findMarkForEvent(int eventId);

    @Query("SELECT avg(M.mark) FROM Mark AS M WHERE M.event.initiator.id in :userId")
    int findMarkForUser(int userId);

}