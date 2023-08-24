package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}