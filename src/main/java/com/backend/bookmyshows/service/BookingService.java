package com.backend.bookmyshows.service;

import com.backend.bookmyshows.dto.BookingResponse;
import com.backend.bookmyshows.dto.CreateBookingRequest;
import com.backend.bookmyshows.entity.*;
import com.backend.bookmyshows.repository.BookingRepository;
import com.backend.bookmyshows.repository.CustomerRepository;
import com.backend.bookmyshows.repository.ShowRepository;
import com.backend.bookmyshows.repository.ShowSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class BookingService {

    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;

    public BookingService(ShowRepository showRepository, ShowSeatRepository showSeatRepository1, BookingRepository bookingRepository, CustomerRepository customerRepository) {
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository1;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public BookingResponse book(Long showId, CreateBookingRequest request){

        Show show = showRepository.findById(showId).orElseThrow(()-> new ResourceNotFoundException("Show Not Found"));
        var customer = customerRepository.findById(request.profile())
                .orElseThrow(()-> new ResourceNotFoundException("Profile not found"));

        List<String> labels =  request.seatLabels().stream()
                .map(lable-> lable.trim().toUpperCase(Locale.ROOT)).toList();

        if(labels.stream().distinct().count() != labels.size()){
            throw new SeatUnavailbleException("Duplicate seat lales are not allowed");
        }

        List<ShowSeat> seats = showSeatRepository.findForUpdate(showId, labels);
        if (seats.size() != labels.size() || seats.stream().anyMatch(ShowSeat::isReserved)){
            throw new SeatUnavailbleException("One or more selected seats are Unavailable");
        }

        seats.forEach(ShowSeat::reserve);
        show.reserve(labels.size());

        BigDecimal total = show.getTicketPrice().multiply(BigDecimal.valueOf(labels.size()));
        Booking booking = bookingRepository.save(new Booking(show, customer, total, labels));

        return BookingResponse.from(booking);
    }

    public BookingResponse find(Long bookingId){
         Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        return BookingResponse.from(booking);
    }

    @Transactional
    public List<BookingResponse> findByProfileId(Long profileId){
        if(!customerRepository.existsById(profileId)){
            throw new ResourceNotFoundException("Profile Not Found");
        }

        List<BookingResponse> list = bookingRepository.findByCustomerIdOrderByBookedAtDesc(profileId).stream()
                .map(BookingResponse::from).toList();

        return list;
    }

    @Transactional
    public BookingResponse cancel(Long bookingId, Long profileId){
        Booking booking = bookingRepository.findByIdAndCustomerId(bookingId, profileId)
                .orElseThrow(()-> new ResourceNotFoundException("Booking ot Found"));

        if (booking.getStatus() == BookingStatus.CONFIRMED){
            List<ShowSeat> seats = showSeatRepository.findForUpdate(booking.getId(), booking.getSeatLabels());
            seats.forEach(ShowSeat::release);
            booking.getShow().release(booking.getSeatLabels().size());
            booking.cancel();
        }

        return BookingResponse.from(booking);
    }
}
