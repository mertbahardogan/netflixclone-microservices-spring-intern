package com.microservices.netflix.controller.dataAccess;

import com.microservices.netflix.common.entities.RateFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateFilmDao extends JpaRepository<RateFilm,Long> {

}
