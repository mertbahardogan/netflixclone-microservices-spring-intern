package com.microservices.netflix.controller.api;

import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.controller.business.abstracts.UserService;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UsersController {
    private final UserService userService;

    @ConstructorBinding
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public DataResult<List<Film>> findAllByIsActive() {
        return this.userService.findAllByIsActive();
    }

    @GetMapping(value = "/{id}")
    public DataResult<Optional<Film>> findByIsActiveAndId(@PathVariable(value = "id") Long id) {
        return this.userService.findByIsActiveAndId(id);
    }
}
