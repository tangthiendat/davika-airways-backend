package com.dvk.ct250backend.domain.booking.service;

import com.dvk.ct250backend.app.dto.response.Page;
import com.dvk.ct250backend.app.exception.ResourceNotFoundException;
import com.dvk.ct250backend.domain.booking.dto.TicketDTO;
import com.dvk.ct250backend.domain.booking.entity.Booking;

import java.util.Map;

public interface TicketService {
   void createTicketsForBooking(Booking booking) throws Exception;
   void updatePdfUrl(Integer bookingId, String pdfUrl) throws ResourceNotFoundException;
   void updateTicket(Integer ticketId, TicketDTO ticketDTO) throws ResourceNotFoundException;
   void deleteTicket(Integer ticketId) throws ResourceNotFoundException;
   Page<TicketDTO> getTickets(Map<String, String> params);
}
