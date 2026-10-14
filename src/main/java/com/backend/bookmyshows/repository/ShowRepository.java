package com.backend.bookmyshows.repository;

import com.backend.bookmyshows.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    boolean existsByMovieIdAndTheaterIdAndStartAt(Long movieId, Long theaterId, LocalDateTime startAt);

    @Query("SELECT s FROM Show s " +
            "JOIN FETCH s.movie m " +
            "JOIN FETCH s.theater t " +
            "WHERE s.active = true " +
            "AND m.active = true " +
            "AND t.city = :city " +
            "AND s.startAt >= :from " +
            "AND s.startAt < :to " +
            "ORDER BY s.startAt")
    List<Show> findActiveShows(String city, LocalDateTime from, LocalDateTime to);

}
