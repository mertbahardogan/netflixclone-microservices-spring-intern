package com.microservices.netflix.rate.process.dataAccess;

import com.microservices.netflix.common.entities.RateFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateProcessDao extends JpaRepository<RateFilm,Long> {

}
