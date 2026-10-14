package com.backend.bookmyshows.dto;

import com.backend.bookmyshows.entity.Booking;
import com.backend.bookmyshows.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingResponse(Long id, Long ShowId, Long profileId, String movieTitle,
                              String theaterName, String customerName, String customerEmail,
                              String customerPhone, List<String> seatlabels, BigDecimal totalAmount,
                              BookingStatus status, LocalDateTime bookedAt)
{
    public static BookingResponse from (Booking booking){
        return new BookingResponse(booking.getId(), booking.getShow().getId(),
                booking.getCustomer() == null ? null : booking.getCustomer().getId(),
                booking.getShow().getMovie().getTitle(),
                booking.getShow().getTheater().getName(),
                booking.getCustomerName(), booking.getCustomerEmail(),
                booking.getCustomerPhone(), booking.getSeatLabels(), booking.getTotalAmount(),
                booking.getStatus(), booking.getBookedAt()
                );
    }


}
