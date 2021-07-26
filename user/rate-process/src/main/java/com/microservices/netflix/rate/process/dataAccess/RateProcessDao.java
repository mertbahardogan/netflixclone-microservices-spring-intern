package com.microservices.netflix.rate.process.dataAccess;

import com.microservices.netflix.common.entities.RateFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateProcessDao extends JpaRepository<RateFilm,Long> {
    void deleteById(Long id);
    Optional<RateFilm> findById(Long id);
}
