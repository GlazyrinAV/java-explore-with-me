package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);

    @Query("FROM Category AS C WHERE lower( C.name ) like lower(:name) AND C.id not in (:catId)")
    Category findDuplicate(String name, int catId);

}