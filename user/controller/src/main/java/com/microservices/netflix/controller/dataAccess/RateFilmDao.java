package com.microservices.netflix.controller.dataAccess;

import com.microservices.netflix.common.entities.RateFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateFilmDao extends JpaRepository<RateFilm,Long> {

    @Query("FROM RateFilm r JOIN UserProcess u ON r.id=u.id JOIN Film fs ON u.film.id=fs.id AND u.film.isActive=true")
    List<RateFilm> findByIsActiveAndUserId(int userId);
}
