package com.backend.bookmyshows.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Theater theater;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private BigDecimal ticketPrice;

    private int totalSeats;

    private int availableSeats;

    private boolean active = true;

    @Version
    private long version;

    public Show(Movie movie, Theater theater, LocalDateTime startAt, LocalDateTime endAt,BigDecimal ticketPrice, int totalSeats) {
        this.movie = movie;
        this.theater = theater;
        this.startAt = startAt;
        this.endAt = endAt;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void reserve(int seats){
        if (seats <= 0 || seats > availableSeats){
            throw new IllegalArgumentException("Not Enough seat Available");
        }

        availableSeats -= seats;
    }

    public void release(int seats){
        availableSeats = availableSeats + seats;
    }
}
