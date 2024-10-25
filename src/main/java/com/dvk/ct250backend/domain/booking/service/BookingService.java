package com.dvk.ct250backend.domain.booking.service;

import com.dvk.ct250backend.domain.booking.dto.BookingDTO;

public interface BookingService {
    BookingDTO createInitBooking(BookingDTO bookingDTO);
//    BookingDTO confirmBookingPayment(String redisKey, BookingDTO bookingDTO);
//    BookingDTO reserveBooking(String redisKey, BookingDTO bookingDTO);
}
