package com.microservices.netflix.film.process.dataAccess;

import com.microservices.netflix.common.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmProcessDao extends JpaRepository<Film,Long> {


}
