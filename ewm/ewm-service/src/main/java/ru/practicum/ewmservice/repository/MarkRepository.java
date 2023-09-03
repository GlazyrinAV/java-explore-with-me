package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.Mark;
import ru.practicum.ewmservice.model.MarkId;

public interface MarkRepository extends JpaRepository<Mark, MarkId> {

    Mark findByUser_IdAndEvent_Id(int userId, int eventId);

    @Query("SELECT avg(M.mark) FROM Mark AS M WHERE M.event.id in :eventId")
    Double findMarkForEvent(int eventId);

    @Query("SELECT avg(M.mark) FROM Mark AS M WHERE M.event.initiator.id in :userId")
    Double findMarkForUser(int userId);

}