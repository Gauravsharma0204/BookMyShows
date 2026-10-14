package com.backend.bookmyshows.service;

import com.backend.bookmyshows.dto.MovieResponse;
import com.backend.bookmyshows.dto.ShowResponse;
import com.backend.bookmyshows.dto.TheaterResponse;
import com.backend.bookmyshows.repository.MovieRepository;
import com.backend.bookmyshows.repository.ShowRepository;
import com.backend.bookmyshows.repository.ShowSeatRepository;
import com.backend.bookmyshows.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CatalogService {

    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;


    public CatalogService(MovieRepository movieRepository, TheaterRepository theaterRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository) {
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
    }

    public List<MovieResponse> movie(){
        return movieRepository.findByActiveTrueOrderByTitle().stream().map(MovieResponse::from).toList();
    }

    public List<TheaterResponse> theaters(String city){
        return theaterRepository.findByCityIgnoreCaseOrderByName(city).stream().map(TheaterResponse::from).toList();
    }

    public List<ShowResponse> shows(String city, LocalDate date){
        LocalDateTime from = date.atStartOfDay();
        return showRepository.findActiveShows(city, from, from.plusDays(1)).stream()
                .map(show -> ShowResponse.from(show, showSeatRepository.findAvailableLabels(show.getId()))).toList();
    }
}
