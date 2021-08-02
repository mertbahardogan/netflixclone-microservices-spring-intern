package com.microservices.netflix.controller.dataAccess;

import com.microservices.netflix.common.entities.RateFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateFilmDao extends JpaRepository<RateFilm,Long> {

    @Query("FROM RateFilm WHERE userId=:userId and film.id=:filmId")
    Optional<RateFilm> findByUserIdAndFilm(int userId, Long filmId);

    @Query("FROM RateFilm r JOIN UserProcess u ON r.id=u.id JOIN Film fs ON u.film.id=fs.id AND u.film.isActive=true AND u.userId=:userId")
    List<RateFilm> findByIsActiveAndUserId(int userId);
}
