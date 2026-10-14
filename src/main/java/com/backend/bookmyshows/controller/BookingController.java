package com.backend.bookmyshows.controller;


import com.backend.bookmyshows.dto.BookingResponse;
import com.backend.bookmyshows.dto.CreateBookingRequest;
import com.backend.bookmyshows.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/shows/{showId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse book(@PathVariable Long showId, @RequestBody CreateBookingRequest request){
        return bookingService.book(showId,request);
    }

    @PostMapping("/{bookingId}/cancel")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse cancel(@PathVariable Long bookingId, @RequestBody Long profileId){
        return bookingService.cancel(bookingId,profileId);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse find(@PathVariable Long bookingId){
            return bookingService.find(bookingId);
    }
}
