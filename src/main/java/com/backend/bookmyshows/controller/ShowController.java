package com.backend.bookmyshows.controller;

import com.backend.bookmyshows.dto.ShowResponse;
import com.backend.bookmyshows.service.CatalogService;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final CatalogService catalogService;

    public ShowController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public List<ShowResponse> shows(@RequestParam String city,
                                    @RequestParam LocalDateTime date){
           return catalogService.shows(city, LocalDate.from(date));
    }
}
