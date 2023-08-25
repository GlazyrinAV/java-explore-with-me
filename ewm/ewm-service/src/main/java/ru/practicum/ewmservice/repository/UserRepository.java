package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("FROM User AS U WHERE (:ids is null or U.id in :ids)")
    Page<User> findAllAdminWithCriteria(Pageable page,
                                        Integer[] ids);

}