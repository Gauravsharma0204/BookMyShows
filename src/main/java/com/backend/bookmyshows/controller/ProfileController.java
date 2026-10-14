package com.backend.bookmyshows.controller;

import com.backend.bookmyshows.dto.BookingResponse;
import com.backend.bookmyshows.dto.CreateProfileRequest;
import com.backend.bookmyshows.dto.ProfileResponse;
import com.backend.bookmyshows.service.BookingService;
import com.backend.bookmyshows.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final BookingService bookingService;

    public ProfileController(ProfileService profileService, BookingService bookingService) {
        this.profileService = profileService;
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse create(@Valid @RequestBody CreateProfileRequest request){
        return profileService.create(request);
    }

    @GetMapping("/login")
    public ProfileResponse login(@RequestParam String identifier){
        return profileService.login(identifier);
    }

    @GetMapping("/{profileIs}/bookings")
    public List<BookingResponse> booking(@PathVariable long profileId){
        return bookingService.findByProfileId(profileId);
    }

}
