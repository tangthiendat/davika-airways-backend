package com.dvk.ct250backend.domain.booking.service;

import com.dvk.ct250backend.app.exception.ResourceNotFoundException;
import com.dvk.ct250backend.domain.booking.dto.BookingDTO;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDTO);
}
