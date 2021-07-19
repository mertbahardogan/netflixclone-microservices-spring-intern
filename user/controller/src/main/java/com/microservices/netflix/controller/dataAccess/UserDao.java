package com.microservices.netflix.controller.dataAccess;

import com.microservices.netflix.common.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<Film,Long> {
    @Query("FROM Film WHERE isActive = True")
    List<Film> findAllByIsActive();

    @Query("FROM Film WHERE isActive = True and id=:id")
    Optional<Film> findByIsActiveAndId(Long id);
}
