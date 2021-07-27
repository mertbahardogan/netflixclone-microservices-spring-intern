package com.microservices.netflix.controller.api;

import com.microservices.netflix.common.entities.RateFilm;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.controller.business.abstracts.RateFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/rate-films/")
@CrossOrigin
public class RateFilmsController {
    private RateFilmService rateFilmService;

    @Autowired
    public RateFilmsController(RateFilmService rateFilmService) {
        this.rateFilmService = rateFilmService;
    }

    @PostMapping("add")
    public Result add(@RequestBody RateFilm rateFilm) throws IOException {
        return this.rateFilmService.add(rateFilm);
    }

    @PutMapping("update")
    public Result update(@RequestParam Long id,@RequestBody RateFilm rateFilm) throws IOException {
        return this.rateFilmService.update(id,rateFilm);
    }

    @DeleteMapping("delete")
    public Result add(@RequestParam Long id) throws IOException {
        return this.rateFilmService.delete(id);
    }

    @GetMapping("findRatedFilmsByIsActiveAndUserId")
    public DataResult<List<RateFilm>> findRatedFilmsByIsActiveAndUserId(@RequestParam int userId) throws IOException {
        return this.rateFilmService.findRatedFilmsByIsActiveAndUserId(userId);
    }
}
