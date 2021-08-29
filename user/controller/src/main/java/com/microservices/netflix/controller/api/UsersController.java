package com.microservices.netflix.controller.api;

import com.microservices.netflix.common.entities.FavouriteFilm;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.controller.business.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/")
//@CrossOrigin
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("findAllByIsActive")
    public DataResult<List<Film>> findAllByIsActive() {
        return this.userService.findAllByIsActive();
    }

    @GetMapping("findByIsActiveAndId")
    public  DataResult<Optional<Film>> findAllByIsActive(@RequestParam Long id) {
        return this.userService.findByIsActiveAndId(id);
    }


}
