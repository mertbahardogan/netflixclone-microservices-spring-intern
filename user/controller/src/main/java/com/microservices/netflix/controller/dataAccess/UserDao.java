package com.microservices.netflix.controller.dataAccess;

import com.microservices.netflix.common.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<Film,Long> {
}
