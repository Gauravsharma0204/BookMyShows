package com.backend.bookmyshows.controller;


import com.backend.bookmyshows.dto.MovieResponse;
import com.backend.bookmyshows.service.CatalogService;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final CatalogService catalogService;

    public MovieController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public List<MovieResponse> movies(){
        return catalogService.movie();
    }
}
