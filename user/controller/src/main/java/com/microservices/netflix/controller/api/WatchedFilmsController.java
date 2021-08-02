package com.microservices.netflix.controller.api;

import com.microservices.netflix.common.entities.RateFilm;
import com.microservices.netflix.common.entities.WatchContent;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.controller.business.abstracts.WatchedFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/watched-films/")
public class WatchedFilmsController {
    private final WatchedFilmService watchedFilmService;

    @Autowired
    public WatchedFilmsController(WatchedFilmService watchedFilmService) {
        this.watchedFilmService = watchedFilmService;
    }

    @PostMapping("add")
    public Result add(@RequestBody WatchContent watchContent){
        return this.watchedFilmService.add(watchContent);
    }

    @PutMapping("update")
    public Result update(@RequestParam Long id,@RequestBody  WatchContent watchContent)  {
        return this.watchedFilmService.update(id,watchContent);
    }

    @GetMapping("findByIsActiveAndIsFinishedAndUserId")
    public DataResult<List<WatchContent>> findByIsActiveAndIsFinishedAndUserId(@RequestParam int userId){
        return this.watchedFilmService.findByIsActiveAndIsFinishedAndUserId(userId);
    }

    @GetMapping("findByIsActiveAndIsNotFinishedAndUserId")
    public DataResult<List<WatchContent>> findByIsActiveAndIsNotFinishedAndUserId(@RequestParam int userId){
        return this.watchedFilmService.findByIsActiveAndIsNotFinishedAndUserId(userId);
    }
}
