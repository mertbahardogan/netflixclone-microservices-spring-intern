package com.microservices.netflix.controller.api;

import com.microservices.netflix.common.entities.WatchContent;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.controller.business.abstracts.WatchedFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/watched")
public class WatchedFilmsController {
    private final WatchedFilmService watchedFilmService;

    @Autowired
    public WatchedFilmsController(WatchedFilmService watchedFilmService) {
        this.watchedFilmService = watchedFilmService;
    }

    @PostMapping()
    public Result add(@RequestBody WatchContent watchContent){
        return this.watchedFilmService.add(watchContent);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id") Long id,@RequestBody WatchContent watchContent)  {
        return this.watchedFilmService.update(id,watchContent);
    }

    @GetMapping("/finished-films/{userId}")
    public DataResult<List<WatchContent>> findByIsActiveAndIsFinishedAndUserId(@PathVariable(value = "userId") int userId){
        return this.watchedFilmService.findByIsActiveAndIsFinishedAndUserId(userId);
    }

    @GetMapping("/films/{userId}")
    public DataResult<List<WatchContent>> findByIsActiveAndIsNotFinishedAndUserId(@PathVariable(value = "userId") int userId){
        return this.watchedFilmService.findByIsActiveAndIsNotFinishedAndUserId(userId);
    }
}
