package com.microservices.netflix.controller.api;

import com.microservices.netflix.common.entities.RateFilm;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.controller.business.abstracts.RateFilmService;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rate/api/v1/")
public class RateFilmsController {
    private final RateFilmService rateFilmService;

    @ConstructorBinding
    public RateFilmsController(RateFilmService rateFilmService) {
        this.rateFilmService = rateFilmService;
    }

    @PostMapping()
    public Result add(@RequestBody RateFilm rateFilm){
        return this.rateFilmService.add(rateFilm);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id") Long id,@RequestBody RateFilm rateFilm)  {
        return this.rateFilmService.update(id,rateFilm);
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") Long id) {
        return this.rateFilmService.delete(id);
    }

    @GetMapping(value = "/{userId}")
    public DataResult<List<RateFilm>> findRatedFilmsByIsActiveAndUserId(@PathVariable(value = "userId") int userId){
        return this.rateFilmService.findRatedFilmsByIsActiveAndUserId(userId);
    }
}
