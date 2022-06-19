package com.microservices.netflix.watched.content.process.business.concretes;

import com.microservices.netflix.common.entities.WatchContent;
import com.microservices.netflix.watched.content.process.business.abstracts.WatchedFilmProcessService;
import com.microservices.netflix.watched.content.process.dataAccess.WatchedFilmProcessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchedFilmProcessManager implements WatchedFilmProcessService {
    private final WatchedFilmProcessDao watchedFilmProcessDao;

    @Autowired
    public WatchedFilmProcessManager(WatchedFilmProcessDao watchedFilmProcessDao) {
        this.watchedFilmProcessDao = watchedFilmProcessDao;
    }

    @Override
    public void add(WatchContent watchContent) {
        this.watchedFilmProcessDao.save(watchContent);
    }

    @Override
    public void update(WatchContent watchContent) {
        WatchContent value = this.watchedFilmProcessDao.findById(watchContent.getId()).get();
        value.setRemainTime(watchContent.getRemainTime());
        value.setFinished(watchContent.getRemainTime().equals("0")?true:false);
        System.out.println(watchContent.isFinished());
        this.watchedFilmProcessDao.save(value);
    }
}
