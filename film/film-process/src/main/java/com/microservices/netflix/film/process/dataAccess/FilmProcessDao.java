package com.microservices.netflix.film.process.dataAccess;

import com.microservices.netflix.common.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmProcessDao extends JpaRepository<Film,Long> {
    Optional<Film> findById(Long id);
//    void deleteById(Long id);
}
