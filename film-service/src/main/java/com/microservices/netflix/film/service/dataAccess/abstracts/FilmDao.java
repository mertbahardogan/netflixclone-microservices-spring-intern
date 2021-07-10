package com.microservices.netflix.film.service.dataAccess.abstracts;

import com.microservices.netflix.film.service.entities.concretes.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmDao extends JpaRepository<Film, Long> {
    List<Film> findAll();

    Film getById(Long id);
}
