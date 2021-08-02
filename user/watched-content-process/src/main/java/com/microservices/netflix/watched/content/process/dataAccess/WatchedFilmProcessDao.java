package com.microservices.netflix.watched.content.process.dataAccess;

import com.microservices.netflix.common.entities.WatchContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedFilmProcessDao extends JpaRepository<WatchContent,Long> {

}
