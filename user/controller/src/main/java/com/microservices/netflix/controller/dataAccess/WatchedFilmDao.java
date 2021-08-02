package com.microservices.netflix.controller.dataAccess;

import com.microservices.netflix.common.entities.WatchContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchedFilmDao extends JpaRepository<WatchContent,Long> {

    @Query("FROM WatchContent WHERE userId=:userId and film.id=:filmId")
    Optional<WatchContent> findByUserIdAndFilm(int userId, Long filmId);

    @Query("FROM WatchContent w  JOIN UserProcess u ON w.id=u.id AND w.isFinished=true JOIN Film fs ON u.film.id=fs.id AND u.film.isActive=true AND u.userId=:userId")
    List<WatchContent> findByIsActiveAndIsFinishedAndUserId(int userId);

    @Query("FROM WatchContent w JOIN UserProcess u ON w.id=u.id  AND w.isFinished=false JOIN Film fs ON u.film.id=fs.id AND u.film.isActive=true AND u.userId=:userId")
    List<WatchContent> findByIsActiveAndIsNotFinishedAndUserId(int userId);
}
